from flask import Flask,request,jsonify
import joblib
import datetime
import numpy as np
import pandas as pd
from Landslide.Model_Landslide import predict_disaster

rfr_model, ref_columns1, target1 = joblib.load('Integration/Flood/Flood_RFRModel.pkl','r+')
xgb_model, ref_columns2, target2 = joblib.load('Integration/Flood/Flood_XGBModel.pkl','r+')

app = Flask(__name__)

@app.route('/')
def home():
    return 'Hello world'

@app.route('/predict',methods=['POST'])
def predict():
    location = request.form.get('Location')
    location1 = request.form.get('Location1')
    location2 = request.form.get('Location2')
    district = request.form.get('District')
    month = request.form.get('Month')
    day = request.form.get('Day')
    rainfall = request.form.get('Rainfall(mm)')
    
    rfr_prediction,xgb_prediction = predict_disaster(location,location1,location2,district,month,day,rainfall)
    
    rfr_prediction = np.round(rfr_prediction*100,2)
    xgb_prediction = np.round(xgb_prediction*100,2)
    
    month = int(month)
    day = int(day)+1
    
    def lastday(month,day):
        last_day_of_month = (datetime.date(datetime.date.today().year, month % 12 + 1, 1) - datetime.timedelta(days=1)).day
        if day == last_day_of_month:
            if month == 12:  
                month = 1
            else:
                month += 1
                day = 1
        else:
            day += 1
        return month,day
    
    
    print(day)
    
    
    return jsonify({'RandomForest Predicted Value':float(rfr_prediction),"XGB Predicted Value":float(xgb_prediction)})

if __name__=='__main__':
    app.run(debug=True)

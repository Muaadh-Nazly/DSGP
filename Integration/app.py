from flask import Flask,request,jsonify
import joblib
import numpy as np
import pandas as pd

rfr_model, ref_columns1, target1 = joblib.load('Modelling/Flood_RFRModel.pkl','r+')
xgb_model, ref_columns2, target2 = joblib.load('Modelling/Flood_XGBModel.pkl','r+')



app = Flask(__name__)

@app.route('/')
def home():
    return 'Hello world'

@app.route('/predict',methods=['POST'])
def predict():
    disaster = request.form.get('Disaster')
    location = request.form.get('Location')
    district = request.form.get('District')
    month = request.form.get('Month')
    day = request.form.get('Day')
    rainfall = request.form.get('Rainfall(mm)')
    
    user_data = pd.DataFrame({
    'Disaster' : int(disaster),
    'Location' : int(location),
    'District' : int(district),
    'Month' : int(month),
    'Day' : int(day),
    'Rainfall(mm)': float(rainfall)
    },index=[0])

    
    rfr_prediction = np.round((rfr_model.predict(user_data)[0]*100),2)
    xgb_prediction = np.round((xgb_model.predict(user_data)[0]*100),2)

    return jsonify({'RandomForest Predicted Value':rfr_prediction,"XGB Predicted Value":xgb_prediction})

if __name__=='__main__':
    app.run(debug=True)


    

from flask import Flask,request,jsonify
import datetime as dt
from Model_Landslide import predict_landslide
from Model_Flood import predict_flood
from Model_Cyclone import predict_cyclone
app = Flask(__name__)

@app.route('/')
def home():
    return 'IT WORKS'

@app.route('/predict_landslide',methods=['POST'])
def landslide_predict():
    
    location = request.form.get('Location')
    location1 = request.form.get('Location1')
    location2 = request.form.get('Location2')
    district = request.form.get('District')
    rainfall = float(request.form.get('Rainfall(mm)'))
    
    date = dt.date.today() 
    rfr_prediction,xgb_prediction = predict_landslide(location=location,location1=location1,location2=location2,
                                                     district=district,month=date.month,day=date.day,rainfall=rainfall) 
    
    def add_day(date, days_to_add):
        new_date = date + dt.timedelta(days=days_to_add)
        return new_date
    
    date1 = add_day(date,1)
    rfr_prediction_day1 , xgb_prediction_day1 = predict_landslide(location=location,location1=location1,location2=location2,
                                                     district=district,month=date1.month,day=date1.day,rainfall=rainfall) 
    
    date2 = add_day(date,2)
    rfr_prediction_day2 , xgb_prediction_day2 = predict_landslide(location=location,location1=location1,location2=location2,
                                                     district=district,month=date2.month,day=date2.day,rainfall=rainfall) 
    
    date3 = add_day(date,3)
    rfr_prediction_day3 , xgb_prediction_day3 = predict_landslide(location=location,location1=location1,location2=location2,
                                                     district=district,month=date3.month,day=date3.day,rainfall=rainfall) 
    
    return jsonify({f'Prediction for {date} RFR':float(rfr_prediction),f"Prediction for {date} XGB":float(xgb_prediction),
                    f'Prediction for {date1} RFR':float(rfr_prediction_day1),f'Prediction for {date1} XGB':float(xgb_prediction_day1),
                    f'Prediction for {date2} RFR':float(rfr_prediction_day2),f'Prediction for {date2} XGB':float(xgb_prediction_day2),
                    f'Prediction for {date3} RFR':float(rfr_prediction_day3),f'Prediction for {date3} XGB':float(xgb_prediction_day3)})
                    
@app.route('/predict_flood',methods=['POST'])
def flood_predict():
    location = request.form.get('Location')
    location1 = request.form.get('Location1')
    location2 = request.form.get('Location2')
    district = request.form.get('District')
    rainfall = float(request.form.get('Rainfall(mm)'))
    
    date = dt.date.today() 
    rfr_prediction,xgb_prediction = predict_flood(location=location,location1=location1,location2=location2,
                                                     district=district,month=date.month,day=date.day,rainfall=rainfall) 
    
    def add_day(date, days_to_add):
        new_date = date + dt.timedelta(days=days_to_add)
        return new_date
    
    date1 = add_day(date,1)
    rfr_prediction_day1 , xgb_prediction_day1 = predict_flood(location=location,location1=location1,location2=location2,
                                                     district=district,month=date1.month,day=date1.day,rainfall=rainfall) 
    
    date2 = add_day(date,2)
    rfr_prediction_day2 , xgb_prediction_day2 = predict_flood(location=location,location1=location1,location2=location2,
                                                     district=district,month=date2.month,day=date2.day,rainfall=rainfall) 
    
    date3 = add_day(date,3)
    rfr_prediction_day3 , xgb_prediction_day3 = predict_flood(location=location,location1=location1,location2=location2,
                                                     district=district,month=date3.month,day=date3.day,rainfall=rainfall) 
    
    return jsonify({f'Prediction for {date} RFR':float(rfr_prediction),f"Prediction for {date} XGB":float(xgb_prediction),
                    f'Prediction for {date1} RFR':float(rfr_prediction_day1),f'Prediction for {date1} XGB':float(xgb_prediction_day1),
                    f'Prediction for {date2} RFR':float(rfr_prediction_day2),f'Prediction for {date2} XGB':float(xgb_prediction_day2),
                    f'Prediction for {date3} RFR':float(rfr_prediction_day3),f'Prediction for {date3} XGB':float(xgb_prediction_day3)})
    
@app.route('/predict_cyclone',methods=['POST'])
def cyclone_predict():
    location = request.form.get('Location')
    location1 = request.form.get('Location1')
    location2 = request.form.get('Location2')
    district = request.form.get('District')
    wind_speed = float(request.form.get('Wind Speed(mph)'))
    
    date = dt.date.today() 
    rfr_prediction,xgb_prediction = predict_cyclone(location=location,location1=location1,location2=location2,
                                                     district=district,month=date.month,day=date.day,wind_speed=wind_speed) 
    
    def add_day(date, days_to_add):
        new_date = date + dt.timedelta(days=days_to_add)
        return new_date
    
    date1 = add_day(date,1)
    rfr_prediction_day1 , xgb_prediction_day1 = predict_cyclone(location=location,location1=location1,location2=location2,
                                                     district=district,month=date1.month,day=date1.day,wind_speed=wind_speed) 
    
    date2 = add_day(date,2)
    rfr_prediction_day2 , xgb_prediction_day2 = predict_cyclone(location=location,location1=location1,location2=location2,
                                                     district=district,month=date2.month,day=date2.day,wind_speed=wind_speed) 
    
    date3 = add_day(date,3)
    rfr_prediction_day3 , xgb_prediction_day3 = predict_cyclone(location=location,location1=location1,location2=location2,
                                                     district=district,month=date3.month,day=date3.day,wind_speed=wind_speed) 
    
    return jsonify({f'Prediction for {date} RFR':float(rfr_prediction),f"Prediction for {date} XGB":float(xgb_prediction),
                    f'Prediction for {date1} RFR':float(rfr_prediction_day1),f'Prediction for {date1} XGB':float(xgb_prediction_day1),
                    f'Prediction for {date2} RFR':float(rfr_prediction_day2),f'Prediction for {date2} XGB':float(xgb_prediction_day2),
                    f'Prediction for {date3} RFR':float(rfr_prediction_day3),f'Prediction for {date3} XGB':float(xgb_prediction_day3)})
if __name__=='__main__':
    app.run(debug=True)

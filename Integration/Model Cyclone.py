import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import mean_squared_error
from xgboost import XGBRegressor
import joblib

rfr_model, ref_columns, target = joblib.load('Cyclone_RFRModel.pkl')
xgb_model, ref_columns, target = joblib.load('Cyclone_XGBModel.pkl')



def predict_disaster(location,location1,location2,district,month,day,rainfall):
    locations = [location,location1,location2]
    for location in locations:
        location_code = mapping_location.get(location)
        if location_code is None:
            continue
        else:
            break
    else:
        location_code = max(mapping_location.values())+1
        
    disaster_code = 0
    district_code = mapping_district.get(district)
    user_data = pd.DataFrame({
        'Disaster' : disaster_code,
        'Location' : location_code,
        'District' : district_code,
        'Month' : int(month),
        'Day' : int(day),
        'Wind Speed(mph)': float(rainfall)
    },index=[0])
    
    rfr_prediction = np.round(rfr_model.predict(user_data),4)
    xgb_prediction = np.round(xgb_model.predict(user_data),4)
    
    return rfr_prediction,xgb_prediction





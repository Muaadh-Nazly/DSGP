# Import libraries
import pandas as pd
import numpy as np
import joblib

#Import models
rfr_model, ref_columns, target = joblib.load('Cyclone_RFRModel.pkl')
xgb_model, ref_columns, target = joblib.load('Cyclone_XGBModel.pkl')

# Read the dictionary for districts and locations
mapping_district = {}
with open ('District_Mapped.txt','r+') as district_file:
    for line in district_file:
        key,value = line.strip().split(':')
        mapping_district[key.strip()] = int(value.strip())
        
mapping_location = {}
with open ('Location_Mapped.txt','r+') as district_file:
    for line in district_file:
        key,value = line.strip().split(':')
        mapping_location[key.strip()] = int(value.strip())
        

# Function to predict the percentage
def predict_cyclone(location,location1,location2,district,month,day,rainfall):
    found= False
    locations = [location,location1,location2]
    for loc in locations:
        for keys in mapping_location.keys():
            if loc in keys: # The location may have two words or typo
                location_code = mapping_location.get(keys)
                found=True
                break
            else:
                continue
        if found:
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
    
    rfr_prediction = rfr_model.predict(user_data)*100
    xgb_prediction = xgb_model.predict(user_data)*100
    
    rfr_prediction = np.round(np.clip(rfr_prediction[0],0.01,99.0),2)
    xgb_prediction = np.round(np.clip(xgb_prediction[0],0.01,99.0),2)
    
    return rfr_prediction,xgb_prediction






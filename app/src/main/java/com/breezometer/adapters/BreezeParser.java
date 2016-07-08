package com.breezometer.adapters;

import android.util.Log;

import com.breezometer.entities.BreezoMeterEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nadzer on 16/03/2016.
 */
public class BreezeParser {
    private String mBreezeResult;
    private BreezoMeterEntity breezoMeter;

    public BreezeParser(String breezeResult) {
        this.mBreezeResult = breezeResult;
    }

    public BreezoMeterEntity getBreezeData() {
        try {
            JSONObject mJsonObject = new JSONObject(mBreezeResult);
            breezoMeter = new BreezoMeterEntity();

            breezoMeter.setAqi(mJsonObject.getInt("country_aqi"));
            breezoMeter.setCountry(mJsonObject.getString("country_name"));
            breezoMeter.setBreezometerAqi(mJsonObject.getInt("breezometer_aqi"));
            breezoMeter.setDescription(mJsonObject.getString("breezometer_description"));
            breezoMeter.setSport(mJsonObject.getJSONObject("random_recommendations").getString("sport"));
            breezoMeter.setHealth(mJsonObject.getJSONObject("random_recommendations").getString("health"));
            breezoMeter.setInside(mJsonObject.getJSONObject("random_recommendations").getString("inside"));
            breezoMeter.setColor(mJsonObject
                    .getString("breezometer_color"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return breezoMeter;
    }




}

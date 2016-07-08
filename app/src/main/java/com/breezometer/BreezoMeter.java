package com.breezometer;

import android.os.AsyncTask;
import android.util.Log;

import com.breezometer.adapters.BreezeParser;
import com.breezometer.entities.BreezoMeterEntity;
import com.breezometer.interfaces.ResultListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nadzer on 16/03/2016.
 */
public class BreezoMeter extends AsyncTask<Double, Void, BreezoMeterEntity> {
    private BreezeParser mParser;
    public ResultListener listener;

    public void setOnResultListener(ResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected BreezoMeterEntity doInBackground(Double... params) {
        BreezoMeterEntity entity = new BreezoMeterEntity();
        HttpURLConnection urlConnection;
        BufferedReader reader;

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://api.breezometer.com/baqi/?lat=");
        urlBuilder.append(params[0]);
        urlBuilder.append("&lon=");
        urlBuilder.append(params[1]);
        urlBuilder.append("&key=91fb161846354745bb8d54648d02f425");

        try {
            URL url = new URL(urlBuilder.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            // Convert result into a String
            String mBreezeResult = buffer.toString();
            mParser = new BreezeParser(mBreezeResult);
            if (!mBreezeResult.contains("\"error\": {\"message\": \"No data available for provided location\"")) {
                entity = mParser.getBreezeData();
            } else {
                entity.setInside("No data available");
                entity.setHealth("No data available");
                entity.setCountry("No data available");
                entity.setDescription("No data available");
                entity.setSport("No data available");
                entity.setColor("#641515");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    protected void onPostExecute(BreezoMeterEntity entity) {
        super.onPostExecute(entity);
        listener.onSuccessfulReturn(entity);
    }
}

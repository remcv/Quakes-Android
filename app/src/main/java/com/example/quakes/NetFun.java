package com.example.quakes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

class NetFun
{
    // some static methods
    static JSONObject urlToJson(String stringURL)
    {
        try
        {
            HttpURLConnection huc = (HttpURLConnection) (new URL(stringURL)).openConnection();
            huc.setRequestMethod("GET");
            huc.setConnectTimeout(10_000);
            huc.setReadTimeout(10_000);
            huc.connect();

            if (huc.getResponseCode() == 200)
            {
                try(BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream())))
                {
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null)
                    {
                        sb.append(line);
                    }

                    return new JSONObject(sb.toString());
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            else
            {
                return null;
            }

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    static List<EarthquakeObservation> jsonToList(JSONObject obj)
    {
        if (obj == null)
        {
            return null;
        }

        int count;
        String place;
        double magnitude;
        LocalDateTime date;
        String linkToUSGS;
        List<EarthquakeObservation> database;

        try
        {
            count = obj.getJSONObject("metadata").getInt("count");

            if (count == 0)
            {
                return null;
            }

            database = new ArrayList<EarthquakeObservation>();

            for (int i = 0; i < count; ++i)
            {
                place = obj.getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("place");
                magnitude = obj.getJSONArray("features").getJSONObject(i).getJSONObject("properties").getDouble("mag");
                date = Instant.ofEpochMilli(obj.getJSONArray("features").getJSONObject(i).getJSONObject("properties").getLong("time")).atZone(ZoneId.systemDefault()).toLocalDateTime();
                linkToUSGS = obj.getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("url");

                database.add(new EarthquakeObservation(place, magnitude, date, linkToUSGS));
            }
        }
        catch (JSONException e)
        {
            Log.d("Quakes", "JSONException thrown");
            return null;
        }

        return database;
    }
}

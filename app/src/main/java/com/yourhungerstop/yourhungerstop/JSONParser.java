package com.yourhungerstop.yourhungerstop;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Deepa on 25-05-2016.
 */
public class JSONParser {


    /**
     * This method connects to the URL provided as input parameter to it and reads the data from the
     * URL. This data is further converted to a JSONObject and returned
     *
     * @param targetUrl : URL from where data needs to be retrieved
     * @return JSONObject : containing data from the URL
     */
    public JSONObject getJSONFromUrl(String targetUrl) {
        JSONObject jObject = null;
        HttpURLConnection urlConnection;
        StringBuilder result = new StringBuilder();
        String json = "";

        try {
            // Create URLConnection object with the url passed to the method by the main UI thread
            URL url = new URL(targetUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

            //read the data from the URL

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            json = result.toString();
        } catch (MalformedURLException e) {
            Log.e("URL data error", "JSONParser.getJSONFromUrl: URL is invalid" + e.toString());
        } catch (SocketTimeoutException e) {
            Log.e("URL data error", "JSONParser.getJSONFromUrl: Data retrieval or connection timed out" + e.toString());
        } catch (IOException e) {
            Log.e("URL data error", "JSONParser.getJSONFromUrl: Could not read response body" + e.toString());
        } catch (Exception e) {
            Log.e("URL data error", "JSONParser.getJSONFromUrl" + e.toString());
        }

        //create json object from the data received from the input stream
        try {
            jObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "JSONParser.getJSONFromUrl: Error parsing data " + e.toString());
        }
        return jObject;
    }

}

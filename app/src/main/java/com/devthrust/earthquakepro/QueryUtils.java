package com.devthrust.earthquakepro;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static String SAMPLE_JSON_RESPONSE ;
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquakes} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquakes> extractEarthquakes(String sampleJsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquakes> earthquakes = new ArrayList<>();
        if (TextUtils.isEmpty(sampleJsonResponse)){
            Log.e("extract earthquake","textUtil is empty");
            return null;
        }

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(sampleJsonResponse);
            JSONArray features = root.getJSONArray("features");
            for (int i = 0; i < features.length(); i++){
                JSONObject arrayObject = features.getJSONObject(i);
                JSONObject properties = arrayObject.getJSONObject("properties");
                earthquakes.add(new Earthquakes(properties.getDouble("mag"),properties.getString("place"),properties.getLong("time")));
            }
            Log.i("QueryUtils", "Message displayed");



        } catch (JSONException e) {

            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;

    }
    public static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        }catch (Exception ex){
            return null;
        }
        return url;
    }

    public static String makeHttpResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = null ;
        Log.e("makeHttpResponse",url.toString());
        String jsonResponse="";
        InputStream inputStream = null;
        if (url==null){
            return jsonResponse;
        }
        try{
            Log.e("makeHttpResponse","start connection");
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.e("makeHttpResponse","open connection");
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(6000);
            urlConnection.connect();

            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e("makeHttpResponse","no server response");
            }




        }catch (Exception ex){

            Log.e("MakeHttpRequest",ex.getLocalizedMessage());
        }
        finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }


        return jsonResponse;

    }
    private static String readFromStream(InputStream inputStream){
        StringBuilder response = new StringBuilder();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String responses = bufferedReader.readLine();
            while (responses != null){
                response.append(responses);
                responses = bufferedReader.readLine();
            }
        }catch (IOException ex){

        }

        return response.toString();

    }

}

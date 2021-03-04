package com.crispereira.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientMovie {
    private static String readStream(InputStream in){
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('n');
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    private static String request(String stringUrl) throws IOException {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        }
        finally {
            urlConnection.disconnect();
        }
    }

    public static Movie returnMovie(String movie) throws JSONException, IOException {
        String responseBody = request("http://www.omdbapi.com/?apikey=d61dfeb6&t="+movie);
        JSONObject obj = new JSONObject(responseBody);
        String title = obj.get("Title").toString();
        String year = obj.get("Year").toString();
        String plot = obj.get("Plot").toString();
        return new Movie(title, year, plot);
    }

}
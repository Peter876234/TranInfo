package hk.edu.hkmu.contactinfo;

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
import java.net.ProtocolException;
import java.net.URL;

public class JsonHandlerThread extends Thread {
    private static final String TAG = "JsonHandlerThread";
    private static final String ACTIVITY_TAG="LogDemo";
    // URL to get contacts JSON file
    private static String jsonUrl = "https://www.lcsd.gov.hk/datagovhk/facility/facility-fw.json";

    // send request to the url, no need to be changed

    private static String langage;

    public JsonHandlerThread(String lang){
        langage = lang;
    }

    public static String makeRequest() {
        String response = null;
        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = inputStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    // download of JSON file from the url to the app
    private static String inputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            }
        }
        return sb.toString();
    }

    public void run() {
        // "contactStr" variable store the json file content
        String trackStr = makeRequest();
        //Log.e(TAG, "Response from url: " + trackStr);

        if (trackStr != null) {
            try {
                JSONArray jsonArr = new JSONArray(trackStr);
                // looping through All Contacts
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    String title = jsonObj.getString("Title_"+langage);
                    String district = jsonObj.getString("District_"+langage);
                    String route = jsonObj.getString("Route_"+langage).split("<br>")[0];
                    String Calorie = jsonObj.getString("Route_"+langage).split("<br>")[1];
                    String howToAccess = jsonObj.getString("HowToAccess_"+langage);
                    String mapurl = jsonObj.getString("MapURL_"+langage);
                    String latitude = jsonObj.getString("Latitude");
                    String longitude = jsonObj.getString("Longitude");

                    // Add contact (name, email, address) to contact list
                    TrackInfo.addTrack(title, district, route, Calorie, howToAccess, mapurl, latitude, longitude);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}
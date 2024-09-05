package hk.edu.hkmu.contactinfo;

import java.util.ArrayList;
import java.util.HashMap;

// A utility class for creating contact list
public class TrackInfo {
    public static String TITLE = "Title"; // used in "new String []..." in MainActivity program
    public static String DISTRICT = "District"; // used in "new String []..." in MainActivity program
    public static String ROUTE = "Route"; // used in "new String []..." in MainActivity program
    public static String CALORIE = "Calorie";
    public static String HOWTOACCESS = "HowToAccess"; // used in "new String []..." in MainActivity program
    public static String MAPURL = "MapUrl";
    public static String LATITUDE = "Latitude";
    public static String LONGITUDE = "Longitude";


    // "contactList" variable used for storing all contact that retrieved from JSON
    // it is used in JsonHandlerThread and also MainActivity program
    public static ArrayList<HashMap<String, String>> trackList = new ArrayList<>();

    // addContact is a function
    // Creates and add contact to contact list
    // x4 input, representing name, email, address and mobile
    public static ArrayList<String> districtList = new ArrayList<>();

    public static void addTrack(String title, String district, String route, String Calorie, String howToAccess, String MapUrl, String latitude, String longitude) {
        // Create contact
        HashMap<String, String> track = new HashMap<>();
        track.put(TITLE, title);
        track.put(DISTRICT, district);
        track.put(ROUTE, route);
        track.put(CALORIE, Calorie);
        track.put(HOWTOACCESS, howToAccess);
        track.put(MAPURL, MapUrl);
        track.put(LATITUDE, latitude);
        track.put(LONGITUDE, longitude);


        // Add contact to contact list
        trackList.add(track);

        if(districtList.indexOf(district)==-1) {
            districtList.add(district);
        }
    }
}

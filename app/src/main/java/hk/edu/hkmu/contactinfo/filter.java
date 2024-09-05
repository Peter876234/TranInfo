package hk.edu.hkmu.contactinfo;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class filter {
    public static ArrayList filter_data(ArrayList<HashMap<String, String>> List, String key){
        ArrayList temp_list = new ArrayList();
        if(!(key.equals("所有")  || key.equals("All"))) {
            for (int i = 0; i < List.size(); i++) {
                if (List.get(i).get("District").equals(key)){
                    temp_list.add(List.get(i));
                }
            }
            return temp_list;
        } else {
            return TrackInfo.trackList;

        }
    }
    public static ArrayList get_search_data(ArrayList<HashMap<String, String>> List, String key){
        if (!key.isEmpty()){
            ArrayList temp_list = new ArrayList<>();
            for (int i = 0; i < List.size(); i++) {
                HashMap<String, String> track = (HashMap<String, String>) List.get(i);
                if (track.get(TrackInfo.TITLE).toLowerCase().contains(key.toLowerCase())){
                    temp_list.add(TrackInfo.trackList.get(i));
                }
            }
            return temp_list;
        }else {
            return List;
        }
    }

    public static ArrayList filter_search_data(ArrayList<HashMap<String, String>> List, String search_key, String filter_key){
        ArrayList temp_list = new ArrayList<>();
        Log.e("Filter", ""+filter_key);
        Log.e("search", ""+search_key);
        Log.e("size", ""+List.size());
        for (int i = 0; i < List.size(); i++) {
            HashMap<String, String> track = (HashMap<String, String>) List.get(i);
            if (track.get(TrackInfo.DISTRICT).equals(filter_key) & track.get(TrackInfo.TITLE).toLowerCase().contains(search_key.toLowerCase())){
                temp_list.add(TrackInfo.trackList.get(i));
                Log.e("test",""+track.get(TrackInfo.TITLE));
            }
        }
        return temp_list;
    }
}

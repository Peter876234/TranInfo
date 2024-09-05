package hk.edu.hkmu.contactinfo;

import static hk.edu.hkmu.contactinfo.TrackInfo.districtList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static boolean first = true;
    public static String lang;
    private final String TAG = "MainActivity";
    public static ArrayList showlist = TrackInfo.trackList;
    public static String selectedDistrict = "";
    public static String key = "";
    private ListView listView; // ui component for displaying all contacts (x8 contacts)
    // spinner
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (first) {
            Log.v(TAG,Locale.getDefault().toLanguageTag());
            switch (Locale.getDefault().toLanguageTag()) {
                case "zh-CHT":
                case "zh-Hant-HK":
                case "zh-Hant-MO":
                case "zh-Hant-TW":
                    lang = "tc";
                    break;
                case "zh-CHS":
                case "zh-Hans-SG":
                case "zh-Hans-CN":
                    lang = "sc";
                    break;
                default:
                    lang = "en";
                    break;
            }
            first = false;
        }

        switch(lang){
            case "tc":
                setTitle("步行徑");
                break;
            case "sc":
                setTitle("步行徑");
                break;
            case "en":
                setTitle("TrackInfo");
                break;
        }

        // initialize the listView ui component by using findViewById method
        listView = (ListView) findViewById(R.id.listview);

        // Get contact list with thread class
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(lang);
        jsonHandlerThread.start();

        try {
            TrackInfo.districtList.clear();
            TrackInfo.trackList.clear();
            if(lang.equals("en")){
                TrackInfo.districtList.add("All");
            }else{
                TrackInfo.districtList.add("所有");
            }
            jsonHandlerThread.join();


            spinner = findViewById(R.id.spinner);
            ArrayAdapter<String> adapter2= new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,districtList);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinner.setAdapter(adapter2);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedDistrict = districtList.get(position);
                    Toast.makeText(MainActivity.this, selectedDistrict, Toast.LENGTH_SHORT).show();

                    if (!key.equals("") & (selectedDistrict=="All" || selectedDistrict=="所有") ){
                        AP_data(filter.get_search_data(TrackInfo.trackList, key));
                        Log.e(TAG,"test1");
                    } else if (!key.equals("") & !(selectedDistrict=="All" || selectedDistrict=="所有") ) {
                        AP_data(filter.filter_search_data(TrackInfo.trackList, key, selectedDistrict));
                        Log.e(TAG,"test2");
                    } else if (key.equals("") & !(selectedDistrict=="All" || selectedDistrict=="所有")){
                        AP_data(filter.filter_data(TrackInfo.trackList, selectedDistrict));
                        Log.e(TAG,"test3");
                    }else {
                        AP_data(TrackInfo.trackList);
                    }
//
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // It is a build-in function ,cannot be deleted
                }


            });
            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> track = (HashMap<String, String>) showlist.get(position);

                            String TITLE = track.get(TrackInfo.TITLE);
                            String DISTRICT = track.get(TrackInfo.DISTRICT);
                            String ROUTE = track.get(TrackInfo.ROUTE);
                            String CALORIE = track.get(TrackInfo.CALORIE);
                            String HOW_TO_ACCESS = track.get(TrackInfo.HOWTOACCESS);
                            String MAPURL = track.get(TrackInfo.MAPURL);
                            String LATITUDE = track.get(TrackInfo.LATITUDE);
                            String LONGITUDE = track.get(TrackInfo.LONGITUDE);

                            Intent intent = new Intent(MainActivity.this, detail_info.class);
                            intent.putExtra("TITLE",TITLE);
                            intent.putExtra("DISTRICT",DISTRICT);
                            intent.putExtra("ROUTE",ROUTE);
                            intent.putExtra("CALORIE",CALORIE);
                            intent.putExtra("HOW_TO_ACCESS",HOW_TO_ACCESS);
                            intent.putExtra("MAPURL",MAPURL);
                            intent.putExtra("LATITUDE",LATITUDE);
                            intent.putExtra("LONGITUDE",LONGITUDE);

                            switch(lang){
                                case "tc":
                                    intent.putExtra("subtitle", "交通:");
                                    break;
                                case "sc":
                                    intent.putExtra("subtitle", "交通:");
                                    break;
                                case "en":
                                    intent.putExtra("subtitle", "Transportation:");
                                    break;
                            }

                            startActivity(intent);
                        }
                    }
            );
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException: " + e.getMessage());
        }

    }


    @Override               //Search and menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem setting=menu.getItem(1);
        MenuItem help=menu.getItem(2);
        MenuItem about=menu.getItem(3);

        switch(lang){
            case "tc":
                setting.setTitle("設定");
                help.setTitle("幫助");
                about.setTitle("關於");
                break;
            case "sc":
                setting.setTitle("设定");
                help.setTitle("帮助");
                about.setTitle("关于");
                break;
            case "en":
                setting.setTitle("Setting");
                help.setTitle("Help");
                about.setTitle("About");
                break;
        }

        SearchView searchView= (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                key = query;
                if (!key.equals("") & (selectedDistrict=="All" || selectedDistrict=="所有") ){
                    AP_data(filter.get_search_data(TrackInfo.trackList, key));
                    Log.e(TAG,"test4");
                } else if (!key.equals("") & !(selectedDistrict=="All" || selectedDistrict=="所有") ) {
                    AP_data(filter.filter_search_data(TrackInfo.trackList, key, selectedDistrict));
                    Log.e(TAG,"test5");
                } else if (key.equals("") & !(selectedDistrict=="All" || selectedDistrict=="所有")){
                    AP_data(filter.filter_data(TrackInfo.trackList, selectedDistrict));
                    Log.e(TAG,"test6");
                }else {
                    AP_data(TrackInfo.trackList);
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                key = newText;
                if (!key.equals("") & (selectedDistrict=="All" || selectedDistrict=="所有") ){
                    AP_data(filter.get_search_data(TrackInfo.trackList, key));
                    Log.e(TAG,"test7");
                } else if (!key.equals("") & !(selectedDistrict=="All" || selectedDistrict=="所有") ) {
                    AP_data(filter.filter_search_data(TrackInfo.trackList, key, selectedDistrict));
                    Log.e(TAG,"test8");
                } else if (key.equals("") & !(selectedDistrict=="All" || selectedDistrict=="所有")){
                    AP_data(filter.filter_data(TrackInfo.trackList, selectedDistrict));
                    Log.e(TAG,"test9");
                }else {
                    AP_data(TrackInfo.trackList);
                }
                return false;
            }
        });


        return true;
    }

    private void AP_data(ArrayList<HashMap<String, String>> list){
        listView.setAdapter(null);
        ArrayList temp_list = new ArrayList<>();
        showlist = list;

        if (list.size() > 1){
            listView.setVisibility(View.VISIBLE);
            findViewById(R.id.search_result).setVisibility(View.GONE);
            if (list.get(0).equals(list.get(list.size()/2))){
                for (int i = 0; i < list.size()/2; i++) {
                    temp_list.add(list.get(i));
                }
                list = temp_list;
                showlist = list;
            }

            SimpleAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    list,  // "contactList" that stores all the retrieved contacts, defined in ContactInfo
                    R.layout.list_view_layout, // layout resource represent item layout design
                    new String[]{TrackInfo.TITLE, TrackInfo.DISTRICT, TrackInfo.ROUTE, TrackInfo.CALORIE, TrackInfo.HOWTOACCESS, TrackInfo.MAPURL, TrackInfo.LATITUDE, TrackInfo.LONGITUDE},  // represent the three data that display in an item
                    new int[]{R.id.title, R.id.district, R.id.route}  // represent where the item is displayed
            );
            listView.setAdapter(adapter);

        } else if (list.size() == 0) {

            listView.setVisibility(View.GONE);
            findViewById(R.id.search_result).setVisibility(View.VISIBLE);
            TextView search = (TextView) findViewById(R.id.search_result);
            switch(lang){
                case "tc":
                    search.setText("沒有符合的搜尋結果");
                    break;
                case "sc":
                    search.setText("没有符合的搜索结果");
                    break;
                case "en":
                    search.setText("No matching results");
                    break;
            }



        }else {
            SimpleAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    list,  // "contactList" that stores all the retrieved contacts, defined in ContactInfo
                    R.layout.list_view_layout, // layout resource represent item layout design
                    new String[]{TrackInfo.TITLE, TrackInfo.DISTRICT, TrackInfo.ROUTE, TrackInfo.CALORIE, TrackInfo.HOWTOACCESS, TrackInfo.MAPURL, TrackInfo.LATITUDE, TrackInfo.LONGITUDE},  // represent the three data that display in an item
                    new int[]{R.id.title, R.id.district, R.id.route}  // represent where the item is displayed
            );
            listView.setAdapter(adapter);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 取得點選項目的id

        int id = item.getItemId();

        // 依照id判斷點了哪個項目並做相應事件
        if (id == R.id.setting) {
            // 按下「設定」要做的事
            Toast.makeText(this, "設定", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, preference.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.help) {
            // 按下「使用說明」要做的事
            Intent intent = new Intent(MainActivity.this, Help.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.about) {
            // 按下「關於」要做的事
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
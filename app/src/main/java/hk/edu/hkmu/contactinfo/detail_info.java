package hk.edu.hkmu.contactinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class detail_info extends AppCompatActivity {

    private ListView listView;
    private ImageView Image_map;
    private String TAG="Detail_info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        Image_map = findViewById(R.id.map);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (extras != null) {

            String TITLE = extras.getString("TITLE");
            String DISTRICT = extras.getString("DISTRICT");
            String ROUTE = extras.getString("ROUTE");
            String CALORIE = extras.getString("CALORIE");
            String HOW_TO_ACCESS = extras.getString("HOW_TO_ACCESS");
            String MAPURL = extras.getString("MAPURL");
            String LATITUDE = extras.getString("LATITUDE");
            String LONGITUDE = extras.getString("LONGITUDE");
            String subtitle = extras.getString("subtitle");
            //spilt the <br> (pre kilo)

            String[] access = HOW_TO_ACCESS.split("<br>");
            getSupportActionBar().setTitle(TITLE);

            //display
            TextView tvId1 = (TextView) findViewById(R.id.name);
            tvId1.setText(TITLE);

            TextView tvId2 = (TextView) findViewById(R.id.pre_kilo);
            tvId2.setText(CALORIE);

            TextView tvId3 = (TextView) findViewById(R.id.district);
            tvId3.setText(DISTRICT);

            TextView tvId4 = (TextView) findViewById(R.id.route);
            tvId4.setText(ROUTE);


            TextView tvId5 = (TextView) findViewById(R.id.Subtitle);
            tvId5.setText(subtitle);

            listView = (ListView) findViewById(R.id.access);
            final ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < access.length; ++i) {
                list.add(access[i]);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,list);

            listView.setAdapter(adapter);

            Glide.with(this).load(MAPURL).into(Image_map);

            Image_map.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
//              open google map
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+LATITUDE + "," + LONGITUDE));
                    startActivity(intent);
                }
            });

        }



    }
}
package hk.edu.hkmu.contactinfo;

import static hk.edu.hkmu.contactinfo.MainActivity.lang;
import static hk.edu.hkmu.contactinfo.TrackInfo.districtList;
import static hk.edu.hkmu.contactinfo.TrackInfo.trackList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class preference extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        if (lang == "en"){
            getSupportActionBar().setTitle("Setting");
        } else if (lang == "sc") {
            getSupportActionBar().setTitle("设定");
        } else if (lang == "tc") {
            getSupportActionBar().setTitle("設定");
        }

        //Setting Page
        RadioButton tc = (RadioButton)findViewById(R.id.tc);
        RadioButton sc = (RadioButton)findViewById(R.id.sc);
        RadioButton en = (RadioButton)findViewById(R.id.en);
        Button btn = (Button)findViewById(R.id.getBtn);

        switch(lang){
            case "tc":
                tc.setChecked(true);
                btn.setText("確定");
                break;
            case "sc":
                sc.setChecked(true);
                btn.setText("确定");
                break;
            case "en":
                en.setChecked(true);
                btn.setText("Confirm");
                break;
        }

        tc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn.setText("確定");
            }
        });

        sc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn.setText("确定");
            }
        });

        en.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn.setText("Confirm");
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "Selected Language: ";
                result+= (tc.isChecked())?"Traditional Chinese":(sc.isChecked())?"Simplified Chinese":(en.isChecked())?"English":"";
                lang = (tc.isChecked())?"tc":(sc.isChecked())?"sc":(en.isChecked())?"en":"";
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                trackList.clear();
                //list
                districtList.clear();
                Intent mainIntent = new Intent(preference.this, MainActivity.class);
                MainActivity.key="";
                startActivity(mainIntent);
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tc:
                if(checked)
                    str = "Traditional Chinese Selected";
                break;
            case R.id.sc:
                if(checked)
                    str = "Simplified Chinese Selected";
                break;
            case R.id.en:
                if(checked)
                    str = "English Selected";
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
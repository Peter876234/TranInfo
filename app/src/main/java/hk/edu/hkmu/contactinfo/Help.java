package hk.edu.hkmu.contactinfo;

import static hk.edu.hkmu.contactinfo.MainActivity.lang;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        switch (lang) {
            case "tc":
                getSupportActionBar().setTitle("幫助");
                break;
            case "sc":
                getSupportActionBar().setTitle("帮助");
                break;
            case "en":
                getSupportActionBar().setTitle("Help");
                break;
        }

        switch (lang) {
            case "tc":
                TextView yourson1 = (TextView) findViewById(R.id.name);
                yourson1.setText("如果您有任何問題，請您電郵至 s128319@live.hkmu.edu.hk");
                break;
            case "sc":
                TextView yourson2 = (TextView) findViewById(R.id.name);
                yourson2.setText("如果您有任何问题，请您电邮至 s128319@live.hkmu.edu.hk");
                break;
            case "en":
                TextView yourson3 = (TextView) findViewById(R.id.name);
                yourson3.setText("If you have any questions, please email to s128319@live.hkmu.edu.hk");
                break;
        }


    }
}
package hk.edu.hkmu.contactinfo;

import static hk.edu.hkmu.contactinfo.MainActivity.lang;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        switch (lang) {
            case "tc":
                getSupportActionBar().setTitle("關於");
                break;
            case "sc":
                getSupportActionBar().setTitle("关于");
                break;
            case "en":
                getSupportActionBar().setTitle("About");
                break;
        }

        switch (lang) {
            case "tc":
                TextView yourson1 = (TextView) findViewById(R.id.About);
                yourson1.setText("步行徑APP可以向人們提供來自康樂及文化事務署的最新健身步行徑資訊。其目的是幫助人們找到適合的步行路徑，提高步行安全性。總的來說，步行徑APP可以提供關於健身步行徑的資訊，是促進身體活動、改善健康成果和建立志同道合社群的有價值工具。");
                break;
            case "sc":
                TextView yourson2 = (TextView) findViewById(R.id.About);
                yourson2.setText("步行徑APP可以向人们提供来自康乐及文化事务署的最新健身步行径资讯。其目的是帮助人们找到适合的步行路径，提高步行安全性。总的来说，步行徑APP可以提供关于健身步行径的资讯，是促进身体活动、改善健康成果和建立志同道合社群的有价值工具。");
                break;
            case "en":
                TextView yourson3 = (TextView) findViewById(R.id.About);
                yourson3.setText("TraceInfo APP can inform people about the latest fitness walking tracks information from LEISURE AND CULTURAL SERVICES DEPARTMENT. The aim is helping people find suitable walking routes and increasing the walking safety. Overall, TraceInfo APP can provide information about fitness walking tracks can be a valuable tool for promoting physical activity, improving health outcomes, and building a community of like-minded individuals.");
                break;
        }


    }
}
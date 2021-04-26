package example.com.newapp.Views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.w3c.dom.Text;

import example.com.newapp.R;
import example.com.newapp.Utils.InternetCheckService;
import example.com.newapp.Utils.SharedPref;

/*
    Bu aktiviti programın geçiş ekranıdır. Yarım saniye gösterim yapıldıktan sonra Main Activity'e geçecektir.
    İlk girişi ise internet kontrolü yapılıp, eğer internet kontrolü sonucunda telefonda wifi veya mobil veri
    açık ise yarım saniye sonra Main Activity'e geçip "İlk Giriş" bayrağını değiştirecektir. İlk girişten sonraki tüm
    girişlerde internet kontrolü yapılmayacaktır.
 */
/*

    This activity is the transition screen of the program. After half a second of screening, it will switch to Main Activity.
    The first entry is internet control, if after internet control, wifi or mobile data on the phone
    If it is on, it will switch to Main Activity after half a second and change the "First Entry" flag. All after first login
    Internet control will not be made at the entrances.
 */

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "Splash Screen";
    private SharedPref sharedPref;
    private TextView internetCheckTw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initialUI();
        if(!sharedPref.getIsFirst().equals("true")){
            new Handler().postDelayed(this::goActivityWithOther, 500);

            }
        else{
            internetChecking();
            new Handler().postDelayed(this::goActivityWithFirst, 500);

        }
    }
    public void internetChecking() {

        Intent intent= new Intent(this, InternetCheckService.class);
        intent.setAction("checkinternet");
        startService(intent);
    }
    private void initialUI() {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        internetCheckTw = findViewById(R.id.isInternetEnable);
        sharedPref = SharedPref.getInstance(this);
        if (sharedPref.getDarkmode().equals("on")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
    private void goActivityWithOther() {
            Intent goMain = new Intent(SplashScreen.this, MainActivity.class);
            SplashScreen.this.startActivity(goMain);
            finish();
        }


        private void goActivityWithFirst() {
        if(sharedPref.getCONNECT_STATUS().equals("true")){
            sharedPref.setIsFirst("false");
            Intent goMain = new Intent(SplashScreen.this, MainActivity.class);
            SplashScreen.this.startActivity(goMain);
            finish();
        }
        else{
            internetCheckTw.setText(getString(R.string.internet));
        }


    }


}
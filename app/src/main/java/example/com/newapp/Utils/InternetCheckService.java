package example.com.newapp.Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import example.com.newapp.R;
/*
    Bu Class internet kontrolü servisidir. Bir ArkaPlan Servisidir.
 */
/*
    This is Class internet control service. It is a Background Service.
 */

public class InternetCheckService extends Service {
    public static final String BroadCastStringForAction="checkinternet";
    private int a=1;

    public InternetCheckService(){
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(periodicupdate);
        return START_STICKY;
    }
    public boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {

            return true;
        } else {
            return false;
        }
    }
    Handler handler= new Handler();
    private Runnable periodicupdate= new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicupdate,1*1000- SystemClock.elapsedRealtime()%1000);
            Intent broadCastIntent= new Intent();
            broadCastIntent.setAction(InternetCheckService.BroadCastStringForAction);
            broadCastIntent.putExtra("online_status",""+isOnline(InternetCheckService.this));
            if(isOnline(InternetCheckService.this)){
                Log.d("isOnline", ""+isOnline(InternetCheckService.this));
                SharedPref.getInstance(getApplicationContext()).setCONNECT_STATUS(String.valueOf(isOnline(getApplicationContext())));
                a=1;

            }
            else{
                if(a==1){
                    Log.d("TAG", "run: FALSE"+ isOnline(InternetCheckService.this));
                    SharedPref.getInstance(getApplicationContext()).setCONNECT_STATUS(String.valueOf(isOnline(getApplicationContext())));
                    a=0;
                }


            }
            sendBroadcast(broadCastIntent);
        }
    };

    public interface internetCheckListener {
        public void internetCheckListener();
        public void internetCheckListener(String value);
    }
}


package example.com.newapp.Views;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import example.com.newapp.DataBase.Team.Team;
import example.com.newapp.Models.Datum;
import example.com.newapp.Models.TeamModel;
import example.com.newapp.R;
import example.com.newapp.Utils.Api;
import example.com.newapp.Utils.ApiServices.GetService;
import example.com.newapp.Utils.SharedPref;
import example.com.newapp.ViewModels.TeamViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
    Bu aktivite ana aktivitedir. Bu aktivite'de takımlar API'den çekilip "Teams" Modeline uygun olarak takımlar listelenmektedir.
    İlk kez giriş yapıldığında internet bu sebepten gereklidir. 2. giriş ve daha sonralarında yapılan girişlerde API request'i atılmayıp
    veritabanında bulunan bilgileri gösterecektir.
*/
/*
    This activity is the main activity. In this activity, teams are withdrawn from the API and the teams are listed in accordance with the "Teams" Model.
    Internet is necessary for the first time. API request is not sent in the 2nd login and the entries made afterwards.
    will display the information contained in the database.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main Activity";
    private Button teamShowButton, fixtureShowButton;
    private static final String TOKEN = "iYQo3HTAU0hmSXmWgwTvopNxWVlIjrYUIh9xbXNvUfrV2KtHND4nDSJyOwyG";
    private Switch darkModeSwitch;
    private SharedPref sharedPref;
    private LottieAnimationView animationView;
    private TeamViewModel teamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initalUI();
        if(Integer.parseInt(sharedPref.getTeamsSize())==0){
            fetchTeams(TOKEN);
        }
        else{
        }

        Switch_Change();
        if (sharedPref.getDarkmode().equals("on")) {
            darkModeSwitch.setChecked(true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            darkModeSwitch.setChecked(false);
        }


    }

    private void initalUI() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        sharedPref = SharedPref.getInstance(getApplicationContext());
        teamShowButton = findViewById(R.id.teamsButton);
        teamShowButton.setOnClickListener(this);
        fixtureShowButton = findViewById(R.id.fixtureButton);
        fixtureShowButton.setOnClickListener(this);
        darkModeSwitch = findViewById(R.id.darkMode);
        animationView = findViewById(R.id.animationView);

    }
    private void fetchTeams(String token) {
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        GetService service = Api.createService(GetService.class);
        Call<TeamModel> call = service.WhatIsTeam(token);

        call.enqueue(new Callback<TeamModel>() {
            @Override
            public void onResponse(Call<TeamModel> call, Response<TeamModel> response) {
                List<Datum> teamDatumList;
                if (response.isSuccessful()) {
                    if (response != null) {
                        teamDatumList = response.body().getData();
                        if (Integer.parseInt(sharedPref.getTeamsSize()) == response.body().getData().size()) {
                        } else {
                            for (int i = 0; i < teamDatumList.size(); i++) {
                                String team = teamDatumList.get(i).toString();
                                String[] teamSplit = team.split("!");
                                Team teamInıt = new Team(Integer.parseInt(teamSplit[0]), teamSplit[1]);
                                teamViewModel.insert(teamInıt);
                            }
                            sharedPref.setTeamsSize(String.valueOf(response.body().getData().size()));
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // takeAction();

                            }
                        }, 3000);

                    }
                    else{
                        Toast.makeText(getApplicationContext(),getString(R.string.wentwrong),Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<TeamModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),getString(R.string.wentwrong),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == teamShowButton) {
            Intent intent = new Intent(this, TeamsList.class);
            goActivity(intent, "teams");

        }
        if (v == fixtureShowButton) {
            Intent intent = new Intent(this, ShowFixture.class);
            goActivity(intent, "fixture");
        }
    }

    private void goActivity(Intent intent, String type) {
        showAnimation(type);
        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                hideAnimation();
                startActivity(intent);
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();
    }

    private void showAnimation(String type) {
        if (type.equals("fixture")) {
            animationView.setAnimation(R.raw.fixtureanimation);
            animationView.setVisibility(View.VISIBLE);
        } else if (type.equals("teams")) {
            animationView.setAnimation(R.raw.teamsanimation);
            animationView.setVisibility(View.VISIBLE);
        }

    }

    private void hideAnimation() {
        animationView.setVisibility(View.GONE);
    }

    public void Switch_Change() {
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (darkModeSwitch.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Dark Mode Enable", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPref.setDarkmode("on");
                } else {
                    Toast.makeText(getApplicationContext(), "Dark Mode Disable", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPref.setDarkmode("off");
                }
            }
        });
    }
}
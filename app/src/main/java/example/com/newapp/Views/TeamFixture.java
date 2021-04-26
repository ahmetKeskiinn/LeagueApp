package example.com.newapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import example.com.newapp.Adapters.TeamFixtureAdapter;
import example.com.newapp.DataBase.Fixture.Fixture;
import example.com.newapp.R;
import example.com.newapp.Utils.SharedPref;
import example.com.newapp.ViewModels.FixtureViewModel;

/*
    Bu Aktiviti'de takımların kendi fikstürleri gösterilecektir. Takımlar sayfasından herhangi bir takıma basıldığında
    bu Aktiviti'ye takımın ismi ve ID'si gönderiliyor. Bu isim ve ID'ler kullanılarak veritabanından takımların hangi hafta
    hangi maçı yapacağı gösterilecektir.
 */
/*

    Teams' own fixtures will be displayed in this Activity. Pressing any team from the teams page
    Team name and ID are sent to this Activity. Using these names and IDs, what week
    He will be shown which match he will play.
 */

public class TeamFixture extends AppCompatActivity {
    private TextView teamName;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private FixtureViewModel fixtureViewModel;
    private String team;
    private SharedPref sharedPref;
    private String teamID;
    private TeamFixtureAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_team_fixture);

        teamID = intent.getStringExtra("id");
        team = intent.getStringExtra("name");

        initialUI();

        sharedPref.setSELECETED_TEAM(team);
    }
    private void initialUI(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        sharedPref= SharedPref.getInstance(this);
        teamName = findViewById(R.id.teamName);
        teamName.setText(team);
        adapter = new TeamFixtureAdapter();
        fixtureViewModel =  ViewModelProviders.of(this).get(FixtureViewModel.class);
        fixtureViewModel.getAllTeamFixture(team).observe(this,new Observer<List<Fixture>>() {
            @Override
            public void onChanged(List<Fixture> fixtures) {
                List<Fixture> list = new ArrayList<>();
                if (fixtures.size() == 0){
                    Toast.makeText(getApplicationContext(),getString(R.string.teamFix),Toast.LENGTH_SHORT).show();
                }
                for (int i =0;i<fixtures.size();i++){
                    Fixture fixture = new Fixture(fixtures.get(i).getId(),fixtures.get(i).getTitle(),fixtures.get(i).getWeek());
                    list.add(fixture);
                }
                adapter.submitList(list);
                initRecycler();
            }

        });
    }
    private void initRecycler(){
        RecyclerView recyclerView = findViewById(R.id.teamFixture);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
    }
}
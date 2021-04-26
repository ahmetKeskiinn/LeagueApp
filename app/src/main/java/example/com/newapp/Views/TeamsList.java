package example.com.newapp.Views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import example.com.newapp.Adapters.TeamAdapter;
import example.com.newapp.DataBase.Team.Team;

import example.com.newapp.R;

import example.com.newapp.Utils.SharedPref;
import example.com.newapp.ViewModels.TeamViewModel;

/*
    Bu aktiviti'de Takımların listelenmesi yapılacaktır. RecyclerView ile gösterim yapılacaktır. Eğer ki kullanıcı herhangi bir
    takımın fikstürü görüntülenmek istenirse, takımın üstüne tıklaması yeterlidir. Daha sonra da takım fikstürü görüntüleme için,
    takımın ismi ve ID'si TeamFixture Activity'e gönderilecektir.
 */
/*
    Teams will be listed in this activity. It will be displayed with RecyclerView. If the user has any
    If you want to view the fixture of the team, simply click on the team. For later tool fixture display,
    The team's name and ID will be sent to TeamFixture Activity.
 */

public class TeamsList extends AppCompatActivity {
    private static final String TAG = "Team List";

    private SharedPref sharedPref;
    private TeamViewModel teamViewModel;
    TeamAdapter adapter;
    private LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_list);
            initialUI();

    }

    private void initialUI() {
        animationView = findViewById(R.id.animationView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        sharedPref = SharedPref.getInstance(this);
        adapter = new TeamAdapter();
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        teamViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable final List<Team> teams) {
                adapter.submitList(teams);
                initRecycler();
            }
        });


    }
    private void showAnimation() {
            animationView.setAnimation(R.raw.fan);
            animationView.setVisibility(View.VISIBLE);
    }

    private void hideAnimation() {
        animationView.setVisibility(View.GONE);
    }
    private void initRecycler(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Team team) {
                int teamID = team.getId();
                showAnimation();
                Intent intent = new Intent(TeamsList.this,TeamFixture.class);
                intent.putExtra("id",String.valueOf(teamID));
                intent.putExtra("name",team.getTitle());
                goActivity(intent);
            }
        });
    }
    private void goActivity(Intent intent) {
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

}


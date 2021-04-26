package example.com.newapp.Views.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.newapp.Adapters.FixtureAdapter;
import example.com.newapp.DataBase.Fixture.Fixture;
import example.com.newapp.DataBase.Team.Team;
import example.com.newapp.R;
import example.com.newapp.Utils.SharedPref;
import example.com.newapp.ViewModels.FixtureViewModel;
import example.com.newapp.ViewModels.TeamViewModel;

/*
    Bu aktiviti'de artık takımların Fikstürleri görüntülenecektir. Algoritma; öncelikle takımları çektikten sonra,
    Takımlar match(teams) fonksiyonuna gönderilecektir. Burada takımların sayısı tek ise her hafta 1 takım BYE
    geçecektir. Eğer ki takımların sayısı çift ise BYE geçmeyecektir.
    Matematik işlemleri:
        Takım sayısı Tek ise:
            Toplam hafta sayısı : Takım sayısı*2
            Haftalık yapılacak maç sayısı: Takım sayısı/2 ~ Buradan çıkan sonuç üste yuvarlanır.
        Takım sayısı tek ise:
            Toplam hafta sayısı: (Takım sayısı-1) * 2
            Haftalık yapılacak maç sayısı: Takım sayısı/2
     Bu işlemler yapıldıktan sonra takımlar recyclerVeiew'de yayınlanacaktır.
 */
/*
    Fixtures of the teams will now be displayed in this activity. Algorithm; firstly after pulling the teams,
    The teams will be sent to the match (teams) function. Here, if the number of teams is odd, 1 team BYE every week
    will pass. If the number of teams is double, the BYE will not be exceeded.
    Math operations:
        If the number of teams is Odd:
            Total weeks: Number of teams * 2
            Number of matches per week: Number of teams / 2 ~ The result here will be rounded to the top.
        If the number of teams is odd:
            Total number of weeks: (Number of teams-1) * 2
            Number of matches per week: Number of teams / 2
     After these operations are done, the teams will be published in recyclerVeiew.
 */


public class FixtureViewPager extends Fragment {
    private static final String TAG = "Fixture Fragment";
    private TeamViewModel teamViewModel;
    View view;
    private SharedPref sharedPref;
    private int weekNo;
    private TextView topTw;
    private Context c;
    int id = 1;
    private FixtureViewModel fixtureViewModel;


    public FixtureViewPager(Context c, int weekNo) {
        this.weekNo = weekNo;
        this.c = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fixture_view_pager, container, false);
        topTw = view.findViewById(R.id.weekText);
        RecyclerView recyclerView = view.findViewById(R.id.fixtureRecyclerView);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        final FixtureAdapter adapter = new FixtureAdapter();
        recyclerView.setAdapter(adapter);
        sharedPref = SharedPref.getInstance(getContext());
        createFixture();
        fixtureViewModel = ViewModelProviders.of(this).get(FixtureViewModel.class);
        fixtureViewModel.getSelectedWeek(String.valueOf(weekNo+1)).observe(this, new Observer<List<Fixture>>() {
            @Override
            public void onChanged(@Nullable final List<Fixture> weeks) {

                adapter.submitList(weeks);

            }
        });
        return view;

    }

    private void createFixture() {
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        teamViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {

            @Override
            public void onChanged(@Nullable List<Team> teams) {
                match(teams);

                sharedPref.setTeamsSize(String.valueOf(teams.size()));
            }
        });
    }

    private void match(List<Team> teamList) {

        int totalWeek = 2 * (teamList.size() - 1);
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < teamList.size(); i++) {
            map.put(i, teamList.get(i).getTitle());
        }
        int teams = teamList.size();
        boolean ghost = false;
        if (teams % 2 == 1) {
            teams++;
            ghost = true;
        }
        int totalRounds = teams - 1;
        int matchesPerRound = teams / 2;
        String[][] rounds = new String[totalRounds][matchesPerRound];
        String[][] rounds2 = new String[totalRounds][matchesPerRound];
        for (int round = 0; round < totalRounds; round++) {
            for (int match = 0; match < matchesPerRound; match++) {
                int home = (round + match) % (teams - 1);
                int away = (teams - 1 - match + round) % (teams - 1);
                if (match == 0) {
                    away = teams - 1;
                }

                if(map.get(home) == null){
                    rounds[round][match] = " BYE " + " vs " + map.get(away);
                    rounds2[round][match] = map.get(away) + " vs " +" BYE ";
                }
                else if (map.get(away) == null){
                    rounds[round][match] = map.get(home) + " vs " + " BYE ";
                    rounds2[round][match] = " BYE " + " vs " + map.get(home);
                }
                else{
                    rounds[round][match] = map.get(home) + " vs " + map.get(away);
                    rounds2[round][match] = map.get(away) + " vs " + map.get(home);
                }

            }
        }
        String[][] interleaved = new String[totalRounds][matchesPerRound];

        if(teamList.size() %2 == 0){
            ArrayList<String > array = new ArrayList<>();
            String[][] copyLists= new String[totalRounds*2][matchesPerRound];
            for(int i =0;i<rounds.length;i++){
                for(int j=0;j<matchesPerRound;j++){
                    copyLists[i][j] =  rounds[i][j];
                    array.add(rounds[i][j]);

                }
            }
            for(int i =0; i< rounds2.length; i++){
                for(int j =0; j<matchesPerRound;j++){
                    copyLists[i+teamList.size()-1][j] = rounds2[i][j];
                    array.add(rounds2[i][j]);
                }
            }
            for (int t = 0; t < copyLists.length; t++) {
                for (int q = 0; q < matchesPerRound; q++) {
                    if (Integer.parseInt(sharedPref.getID()) <= totalWeek * matchesPerRound) {
                        id = Integer.parseInt(sharedPref.getID());
                        Fixture fixtureInit = new Fixture(id, copyLists[t][q], String.valueOf(t+1));
                        fixtureViewModel.insert(fixtureInit);
                        sharedPref.setId(String.valueOf(Integer.parseInt((sharedPref.getID())) + 1));

                    }

                }
            }

        }

        else if(teamList.size() %2 == 1 ){
            String[][] copylist2 = new String[teamList.size()*2][matchesPerRound];
            ArrayList<String > array = new ArrayList<>();
            for(int i =0 ; i<rounds.length;i++){
                for(int j  = 0 ; j <matchesPerRound;j++){
                    array.add(rounds[i][j]);
                }
            }
            for(int i =0 ;i< rounds2.length;i++){
                for (int j =0; j<matchesPerRound;j++){
                    array.add(rounds2[i][j]);
                }
            }
             displayFixtureOdd(array,matchesPerRound,teamList.size()*2);
        }


    }

    private void displayFixtureOdd(ArrayList<String > fixtureList,int matchesPerRound,int totalWeek) {
        int a = 0;
        for (int t = 0; t < fixtureList.size()/matchesPerRound; t++) {
            for (int q = 0; q < matchesPerRound; q++) {
                if (Integer.parseInt(sharedPref.getID()) <= totalWeek * matchesPerRound && a < totalWeek * matchesPerRound) {
                    id = Integer.parseInt(sharedPref.getID());
                    Fixture fixtureInit = new Fixture(id, fixtureList.get(a), String.valueOf(t + 1));
                    fixtureViewModel.insert(fixtureInit);
                    sharedPref.setId(String.valueOf(Integer.parseInt((sharedPref.getID())) + 1));
                    a = a + 1;

                }

            }
        }
    }


}
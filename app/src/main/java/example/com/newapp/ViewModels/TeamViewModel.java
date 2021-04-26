package example.com.newapp.ViewModels;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import example.com.newapp.DataBase.Team.Team;
import example.com.newapp.DataBase.Team.TeamRepository;

public class TeamViewModel extends AndroidViewModel {
    private static final String TAG = "Team ViewModel";

    private TeamRepository mRepository;
    private LiveData<List<Team>> mAllTeams;

    public TeamViewModel(Application application) {
        super(application);
        mRepository = new TeamRepository(application);
        mAllTeams = mRepository.getAllTeams();
    }

    public LiveData<List<Team>> getAllTeams() {
        return mAllTeams;
    }

    public void insert(Team word) {
        mRepository.insert(word);
    }

}
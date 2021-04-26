package example.com.newapp.DataBase.Team;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TeamRepository {
    private TeamDao teamDao;
    private LiveData<List<Team>> allTeams;

    public TeamRepository(Application application) {
        TeamDatabase db = TeamDatabase.getDatabase(application);
        teamDao = db.teamDao();
        allTeams = teamDao.getAllTeams();
    }

    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }

    public void insert(Team word) {
        new insertAsyncTask(teamDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Team, Void, Void> {

        private TeamDao teamDao;

        insertAsyncTask(TeamDao dao) {
            teamDao = dao;
        }

        @Override
        protected Void doInBackground(final Team... teams) {
            teamDao.insert(teams[0]);
            return null;
        }
    }

}
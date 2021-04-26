package example.com.newapp.DataBase.Fixture;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


public class FixtureRepository {
    private FixtureDao fixtureDao;
    private LiveData<List<Fixture>> allWeeks;

    public FixtureRepository(Application application) {
        FixtureDatabase db = FixtureDatabase.getDatabase(application);
        fixtureDao = db.fixtureDao();
        allWeeks = fixtureDao.getAllWeeks();
    }

    public Integer getCount() {
        return fixtureDao.getCount();
    }

    public LiveData<List<Fixture>> selectedWeek(String week) {
        return fixtureDao.selectedWeek(week);
    }
    public LiveData<List<Fixture>> getAllTeamsWeeks(String teamName){
        return fixtureDao.selectedTeam(teamName);
    }
    public LiveData<List<Fixture>> getAllWeeks() {
        return allWeeks;
    }

    public void insert(Fixture word) {
        new FixtureRepository.insertAsyncTask(fixtureDao).execute(word);
    }
    private static class insertAsyncTask extends AsyncTask<Fixture, Void, Void> {

        private FixtureDao fixtureDao;

        insertAsyncTask(FixtureDao dao) {
            fixtureDao = dao;
        }

        @Override
        protected Void doInBackground(final Fixture... fixtures) {
            fixtureDao.insert(fixtures[0]);
            return null;
        }
    }
}

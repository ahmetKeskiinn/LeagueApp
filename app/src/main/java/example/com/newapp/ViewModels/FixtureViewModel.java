package example.com.newapp.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import example.com.newapp.DataBase.Fixture.Fixture;
import example.com.newapp.DataBase.Fixture.FixtureRepository;

public class FixtureViewModel extends AndroidViewModel {
    private static final String TAG = "Fixture ViewModel";

    private FixtureRepository mRepository;
    private LiveData<List<Fixture>> mAllWeeks;

    public FixtureViewModel(Application application) {
        super(application);
        mRepository = new FixtureRepository(application);
        mAllWeeks = mRepository.getAllWeeks();
    }

    public LiveData<List<Fixture>> getAllWeeks() {
        return mAllWeeks;
    }

    public Integer getCount() {
        return mRepository.getCount();
    }

    public LiveData<List<Fixture>> getAllTeamFixture(String teamName){
        return mRepository.getAllTeamsWeeks(teamName);
    }

    public LiveData<List<Fixture>> getSelectedWeek(String week) {
        return mRepository.selectedWeek(week);
    }

    public void insert(Fixture word) {
        mRepository.insert(word);
    }

}


package example.com.newapp.DataBase.Team;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Team team);

    @Update
    void update(Team team);

    @Query("SELECT * FROM teams_table ORDER BY id ASC")
    LiveData<List<Team>> getAllTeams();
}
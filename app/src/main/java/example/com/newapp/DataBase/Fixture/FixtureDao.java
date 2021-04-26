package example.com.newapp.DataBase.Fixture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
/*
Bu interface'de metodların açıklamaları:
    Insert: Takımları kaydet.(Eğer ki gelen takımların id'ler kontrol edilip işlem yapılmaktadır.)
    getAllWeeks: Tüm haftaları almak için id'ye göre takım tablosundan çekecektir.
    getSelectedWeek: Seçilen haftayı ekrana getirmek için kullanacağız.
    selectedTeam: Sadece seçilen takımın maçlarını göstereceğiz.
 */
/*

Explanations of the methods in this interface:
    Insert: Save the teams. (If the ids of the incoming teams are checked and processed.)
    getAllWeeks: It will pull from team table by id to get all weeks.
    getSelectedWeek: We will use it to display the selected week.
    selectedTeam: We will only show the matches of the selected team.
 */

@Dao
public interface FixtureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Fixture fixture);

    @Update
    void update(Fixture fixture);

    @Query("SELECT * FROM fixture_table ORDER BY id ASC")
    LiveData<List<Fixture>> getAllWeeks();

    @Query("SELECT COUNT(id) FROM fixture_table")
    Integer getCount();

    @Query("SELECT * FROM fixture_table WHERE week=:weekNo")
    LiveData<List<Fixture>> selectedWeek(String weekNo);

    @Query("SELECT * FROM fixture_table WHERE title LIKE '%' || (:teamName) || '%'")
    LiveData<List<Fixture>> selectedTeam(String teamName);
}

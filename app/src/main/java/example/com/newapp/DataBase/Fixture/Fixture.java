package example.com.newapp.DataBase.Fixture;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fixture_table")
public class Fixture {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String week;
    private String title;

    public Fixture(int id, String title, String week) {
        this.title = title;
        this.id = id;
        this.week = week;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

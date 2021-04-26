package example.com.newapp.DataBase.Team;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teams_table")
public class Team {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String title;

    public Team(int id, String title) {
        this.title = title;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}


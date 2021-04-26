package example.com.newapp.DataBase.Team;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Team.class}, version = 1)
public abstract class TeamDatabase extends RoomDatabase {
    public abstract TeamDao teamDao();

    private static TeamDatabase INSTANCE;

    static TeamDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TeamDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TeamDatabase.class, "teams")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

}

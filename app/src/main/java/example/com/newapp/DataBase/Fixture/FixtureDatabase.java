package example.com.newapp.DataBase.Fixture;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Fixture.class}, version = 1)
public abstract class FixtureDatabase extends RoomDatabase {
    public abstract FixtureDao fixtureDao();

    private static FixtureDatabase INSTANCE;

    static FixtureDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FixtureDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FixtureDatabase.class, "weeks")
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

package com.larrex.coinrecon.room;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SearchHistoryEntity.class}, version = 1)
public abstract class SearchHistoryDatabase extends RoomDatabase {

    public abstract SearchHistoryDao dao();

    private static SearchHistoryDatabase newInstance;

    private static String databaseName = "SEARCHED_ITEM_DATABASE";

    public static synchronized SearchHistoryDatabase getInstance(Application application) {

        if (newInstance == null) {
            newInstance = Room.databaseBuilder(application, SearchHistoryDatabase.class, databaseName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return newInstance;
    }

}

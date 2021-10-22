package com.example.tubespbp_mbanking.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseMutasi {
    private Context context;
    private static DatabaseMutasi databaseMutasi;

    private AppDatabase database;

    public DatabaseMutasi(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "mutasi").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseMutasi getInstance(Context context) {
        if(databaseMutasi == null) {
            databaseMutasi = new DatabaseMutasi(context);
        }

        return databaseMutasi;
    }

    public AppDatabase getDatabase() { return database; }
}

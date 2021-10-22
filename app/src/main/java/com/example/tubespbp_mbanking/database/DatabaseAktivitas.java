package com.example.tubespbp_mbanking.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseAktivitas {
    private Context context;
    private static DatabaseAktivitas databaseAktivitas;

    private AppDatabase database;

    public DatabaseAktivitas(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "aktivitas").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseAktivitas getInstance(Context context) {
        if(databaseAktivitas == null) {
            databaseAktivitas = new DatabaseAktivitas(context);
        }

        return databaseAktivitas;
    }

    public AppDatabase getDatabase() { return database; }
}

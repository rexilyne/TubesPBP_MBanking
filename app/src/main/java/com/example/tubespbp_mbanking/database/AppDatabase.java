package com.example.tubespbp_mbanking.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tubespbp_mbanking.dao.MutasiDao;
import com.example.tubespbp_mbanking.dao.UserDao;
import com.example.tubespbp_mbanking.model.Mutasi;
import com.example.tubespbp_mbanking.model.User;

@Database(entities = {User.class, Mutasi.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract MutasiDao mutasiDao();
}

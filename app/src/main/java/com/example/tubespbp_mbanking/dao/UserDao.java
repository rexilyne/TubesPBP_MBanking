package com.example.tubespbp_mbanking.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tubespbp_mbanking.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE email = :search")
    List<User> getUserByEmail(String search);

    @Query("SELECT * FROM user WHERE accountNumber = :search")
    List<User> getUserByAccNumber(String search);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}

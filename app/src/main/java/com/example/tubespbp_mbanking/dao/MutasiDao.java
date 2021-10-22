package com.example.tubespbp_mbanking.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tubespbp_mbanking.model.Mutasi;

import java.util.List;

@Dao
public interface MutasiDao {
    @Query("SELECT * FROM mutasi WHERE accountNumber = :search")
    List<Mutasi> getMutasiByAccNumber(String search);

    @Insert
    void insertMutasi(Mutasi mutasi);

    @Update
    void updateMutasi(Mutasi mutasi);

    @Delete
    void deleteMutasi(Mutasi mutasi);
}

package com.example.tubespbp_mbanking.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tubespbp_mbanking.model.Aktivitas;

import java.util.List;

@Dao
public interface AktivitasDao {
    @Query("SELECT * FROM aktivitas WHERE accountNumberOri = :search")
    List<Aktivitas> getAktivitasByAccNumber(String search);

    @Insert
    void insertAktivitas(Aktivitas aktivitas);

    @Update
    void updateAktivitas(Aktivitas aktivitas);

    @Delete
    void deleteAktivitas(Aktivitas aktivitas);
}

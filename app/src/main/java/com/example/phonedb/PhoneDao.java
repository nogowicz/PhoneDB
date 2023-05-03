package com.example.phonedb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PhoneDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Phone phone);

    @Query("SELECT * FROM phone")
    LiveData<List<Phone>> getAll();

    @Query("DELETE FROM phone")
    void deleteAll();

    @Update
    void update(Phone phone);

    @Delete
    void delete(Phone phone);
}

package com.gucarsoft.lockpcwithphone.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gucarsoft.lockpcwithphone.model.Profile;

import java.util.List;

@Dao
public interface ProfileDao {
    @Query("SELECT * FROM profile")
    List<Profile> getAll();

    @Query("SELECT * FROM profile WHERE id=:profileID")
    Profile findById(int profileID);

    @Insert
    void insertAll(Profile... profiles);

    @Delete
    void delete(Profile profile);

    @Update
    int update(Profile profile);
}

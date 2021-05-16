package com.gucarsoft.lockpcwithphone.service;

import com.gucarsoft.lockpcwithphone.database.AppDatabase;
import com.gucarsoft.lockpcwithphone.model.Profile;

import java.util.List;

public class ProfileService {

    private  AppDatabase db;

    public ProfileService(AppDatabase appDatabase) {
        this.db = appDatabase;
    }

    public Profile create(Profile data) {
        db.profileDao().insertAll(data);
        return data;
    }

    public  Profile delete(Profile data) {
        db.profileDao().delete(data);
        return data;
    }

    public  List<Profile> getAll() {
        return db.profileDao().getAll();
    }

    public  Profile findById(int id) {
        return db.profileDao().findById(id);
    }


}

package com.example.phonedb;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {
    private PhoneDao mPhoneDao;
    private LiveData<List<Phone>> mAllPhones;

    public PhoneRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPhoneDao = db.phoneDao();
        mAllPhones = mPhoneDao.getAll();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return mAllPhones;
    }

    public void insert(final Phone phone) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDao.insert(phone);
        });
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDao.deleteAll();
        });
    }

    public void updatePhone(Phone phone) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDao.update(phone);
        });
    }

    public void delete(Phone phone) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDao.delete(phone);
        });
    }
}

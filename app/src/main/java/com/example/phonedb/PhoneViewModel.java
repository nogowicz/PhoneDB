package com.example.phonedb;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private final PhoneRepository mRepository;
    private final LiveData<List<Phone>> mAllPhones;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PhoneRepository(application);
        mAllPhones = mRepository.getAllPhones();
    }

    LiveData<List<Phone>> getAllPhones() {
        return mAllPhones;
    }

    public void insert(Phone phone) {
        mRepository.insert(phone);
    }

    public void deleteAll () {
        mRepository.deleteAll();
    }

    public void updatePhone(int id, String producer, String model, String androidVersion, String website) {
        Phone updatedPhone = new Phone(id, producer, model, androidVersion, website);
        mRepository.updatePhone(updatedPhone);
    }

    public void delete(Phone phone) {
        mRepository.delete(phone);
    }

}

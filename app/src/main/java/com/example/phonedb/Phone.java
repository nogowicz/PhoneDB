package com.example.phonedb;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Phone {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String producer;
    public String model;
    public String androidVersion;
    public String website;

    public Phone(String producer, String model, String androidVersion, String website) {
        this.producer = producer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }

    @Ignore
    public Phone(int id, String producer, String model, String androidVersion, String website) {
        this.id = id;
        this.producer = producer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }


    public String getProducer() {
        return producer;
    }

    public String getModel() {
        return model;
    }

    public int getId() {
        return id;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public String getWebsite() {
        return website;
    }
}

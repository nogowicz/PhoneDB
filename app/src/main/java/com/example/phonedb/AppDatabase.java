package com.example.phonedb;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PhoneDao phoneDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "phone_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {


        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                PhoneDao dao = INSTANCE.phoneDao();;
                Phone phone1 = new Phone("Samsung", "Galaxy S21", "Android 11", "www.samsung.com");
                Phone phone2 = new Phone("Apple", "iPhone 12", "iOS 14", "www.apple.com");
                Phone phone3 = new Phone("Google", "Pixel 5", "Android 11", "www.google.com");

                dao.insert(phone2);
                dao.insert(phone1);
                dao.insert(phone3);
            });
        }
    };
}

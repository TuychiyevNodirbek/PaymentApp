package uz.example.paymentapp.di;


import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import uz.example.paymentapp.data.local.AppDatabase;
import uz.example.paymentapp.data.local.TransactionDao;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    private static final String DATABASE_NAME = "app_database";

    @Provides
    @Singleton
    public AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    public TransactionDao provideTransactionDao(AppDatabase db) {
        return db.transactionDao();
    }
}

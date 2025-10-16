package uz.example.paymentapp.di;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import uz.example.paymentapp.data.repository.TransactionRepositoryImpl;
import uz.example.paymentapp.domain.repository.TransactionRepository;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract TransactionRepository bindTransactionRepository(TransactionRepositoryImpl impl);
}
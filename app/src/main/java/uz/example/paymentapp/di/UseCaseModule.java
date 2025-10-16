package uz.example.paymentapp.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import uz.example.paymentapp.domain.PaymentUseCase;
import uz.example.paymentapp.domain.repository.PaymentUseCaseImpl;

@Module
@InstallIn(SingletonComponent.class)
public abstract class UseCaseModule {

    @Binds
    @Singleton
    public abstract PaymentUseCase bindPaymentUseCase(PaymentUseCaseImpl impl);
}
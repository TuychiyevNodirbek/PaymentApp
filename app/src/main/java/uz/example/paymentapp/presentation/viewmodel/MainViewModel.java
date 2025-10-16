package uz.example.paymentapp.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uz.example.paymentapp.domain.PaymentUseCase;
import uz.example.paymentapp.domain.model.PaymentInfo;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final PaymentUseCase paymentUseCase;
    private final MutableLiveData<PaymentInfo> currentPaymentLive = new MutableLiveData<>();

    @Inject
    public MainViewModel(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    public LiveData<PaymentInfo> getCurrentPayment() {
        return currentPaymentLive;
    }

    public void startPayment(double amount) {
        PaymentInfo payment = paymentUseCase.startPayment(amount);
        currentPaymentLive.setValue(payment);
    }

    public void completePayment(boolean success) {
        PaymentInfo payment = currentPaymentLive.getValue();
        paymentUseCase.completePayment(payment, success);
        currentPaymentLive.setValue(payment);
    }

    public void clearCurrentPayment() {
        currentPaymentLive.setValue(null);
    }
}

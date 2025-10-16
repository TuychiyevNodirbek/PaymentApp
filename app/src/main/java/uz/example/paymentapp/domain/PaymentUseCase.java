package uz.example.paymentapp.domain;

import uz.example.paymentapp.domain.model.PaymentInfo;

public interface PaymentUseCase {

    PaymentInfo startPayment(double amount);

    void completePayment(PaymentInfo payment, boolean success);
}
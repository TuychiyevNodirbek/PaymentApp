package uz.example.paymentapp.domain.repository;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import uz.example.paymentapp.domain.PaymentUseCase;
import uz.example.paymentapp.domain.model.PaymentInfo;
import uz.example.paymentapp.domain.model.Transaction;
@Singleton
public class PaymentUseCaseImpl implements PaymentUseCase {

    private final TransactionRepository repository;

    @Inject
    public PaymentUseCaseImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public PaymentInfo startPayment(double amount) {
        return new PaymentInfo(UUID.randomUUID().toString(), amount);
    }

    @Override
    public void completePayment(PaymentInfo payment, boolean success) {
        if (payment != null) {
            payment.setSuccess(success);
            Transaction transaction = new Transaction(
                    payment.getTransactionId(),
                    payment.getAmount(),
                    payment.isSuccess(),
                    System.currentTimeMillis()
            );
            repository.saveTransaction(transaction);
        }
    }
}
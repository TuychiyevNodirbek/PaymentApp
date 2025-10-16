package uz.example.paymentapp.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import uz.example.paymentapp.domain.model.Transaction;

public interface TransactionRepository {
    void saveTransaction(Transaction transaction);

    LiveData<List<Transaction>> getAllTransactions();

    Transaction getTransactionById(String id);
}

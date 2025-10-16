package uz.example.paymentapp.data.datasource;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import uz.example.paymentapp.data.local.TransactionDao;
import uz.example.paymentapp.data.local.TransactionEntity;

public class TransactionLocalDataSource {

    private final TransactionDao dao;

    @Inject
    public TransactionLocalDataSource(TransactionDao dao) {
        this.dao = dao;
    }

    public void insert(TransactionEntity transaction) {
        new Thread(() -> dao.insert(transaction)).start();
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return dao.getAllTransactions();
    }

    public TransactionEntity getTransactionById(String id) {
        return dao.getTransactionById(id);
    }
}

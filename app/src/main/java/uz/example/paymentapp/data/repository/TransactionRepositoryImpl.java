package uz.example.paymentapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import uz.example.paymentapp.data.datasource.TransactionLocalDataSource;
import uz.example.paymentapp.data.local.TransactionEntity;
import uz.example.paymentapp.domain.model.Transaction;
import uz.example.paymentapp.domain.repository.TransactionRepository;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionLocalDataSource localDataSource;

    @Inject
    public TransactionRepositoryImpl(TransactionLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.id = transaction.getId();
        entity.amount = transaction.getAmount();
        entity.status = transaction.getStatus();
        entity.timestamp = transaction.getTimestamp();
        localDataSource.insert(entity);
    }

    @Override
    public LiveData<List<Transaction>> getAllTransactions() {
        return Transformations.map(localDataSource.getAllTransactions(), entities -> {
            List<Transaction> list = new ArrayList<>();
            for (TransactionEntity e : entities) {
                list.add(new Transaction(e.id, e.amount, e.status, e.timestamp));
            }
            return list;
        });
    }

    @Override
    public Transaction getTransactionById(String id) {
        TransactionEntity e = localDataSource.getTransactionById(id);
        if (e == null) return null;
        return new Transaction(e.id, e.amount, e.status, e.timestamp);
    }
}

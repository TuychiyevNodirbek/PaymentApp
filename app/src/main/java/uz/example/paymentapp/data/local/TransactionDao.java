package uz.example.paymentapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TransactionEntity transaction);

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    LiveData<List<TransactionEntity>> getAllTransactions();

    @Query("SELECT * FROM transactions WHERE status = :status ORDER BY timestamp DESC")
    LiveData<List<TransactionEntity>> getTransactionsByStatus(String status);

    @Query("SELECT * FROM transactions WHERE id = :transactionId LIMIT 1")
    TransactionEntity getTransactionById(String transactionId);
}

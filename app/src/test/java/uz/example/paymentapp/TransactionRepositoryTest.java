package uz.example.paymentapp;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uz.example.paymentapp.domain.model.Transaction;
import uz.example.paymentapp.domain.repository.TransactionRepository;

public class TransactionRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private TransactionRepository repository;

    @Before
    public void setup() {
        repository = new InMemoryTransactionRepository();
    }

    @Test
    public void testSaveTransaction_addsTransaction() {
        Transaction t1 = new Transaction("id1", 1000, true, 1000);
        repository.saveTransaction(t1);

        List<Transaction> list = repository.getAllTransactions().getValue();
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(t1, list.get(0));
    }

    @Test
    public void testGetAllTransactions_returnsList() {
        Transaction t1 = new Transaction("id1", 1000, true, 1000);
        Transaction t2 = new Transaction("id2", 500, false, 2000);

        repository.saveTransaction(t1);
        repository.saveTransaction(t2);

        List<Transaction> list = repository.getAllTransactions().getValue();
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(t1, list.get(0));
        assertEquals(t2, list.get(1));
    }

    @Test
    public void testGetAllTransactions_emptyRepository() {
        List<Transaction> list = repository.getAllTransactions().getValue();
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetTransactionById_exists() {
        Transaction t1 = new Transaction("id1", 1000, true, 1000);
        repository.saveTransaction(t1);

        Transaction found = repository.getTransactionById("id1");
        assertNotNull(found);
        assertEquals(t1, found);
    }

    @Test
    public void testGetTransactionById_notExists() {
        Transaction found = repository.getTransactionById("nonexistent");
        assertNull(found);
    }

    private static class InMemoryTransactionRepository implements TransactionRepository {

        private final MutableLiveData<List<Transaction>> transactionsLive = new MutableLiveData<>(new ArrayList<>());
        private final List<Transaction> transactions = new ArrayList<>();

        @Override
        public void saveTransaction(Transaction transaction) {
            transactions.add(transaction);
            transactionsLive.setValue(new ArrayList<>(transactions));
        }

        @Override
        public LiveData<List<Transaction>> getAllTransactions() {
            return transactionsLive;
        }

        @Override
        public Transaction getTransactionById(String id) {
            for (Transaction t : transactions) {if (t.getId().equals(id)) return t;}
            return null;
        }
    }
}

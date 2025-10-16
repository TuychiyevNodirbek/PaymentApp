package uz.example.paymentapp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import uz.example.paymentapp.domain.model.Transaction;
import uz.example.paymentapp.domain.repository.TransactionRepository;
import uz.example.paymentapp.presentation.viewmodel.HistoryViewModel;

public class HistoryViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TransactionRepository repository;

    private HistoryViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        viewModel = new HistoryViewModel(repository);
    }

    @Test
    public void testGetAllTransactions_returnsList() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("id1", 1000, true, 12345),
                new Transaction("id2", 500, false, 12346)
        );
        MutableLiveData<List<Transaction>> liveData = new MutableLiveData<>();
        liveData.setValue(transactions);
        when(repository.getAllTransactions()).thenReturn(liveData);

        LiveData<List<Transaction>> resultLive = viewModel.getAllTransactions();
        assertNotNull(resultLive);
        assertEquals(2, resultLive.getValue().size());
        assertEquals("id1", resultLive.getValue().get(0).getId());
        assertEquals("id2", resultLive.getValue().get(1).getId());
    }

    @Test
    public void testGetAllTransactions_emptyList() {
        MutableLiveData<List<Transaction>> liveData = new MutableLiveData<>();
        liveData.setValue(Arrays.asList());
        when(repository.getAllTransactions()).thenReturn(liveData);

        LiveData<List<Transaction>> resultLive = viewModel.getAllTransactions();
        assertNotNull(resultLive);
        assertTrue(resultLive.getValue().isEmpty());
    }

    @Test
    public void testGetTransactionById_found() {
        Transaction transaction = new Transaction("id123", 1500, true, 12345);
        when(repository.getTransactionById("id123")).thenReturn(transaction);

        Transaction result = viewModel.getTransactionById("id123");
        assertNotNull(result);
        assertEquals("id123", result.getId());
        assertEquals(1500, result.getAmount(), 0.001);
        assertTrue(result.getStatus());
    }

    @Test
    public void testGetTransactionById_notFound() {
        when(repository.getTransactionById("unknown")).thenReturn(null);

        Transaction result = viewModel.getTransactionById("unknown");
        assertNull(result);
    }

    @Test
    public void testRepositoryCalls() {
        viewModel.getTransactionById("id123");
        viewModel.getAllTransactions();

        verify(repository, times(1)).getTransactionById("id123");
        verify(repository, times(1)).getAllTransactions();
    }
}

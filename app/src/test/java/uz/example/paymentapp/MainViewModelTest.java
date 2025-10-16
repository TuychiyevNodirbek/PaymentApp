package uz.example.paymentapp;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import uz.example.paymentapp.domain.PaymentUseCase;
import uz.example.paymentapp.domain.model.PaymentInfo;
import uz.example.paymentapp.presentation.viewmodel.MainViewModel;

public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private PaymentUseCase useCaseMock;
    private MainViewModel viewModel;

    @Before
    public void setup() {
        useCaseMock = mock(PaymentUseCase.class);
        viewModel = new MainViewModel(useCaseMock);
    }


    @Test
    public void testStartPayment_updatesLiveData() {
        double amount = 2000.0;
        PaymentInfo mockPayment = new PaymentInfo(UUID.randomUUID().toString(), amount);

        when(useCaseMock.startPayment(amount)).thenReturn(mockPayment);

        viewModel.startPayment(amount);

        PaymentInfo current = viewModel.getCurrentPayment().getValue();
        assertNotNull(current);
        assertEquals(amount, current.getAmount(), 0.001);
        assertEquals(mockPayment.getTransactionId(), current.getTransactionId());
        assertFalse(current.isSuccess());
    }

    @Test
    public void testCompletePayment_success() {
        double amount = 2000.0;
        PaymentInfo mockPayment = new PaymentInfo(UUID.randomUUID().toString(), amount);
        when(useCaseMock.startPayment(amount)).thenReturn(mockPayment);
        viewModel.startPayment(amount);
        viewModel.completePayment(true);
        verify(useCaseMock).completePayment(mockPayment, true);
    }

    @Test
    public void testClearCurrentPayment_clearsLiveData() {
        double amount = 2000.0;
        PaymentInfo mockPayment = new PaymentInfo(UUID.randomUUID().toString(), amount);
        when(useCaseMock.startPayment(amount)).thenReturn(mockPayment);
        viewModel.startPayment(amount);
        assertNotNull(viewModel.getCurrentPayment().getValue());
        viewModel.clearCurrentPayment();
        assertNull(viewModel.getCurrentPayment().getValue());
    }

}
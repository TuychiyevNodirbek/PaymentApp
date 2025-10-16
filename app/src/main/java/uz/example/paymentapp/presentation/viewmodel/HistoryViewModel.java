package uz.example.paymentapp.presentation.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import uz.example.paymentapp.domain.model.Transaction;
import uz.example.paymentapp.domain.repository.TransactionRepository;
import uz.example.paymentapp.utils.PdfExportUtils;

@HiltViewModel
public class HistoryViewModel extends ViewModel {

    private final TransactionRepository repository;
    private final MutableLiveData<File> pdfFileLive = new MutableLiveData<>();


    @Inject
    public HistoryViewModel(TransactionRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return repository.getAllTransactions();
    }

    public Transaction getTransactionById(String id) {
        return repository.getTransactionById(id);
    }


    public LiveData<File> getPdfFileLive() {
        return pdfFileLive;
    }

    public void exportTransactionsToPdf(Context context) {

        repository.getAllTransactions().observeForever(transactions -> {
            if (transactions == null || transactions.isEmpty()) return;

            new Thread(() -> {
                File pdfFile = PdfExportUtils.exportTransactions(context.getApplicationContext(), transactions);
                pdfFileLive.postValue(pdfFile);
            }).start();
        });
    }
}

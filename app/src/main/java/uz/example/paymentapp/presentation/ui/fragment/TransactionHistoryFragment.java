package uz.example.paymentapp.presentation.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Collections;

import dagger.hilt.android.AndroidEntryPoint;
import uz.example.paymentapp.R;
import uz.example.paymentapp.databinding.FragmentTransactionHistoryBinding;
import uz.example.paymentapp.presentation.ui.adapter.TransactionAdapter;
import uz.example.paymentapp.presentation.viewmodel.HistoryViewModel;


@AndroidEntryPoint
public class TransactionHistoryFragment extends Fragment {

    private FragmentTransactionHistoryBinding binding;
    private HistoryViewModel viewModel;
    private TransactionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);

        adapter = new TransactionAdapter();
        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvTransactions.setAdapter(adapter);

        viewModel.getAllTransactions().observe(getViewLifecycleOwner(), transactions -> {
            boolean hasData = transactions != null && !transactions.isEmpty();
            binding.rvTransactions.setVisibility(hasData ? View.VISIBLE : View.GONE);
            binding.tvEmpty.setVisibility(hasData ? View.GONE : View.VISIBLE);
            binding.fabExportPdf.setVisibility(hasData ? View.VISIBLE : View.GONE);

            adapter.submitList(transactions != null ? transactions : Collections.emptyList());
        });
        initPdfExport();

        binding.fabExportPdf.setOnClickListener(v -> {
            viewModel.exportTransactionsToPdf(requireContext());
        });
    }

    void initPdfExport() {
        viewModel.getPdfFileLive().observe(getViewLifecycleOwner(), file -> {
            if (file != null) {
                Toast.makeText(requireContext(), getString(R.string.toast_pdf_success) + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(requireContext(), R.string.toast_pdf_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
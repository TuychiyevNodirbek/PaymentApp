package uz.example.paymentapp.presentation.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.example.paymentapp.R;
import uz.example.paymentapp.databinding.FragmentResultBinding;
import uz.example.paymentapp.domain.model.PaymentInfo;
import uz.example.paymentapp.presentation.viewmodel.MainViewModel;
import uz.example.paymentapp.utils.FormatUtils;

public class ResultFragment extends Fragment {

    private FragmentResultBinding binding;
    private MainViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        initView();
    }

    private void initView() {
        viewModel.getCurrentPayment().observe(getViewLifecycleOwner(), payment -> {
            if (payment != null) {
                binding.tvAmount.setText(String.format("%s UZS", FormatUtils.formatAmount(payment.getAmount())));
                boolean success = payment.isSuccess();
                binding.tvStatus.setText(success ? getString(R.string.common_success) : getString(R.string.common_error));
                binding.ivStatusIcon.setImageResource(success ? R.drawable.ic_checkcircle : R.drawable.ic_warningcircle);
            }
        });

        binding.btnBackToMenu.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_resultFragment_to_homeFragment);
            viewModel.clearCurrentPayment();
        });
    }
}
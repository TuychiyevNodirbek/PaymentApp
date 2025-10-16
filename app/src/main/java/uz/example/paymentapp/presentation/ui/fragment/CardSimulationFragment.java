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

import dagger.hilt.android.AndroidEntryPoint;
import uz.example.paymentapp.R;
import uz.example.paymentapp.databinding.FragmentCardSimulationBinding;
import uz.example.paymentapp.presentation.viewmodel.MainViewModel;

@AndroidEntryPoint
public class CardSimulationFragment extends Fragment {

    private FragmentCardSimulationBinding binding;
    private MainViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardSimulationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.actionSuccess.setOnClickListener(v -> {
            viewModel.completePayment(true);
            NavHostFragment.findNavController(this).navigate(R.id.action_cardSimulation_to_result);
        });

        binding.actionError.setOnClickListener(v -> {
            viewModel.completePayment(false);
            NavHostFragment.findNavController(this).navigate(R.id.action_cardSimulation_to_result);
        });
    }
}
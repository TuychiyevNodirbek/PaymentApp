package uz.example.paymentapp.presentation.ui.fragment;

import static uz.example.paymentapp.utils.FormatUtils.MAX_LENGTH;
import static uz.example.paymentapp.utils.FormatUtils.formatAmount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import uz.example.paymentapp.R;
import uz.example.paymentapp.databinding.FragmentAmountInputBinding;
import uz.example.paymentapp.presentation.ui.bottomSheet.CalculatorBottomSheetDialog;
import uz.example.paymentapp.presentation.viewmodel.MainViewModel;
import uz.example.paymentapp.utils.FormatUtils;

@AndroidEntryPoint
public class AmountInputFragment extends Fragment {

    private FragmentAmountInputBinding binding;
    private MainViewModel viewModel;
    private StringBuilder input = new StringBuilder();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAmountInputBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        initKeyboard();
        initBackspaceAction();
        initCalculatorAction();
        initPayButton();

    }

    private void initCalculatorAction() {
        binding.btnCalculator.setOnClickListener(v -> {
            CalculatorBottomSheetDialog dialog = new CalculatorBottomSheetDialog(0.0, 10000000000.0, result -> {
                binding.tvAmount.setText(FormatUtils.formatAmount(result));
                input.setLength(0);
                input.append(result);
            });
            dialog.show(getParentFragmentManager(), "CalculatorBottomSheet");
        });
    }

    private void initPayButton() {
        binding.payButton.setOnClickListener(v -> {
            if (input.length() == 0) {
                Toast.makeText(getContext(), R.string.common_field_amount, Toast.LENGTH_SHORT).show();
                return;
            }
            double amount = Double.parseDouble(input.toString());
            viewModel.startPayment(amount);
            NavHostFragment.findNavController(this).navigate(R.id.action_amountInput_to_cardSimulation);
        });
    }

    private void initKeyboard() {
        View.OnClickListener keyClick = v -> {
            TextView key = (TextView) v;
            String text = key.getText().toString();

            if (text.equals("C")) {
                input.setLength(0);
            } else {
                if (text.equals(".")) {
                    if (input.toString().contains(".")) return;
                } else {
                    int dotIndex = input.toString().indexOf('.');
                    if (dotIndex >= 0 && input.toString().length() - dotIndex > 2) return;
                }

                if (input.length() < MAX_LENGTH) input.append(text);
            }

            updateField();
        };

        for (int i = 0; i < binding.keyboard.gridKeyboard.getChildCount(); i++) {
            View child = binding.keyboard.gridKeyboard.getChildAt(i);
            child.setOnClickListener(keyClick);
        }
    }

    private void updateField() {
        String current = input.toString();
        if (current.isEmpty()) {
            binding.tvAmount.setText("0");
            return;
        }

        int dotIndex = current.indexOf('.');
        if (dotIndex >= 0) {
            String integerPart = current.substring(0, dotIndex);
            String decimalPart = current.substring(dotIndex);

            if (!integerPart.isEmpty()) {
                integerPart = FormatUtils.formatAmount(integerPart);
            } else {
                integerPart = "0";
            }

            binding.tvAmount.setText(integerPart + decimalPart);
        } else binding.tvAmount.setText(FormatUtils.formatAmount(current));

    }

    private void initBackspaceAction() {
        binding.btnDeleteChar.setOnClickListener(v -> {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
            }
            String display = input.length() > 0 ? FormatUtils.formatAmount(input.toString()) : "0";
            binding.tvAmount.setText(display);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
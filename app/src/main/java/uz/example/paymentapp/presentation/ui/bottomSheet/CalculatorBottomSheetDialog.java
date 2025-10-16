package uz.example.paymentapp.presentation.ui.bottomSheet;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import uz.example.paymentapp.databinding.BottomSheetCalculatorBinding;
import uz.example.paymentapp.domain.logic.CalculatorEngine;

public class CalculatorBottomSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetCalculatorBinding binding;
    private Vibrator vibrator;
    private CalculatorEngine engine;
    private final OnConfirmListener listener;

    public interface OnConfirmListener {
        void onConfirm(double result);
    }

    public CalculatorBottomSheetDialog(double min, double max, OnConfirmListener listener) {
        this.engine = new CalculatorEngine(min, max);
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = BottomSheetCalculatorBinding.inflate(inflater, container, false);
        vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.tvAmount.setText(engine.getDisplayValue());

        binding.btnBackspace.setOnClickListener(v -> {
            engine.backspace();
            updateDisplay();
            vibrate(50);
        });

        for (int i = 0; i < binding.calculatorGridKeyboard.getChildCount(); i++) {
            View child = binding.calculatorGridKeyboard.getChildAt(i);
            if (child instanceof TextView) {
                child.setOnClickListener(v -> {
                    String input = ((TextView) child).getText().toString();
                    engine.handleInput(input);
                    updateDisplay();
                    vibrate(50);
                });
            }
        }

        binding.btnConfirm.setOnClickListener(v -> {
            engine.calculateResult();
            updateDisplay();
            if (listener != null) listener.onConfirm(engine.getResult());
            dismiss();
        });
    }

    private void updateDisplay() {
        binding.tvAmount.setText(engine.getDisplayValue());
    }

    private void vibrate(int ms) {
        if (vibrator != null) vibrator.vibrate(ms);
    }
}
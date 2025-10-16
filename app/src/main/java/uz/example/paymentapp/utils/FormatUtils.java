package uz.example.paymentapp.utils;

import android.annotation.SuppressLint;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {

    private static final NumberFormat formatter = NumberFormat.getInstance(Locale.US);

    public static final int MAX_LENGTH = 12;
    public static String formatAmount(String amount) {
        if (amount == null || amount.isEmpty()) return "0";

        amount = amount.replaceAll("[^\\d.]", "");

        if (amount.length() > MAX_LENGTH) amount = amount.substring(0, MAX_LENGTH);
        int dotIndex = amount.indexOf('.');
        if (dotIndex >= 0) {

            String integerPart = amount.substring(0, dotIndex);
            String decimalPart = amount.substring(dotIndex + 1);

            integerPart = integerPart.isEmpty() ? "0" : String.valueOf(Long.parseLong(integerPart));

            if (decimalPart.length() > 2) decimalPart = decimalPart.substring(0, 2);

            return formatter.format(Long.parseLong(integerPart)) + "." + decimalPart;
        } else {
            if (amount.isEmpty()) return "0";
            return formatter.format(Long.parseLong(amount));
        }
    }

    public static String formatAmount(double amountDouble) {
        return formatter.format(amountDouble);
    }


    @SuppressLint("DefaultLocale")
    public static String formatNumber(String number) {
        try {
            double val = Double.parseDouble(number);
            boolean isInteger = val == (long) val;

            String formatted;
            if (isInteger) {
                formatted = String.format("%,d", (long) val).replace(',', ' ');
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.DOWN);
                formatted = df.format(val);

                int dotIndex = formatted.indexOf('.');
                if (dotIndex > 0) {
                    String intPart = formatted.substring(0, dotIndex);
                    String decimalPart = formatted.substring(dotIndex);
                    intPart = addSpacesEveryThreeDigits(intPart);
                    formatted = intPart + decimalPart;
                } else {
                    formatted = addSpacesEveryThreeDigits(formatted);
                }
            }

            return formatted;

        } catch (Exception e) {
            return number;
        }
    }

    public static String formatDate(long timestamp) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date(timestamp));
    }

    private static String addSpacesEveryThreeDigits(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        int firstGroupLen = len % 3;
        if (firstGroupLen == 0) firstGroupLen = 3;

        sb.append(s, 0, firstGroupLen);

        for (int i = firstGroupLen; i < len; i += 3) {
            sb.append(' ');
            sb.append(s, i, i + 3);
        }
        return sb.toString();
    }
}

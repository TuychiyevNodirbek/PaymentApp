package uz.example.paymentapp.domain.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculatorEngine {

    private static final int MAX_INPUT_LENGTH = 15;
    public final StringBuilder operationField = new StringBuilder();
    private double result = 0.0;
    private final double minValue;
    private final double maxValue;

    public CalculatorEngine(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void handleInput(String input) {
        if (input == null || input.isEmpty()) return;
        char ch = input.charAt(0);
        String ops = "+-×÷";

        if (ch == '=') {
            calculateResult();
            operationField.setLength(0);
            operationField.append(getResultString());
            return;
        }

        if (operationField.length() >= MAX_INPUT_LENGTH) return;

        if (Character.isDigit(ch)) {
            String num = currentNumber();

            if (num.isEmpty() && ch == '0') {
                operationField.append("0");
                return;
            }
            if (num.equals("0") && ch != '.') operationField.deleteCharAt(operationField.length() - 1);

            int decimals = num.contains(".") ? num.length() - num.indexOf(".") - 1 : 0;
            if (decimals < 2) operationField.append(ch);
        } else if (ops.indexOf(ch) >= 0) {
            if (operationField.length() > 0 && ops.indexOf(lastChar()) < 0)
                operationField.append(ch);
        } else if (ch == '.' && !currentNumber().contains(".")) {
            operationField.append(ch);
        }
    }

    public void backspace() {
        if (operationField.length() > 0) operationField.deleteCharAt(operationField.length() - 1);
    }

    public void clear() {
        operationField.setLength(0);
        result = 0.0;
    }

    public String getDisplayValue() {
        return operationField.length() == 0 ? "0" : operationField.toString();
    }

    public double getResult() {
        return result;
    }

    private String getResultString() {
        BigDecimal bd = BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_UP);
        return bd.stripTrailingZeros().toPlainString();
    }

    public void calculateResult() {
        if (operationField.length() == 0) {
            result = 0.0;
            operationField.setLength(0);
            operationField.append("0");
            return;
        }

        try {
            String expr = operationField.toString().replace("×", "*").replace("÷", "/");
            List<BigDecimal> numbers = new ArrayList<>();
            List<Character> operators = new ArrayList<>();
            parse(expr, numbers, operators);

            BigDecimal total = compute(numbers, operators);

            if (total.compareTo(BigDecimal.valueOf(maxValue)) > 0)
                total = BigDecimal.valueOf(maxValue);
            if (total.compareTo(BigDecimal.valueOf(minValue)) < 0)
                total = BigDecimal.valueOf(minValue);

            total = total.setScale(2, RoundingMode.HALF_UP);
            result = total.doubleValue();

        } catch (Exception e) {
            result = 0.0;
            operationField.setLength(0);
            operationField.append("0");
        }
    }

    private char lastChar() {
        return operationField.charAt(operationField.length() - 1);
    }

    private String currentNumber() {
        String ops = "+-×÷";
        int last = -1;
        for (char op : ops.toCharArray()) {
            int idx = operationField.lastIndexOf(String.valueOf(op));
            if (idx > last) last = idx;
        }
        return operationField.substring(last + 1);
    }

    private void parse(String expr, List<BigDecimal> numbers, List<Character> operators) {
        StringBuilder buf = new StringBuilder();
        for (char ch : expr.toCharArray()) {
            if ("+-*/".indexOf(ch) >= 0) {
                numbers.add(new BigDecimal(buf.toString()));
                operators.add(ch);
                buf.setLength(0);
            } else buf.append(ch);
        }
        if (buf.length() > 0) numbers.add(new BigDecimal(buf.toString()));
    }

    private BigDecimal compute(List<BigDecimal> numbers, List<Character> operators) {
        for (int i = 0; i < operators.size(); ) {
            char op = operators.get(i);
            if (op == '*' || op == '/') {
                BigDecimal a = numbers.get(i);
                BigDecimal b = numbers.get(i + 1);
                BigDecimal res;
                if (op == '*') res = a.multiply(b);
                else
                    res = b.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : a.divide(b, 10, RoundingMode.HALF_UP);
                numbers.set(i, res);
                numbers.remove(i + 1);
                operators.remove(i);
            } else i++;
        }

        BigDecimal total = numbers.get(0);
        for (int i = 0; i < operators.size(); i++) {
            total = operators.get(i) == '+' ? total.add(numbers.get(i + 1)) : total.subtract(numbers.get(i + 1));
        }
        return total;
    }
}
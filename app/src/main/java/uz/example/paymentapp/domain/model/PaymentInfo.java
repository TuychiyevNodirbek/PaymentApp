package uz.example.paymentapp.domain.model;

public class PaymentInfo {
    private final String transactionId;
    private final double amount;
    private boolean success;

    public PaymentInfo(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.success = false;
    }

    public String getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

}
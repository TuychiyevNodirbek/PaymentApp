package uz.example.paymentapp.domain.model;

import java.util.Objects;

public class Transaction {

    private final String id;
    private final double amount;
    private final boolean status;
    private final long timestamp;

    public Transaction(String id, double amount, boolean status, long timestamp) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amount, that.amount) == 0 && timestamp == that.timestamp && Objects.equals(id, that.id) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, status, timestamp);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

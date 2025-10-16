package uz.example.paymentapp.presentation.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import uz.example.paymentapp.R;
import uz.example.paymentapp.databinding.ItemTransactionBinding;
import uz.example.paymentapp.domain.model.Transaction;
import uz.example.paymentapp.utils.FormatUtils;

public class TransactionAdapter extends ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder> {


    public TransactionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Transaction> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Transaction>() {
                @Override
                public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                    return oldItem.getAmount() == newItem.getAmount()
                            && oldItem.getStatus() == newItem.getStatus()
                            && oldItem.getTimestamp() == newItem.getTimestamp();
                }
            };

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTransactionBinding binding = ItemTransactionBinding.inflate(inflater, parent, false);
        return new TransactionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = getItem(position);
        holder.bind(transaction);
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final ItemTransactionBinding binding;
        public TransactionViewHolder(@NonNull ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Transaction transaction) {
            Context context = binding.getRoot().getContext();

            binding.tvTransactionId.setText(context.getString(R.string.history_transaction_id, transaction.getId()));
            binding.tvAmount.setText(String.format("Сумма: %s UZS", FormatUtils.formatAmount(transaction.getAmount())));

            String formattedDate = FormatUtils.formatDate(transaction.getTimestamp());
            binding.tvDate.setText(context.getString(R.string.history_transaction_date, formattedDate));

            if (transaction.getStatus()) {
                binding.tvStatus.setText(context.getString(R.string.history_transaction_status_success));
                binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
            } else {
                binding.tvStatus.setText(context.getString(R.string.history_transaction_status_failed));
                binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
            }
        }
    }
}

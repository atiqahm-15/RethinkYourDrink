package com.example.rethinkyourdrink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BeverageDetailAdapter extends RecyclerView.Adapter<BeverageDetailAdapter.ViewHolder> {
    private List<BeverageRecord> beverageRecords = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView beverageNameText;
        private final TextView categoryText;
        private final TextView amountText;

        public ViewHolder(View view) {
            super(view);
            beverageNameText = view.findViewById(R.id.beverageNameText);
            categoryText = view.findViewById(R.id.categoryText);
            amountText = view.findViewById(R.id.amountText);
        }
    }

    public void setBeverageRecords(List<BeverageRecord> records) {
        this.beverageRecords = new ArrayList<>(records);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beverage_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BeverageRecord record = beverageRecords.get(position);

        String displayName = record.getBeverageName().isEmpty() ?
                record.getCategory() : record.getBeverageName();
        holder.beverageNameText.setText(displayName);
        holder.categoryText.setText(record.getCategory());
        holder.amountText.setText(String.format("%d ml", record.getAmount()));
    }

    @Override
    public int getItemCount() {
        return beverageRecords.size();
    }
}

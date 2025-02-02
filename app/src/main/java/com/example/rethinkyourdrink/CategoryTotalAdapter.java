package com.example.rethinkyourdrink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryTotalAdapter extends RecyclerView.Adapter<CategoryTotalAdapter.ViewHolder>{
    private List<Map.Entry<String, Integer>> categoryTotals = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryText;
        private final TextView amountText;

        public ViewHolder(View view) {
            super(view);
            categoryText = view.findViewById(R.id.categoryText);
            amountText = view.findViewById(R.id.amountText);
        }
    }

    public void setCategoryTotals(Map<String, Integer> totals) {
        categoryTotals = new ArrayList<>(totals.entrySet());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_total, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map.Entry<String, Integer> entry = categoryTotals.get(position);
        holder.categoryText.setText(entry.getKey());
        holder.amountText.setText(String.format("%d ml", entry.getValue()));
    }

    @Override
    public int getItemCount() {
        return categoryTotals.size();
    }
}

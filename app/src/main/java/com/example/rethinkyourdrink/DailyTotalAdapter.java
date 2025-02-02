package com.example.rethinkyourdrink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;
import java.util.TreeMap;

public class DailyTotalAdapter extends RecyclerView.Adapter<DailyTotalAdapter.ViewHolder>{
    private Map<Integer, Integer> dailyTotals = new TreeMap<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dayText;
        private final TextView totalText;

        public ViewHolder(View view) {
            super(view);
            dayText = view.findViewById(R.id.dayText);
            totalText = view.findViewById(R.id.totalText);
        }
    }

    public void setDailyTotals(Map<Integer, Integer> totals) {
        this.dailyTotals = new TreeMap<>(totals);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_total, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int day = (int) dailyTotals.keySet().toArray()[position];
        int total = dailyTotals.get(day);

        holder.dayText.setText(String.format("Day %d", day));
        holder.totalText.setText(String.format("%d ml", total));
    }

    @Override
    public int getItemCount() {
        return dailyTotals.size();
    }
}

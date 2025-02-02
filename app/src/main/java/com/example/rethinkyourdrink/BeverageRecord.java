package com.example.rethinkyourdrink;

public class BeverageRecord {
    private int day;
    private String category;
    private int amount;
    private String beverageName;
    private long timestamp;

    public BeverageRecord(int day, String category, int amount, String beverageName) {
        this.day = day;
        this.category = category;
        this.amount = amount;
        this.beverageName = beverageName;
        this.timestamp = System.currentTimeMillis();
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public int getDay() {
        return day;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

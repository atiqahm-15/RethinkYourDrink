package com.example.rethinkyourdrink;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BeverageDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BEVERAGES = "beverages";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_NAME = "beverage_name";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_BEVERAGES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DAY + " INTEGER,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_AMOUNT + " INTEGER,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_TIMESTAMP + " INTEGER"
                + ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEVERAGES);
        onCreate(db);
    }

    public long addBeverageRecord(BeverageRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, record.getDay());
        values.put(COLUMN_CATEGORY, record.getCategory());
        values.put(COLUMN_AMOUNT, record.getAmount());
        values.put(COLUMN_NAME, record.getBeverageName());
        values.put(COLUMN_TIMESTAMP, record.getTimestamp());
        return db.insert(TABLE_BEVERAGES, null, values);
    }

    public List<BeverageRecord> getRecordsForDay(int day) {
        List<BeverageRecord> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BEVERAGES,
                null,
                COLUMN_DAY + "=?",
                new String[]{String.valueOf(day)},
                null, null, COLUMN_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                BeverageRecord record = new BeverageRecord(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_DAY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                );
                records.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return records;
    }
}

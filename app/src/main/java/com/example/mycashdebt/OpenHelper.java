package com.example.mycashdebt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class OpenHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "Test";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "Money";

    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "OperationName";
    private final static String COLUMN_VALUE = "Value";

    private final static int NUM_COLUMN_ID = 0; //номера столбцов для использования курсора
    private final static int NUM_COLUMN_NAME = 1;
    private final static int NUM_COLUMN_VALUE = 2;

    public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //Создаю единственную таблицу
        String query = "CREATE TABLE " + "debt" + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VALUE + " INTEGER NOT NULL);";
        db.execSQL(query);
        query = "CREATE TABLE " + "prooceds" + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VALUE + " INTEGER NOT NULL);";
        db.execSQL(query);
        query = "CREATE TABLE " + "spending" + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VALUE + " INTEGER NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // Если новая версия, то удаляем старую и создаем новую
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
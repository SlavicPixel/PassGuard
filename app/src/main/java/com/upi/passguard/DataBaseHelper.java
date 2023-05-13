package com.upi.passguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USERS_TABLE = "USERS";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String ID = "ID";

    // factory = null, version = 1
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // called when a database if accessed for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatment = "CREATE TABLE " + USERS_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT)";

        db.execSQL(createTableStatment);
    }

    // called if database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(UserModel userModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, userModel.getUsername());
        cv.put(COLUMN_PASSWORD, userModel.getPassword());

        long insert = db.insert(USERS_TABLE, null, cv);
        if (insert == -1) return false;

        return true;
    }
}

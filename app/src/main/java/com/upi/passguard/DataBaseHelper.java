package com.upi.passguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USERS_TABLE = "USERS";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_URL = "URL";
    public static final String COLUMN_NOTES = "NOTES";
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

    public boolean getUser(String username, String password){
        String queryString = "SELECT * FROM " + USERS_TABLE + " WHERE "
                + COLUMN_USERNAME + " == " + "'" + username + "'" + " AND " + COLUMN_PASSWORD + " == " + "'" + password + "'";

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(queryString, null);
            cursor.moveToFirst();
            String usernameDB = cursor.getString(1);
            String passwordDB = cursor.getString(2);

            cursor.close();
            db.close();

            return usernameDB.equals(username) && passwordDB.equals(password);
        } catch (Exception e) {
            return false;
        }

    }

    public void createVault(String TABLE_NAME){
        SQLiteDatabase db = this.getWritableDatabase();

        String createTableStatment = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_URL + " TEXT, " + COLUMN_NOTES + " TEXT)";

        db.execSQL(createTableStatment);
    }

    public boolean addEntry(VaultModel vaultModel, String TABLE_NAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, vaultModel.getTitle());
        cv.put(COLUMN_USERNAME, vaultModel.getUsername());
        cv.put(COLUMN_PASSWORD, vaultModel.getPassword());
        cv.put(COLUMN_URL, vaultModel.getUrl());
        cv.put(COLUMN_NOTES, vaultModel.getNotes());

        long insert = db.insert(TABLE_NAME, null, cv);
        return insert != -1;
    }



    public List<VaultModel> getEntries(String ENTRY_TABLE){
        List<VaultModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + ENTRY_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursors (result set) and create new entry object. Put them in return list
            do {
                int entryID = cursor.getInt(0);
                String entryTitle = cursor.getString(1);
                String entryUsername = cursor.getString(2);
                String entryPassword = cursor.getString(3);
                String entryUrl = cursor.getString(4);
                String entryNotes = cursor.getString(5);


                VaultModel newEntry  = new VaultModel(entryID, entryTitle, entryUsername, entryPassword, entryUrl, entryNotes);
                returnList.add(newEntry);
            } while (cursor.moveToNext());
        } else {
            // failure, do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }

}

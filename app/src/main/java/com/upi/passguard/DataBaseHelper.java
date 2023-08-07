package com.upi.passguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
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
    public static final String TABLE_NAME = "entries";
    private char[] databasePassword;

    // factory = null, version = 1
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String password) {
        super(context, name, factory, version);
        this.databasePassword = password.toCharArray();
        SQLiteDatabase db = this.getWritableDatabase(databasePassword);
        db.close();
    }


    // called when a database is accessed for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_URL + " TEXT, " + COLUMN_NOTES + " TEXT)";

        db.rawExecSQL(createTableStatement);
    }

    // called if database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<VaultModel> getEntries() {
        List<VaultModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase(databasePassword);

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int entryID = cursor.getInt(0);
                String entryTitle = cursor.getString(1);
                String entryUsername = cursor.getString(2);
                String entryPassword = cursor.getString(3);
                String entryUrl = cursor.getString(4);
                String entryNotes = cursor.getString(5);


                VaultModel newEntry = new VaultModel(entryID, entryTitle, entryUsername, entryPassword, entryUrl, entryNotes);
                returnList.add(newEntry);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return returnList;
    }

    public boolean addEntry(VaultModel vaultModel) {
        SQLiteDatabase db = this.getWritableDatabase(databasePassword);
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, vaultModel.getTitle());
        cv.put(COLUMN_USERNAME, vaultModel.getUsername());
        cv.put(COLUMN_PASSWORD, vaultModel.getPassword());
        cv.put(COLUMN_URL, vaultModel.getUrl());
        cv.put(COLUMN_NOTES, vaultModel.getNotes());

        long insert = db.insert(TABLE_NAME, null, cv);
        db.close();

        return insert != -1;
    }

    public void deleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase(databasePassword);

        String deleteEntryStatement = "DELETE FROM " + TABLE_NAME + " WHERE ID = " + id;

        db.execSQL(deleteEntryStatement);
        db.close();
    }

    // TODO: change arguments to accept a VaultModel
    public void editEntry(VaultModel vaultModel) {
        SQLiteDatabase db = this.getWritableDatabase(databasePassword);

        String editEntryStatement = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_TITLE + " = '" + vaultModel.getTitle() + "', " +
                COLUMN_USERNAME + " = '" + vaultModel.getUsername() + "', " +
                COLUMN_PASSWORD + " = '" + vaultModel.getPassword() + "', " +
                COLUMN_URL + " = '" + vaultModel.getUrl() + "', " +
                COLUMN_NOTES + " = '" + vaultModel.getNotes() + "'" +
                " WHERE " + ID + " = " + vaultModel.getId();

        db.execSQL(editEntryStatement);
        db.close();
    }

}

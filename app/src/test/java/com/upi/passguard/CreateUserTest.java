package com.upi.passguard;

import static com.upi.passguard.DataBaseHelper.COLUMN_PASSWORD;
import static com.upi.passguard.DataBaseHelper.COLUMN_USERNAME;
import static com.upi.passguard.DataBaseHelper.USERS_TABLE;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class CreateUserTest {

    private DataBaseHelper databaseHelper;

    @Before
    public void setup() {
        // Create a new in-memory database before each test
        databaseHelper = new DataBaseHelper(RuntimeEnvironment.application, "passguard.db", null, 1);
    }

    @Test
    public void addUser_insertsUser() {
        UserModel user = new UserModel(-1, "username", "password");

        boolean result = databaseHelper.addUser(user);

        assertTrue(result);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(USERS_TABLE, null, COLUMN_USERNAME + "=?", new String[]{user.getUsername()}, null, null, null);
        assertTrue(cursor.moveToFirst());

        // Verify that the inserted user has the expected values
        assertEquals(user.getUsername(), cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
        assertEquals(user.getPassword(), cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));

        cursor.close();
        db.close();
    }

    @After
    public void tearDown() {
        // Close the database after each test
        databaseHelper.close();
    }
}

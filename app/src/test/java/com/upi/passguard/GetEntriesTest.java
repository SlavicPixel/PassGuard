package com.upi.passguard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import net.sqlcipher.database.SQLiteDatabase;
import static org.junit.Assert.*;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class GetEntriesTest {

    private DataBaseHelper dbHelper;

    @Before
    public void setup() {
        // Initialize the database helper with an in-memory database for testing
        SQLiteDatabase.loadLibs(InstrumentationRegistry.getInstrumentation().getContext());
        dbHelper = new DataBaseHelper(InstrumentationRegistry.getInstrumentation().getContext(), "test_vault.db", null, 1, "test_password");
    }

    @After
    public void tearDown() {
        // Clean up and close the database connection after each test
        dbHelper.close();
    }

    @Test
    public void getEntries_ShouldReturnCorrectListOfEntries() {
        // Create test VaultModel instances
        VaultModel entry1 = new VaultModel(-1, "Entry 1", "user1", "pass1", "www.example.com", "Notes 1");
        VaultModel entry2 = new VaultModel(-1, "Entry 2", "user2", "pass2", "www.example2.com", "Notes 2");

        // Add test entries to the database
        dbHelper.addEntry(entry1);
        dbHelper.addEntry(entry2);

        // Call the getEntries method to retrieve all entries from the database
        List<VaultModel> entries = dbHelper.getEntries();

        // Check that the returned list is not null and contains the added entries
        assertNotNull(entries);
        assertTrue(entries.contains(entry1));
        assertTrue(entries.contains(entry2));
    }

    @Test
    public void getEntries_ShouldReturnEmptyListWhenNoEntriesPresent() {
        // Call the getEntries method when the database is empty
        List<VaultModel> entries = dbHelper.getEntries();

        // Check that the returned list is not null and empty
        assertNotNull(entries);
        assertTrue(entries.isEmpty());
    }
}

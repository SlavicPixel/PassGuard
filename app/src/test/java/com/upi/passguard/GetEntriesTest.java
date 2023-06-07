package com.upi.passguard;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class GetEntriesTest {

    private DataBaseHelper databaseHelper;

    @Before
    public void setup() {
        databaseHelper = new DataBaseHelper(RuntimeEnvironment.application, "passguard.db", null, 1);
    }

    @Test
    public void getEntries_returnsCorrectEntries() {
        // Given three entries added to the database
        VaultModel entry1 = new VaultModel(-1, "title1", "username1", "password1", "url1", "notes1");
        VaultModel entry2 = new VaultModel(-1, "title2", "username2", "password2", "url2", "notes2");
        VaultModel entry3 = new VaultModel(-1, "title3", "username3", "password3", "url3", "notes3");

        // Table is named after user's name
        String ENTRY_TABLE = "entryTableTest";

        // Create a test table
        databaseHelper.createVault(ENTRY_TABLE);

        // Add test values in the test table
        databaseHelper.addEntry(entry1, ENTRY_TABLE);
        databaseHelper.addEntry(entry2, ENTRY_TABLE);
        databaseHelper.addEntry(entry3, ENTRY_TABLE);

        // Retrieve the entries from the database
        List<VaultModel> result = databaseHelper.getEntries(ENTRY_TABLE);

        // Retrieved entries should have the same data
        assertEquals(3, result.size());

        // Check each entry
        assertEquals(entry1.getTitle(), result.get(0).getTitle());
        assertEquals(entry1.getUsername(), result.get(0).getUsername());
        assertEquals(entry1.getPassword(), result.get(0).getPassword());
        assertEquals(entry1.getUrl(), result.get(0).getUrl());
        assertEquals(entry1.getNotes(), result.get(0).getNotes());

        assertEquals(entry2.getTitle(), result.get(1).getTitle());
        assertEquals(entry2.getUsername(), result.get(1).getUsername());
        assertEquals(entry2.getPassword(), result.get(1).getPassword());
        assertEquals(entry2.getUrl(), result.get(1).getUrl());
        assertEquals(entry2.getNotes(), result.get(1).getNotes());

        assertEquals(entry3.getTitle(), result.get(2).getTitle());
        assertEquals(entry3.getUsername(), result.get(2).getUsername());
        assertEquals(entry3.getPassword(), result.get(2).getPassword());
        assertEquals(entry3.getUrl(), result.get(2).getUrl());
        assertEquals(entry3.getNotes(), result.get(2).getNotes());
    }

    @After
    public void tearDown() {
        databaseHelper.close();
    }
}

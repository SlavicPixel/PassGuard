package com.upi.passguard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class GetUserTest {

    private DataBaseHelper databaseHelper;

    @Before
    public void setup() {
        databaseHelper = new DataBaseHelper(RuntimeEnvironment.application, "passguard.db", null, 1);
    }

    @Test
    public void getUser_returnsCorrectUser() {
        // Add a test user to a database
        UserModel expectedUser = new UserModel(-1, "username", "password");
        databaseHelper.addUser(expectedUser);

        // Retrieve the user from the database
        boolean result = databaseHelper.getUser(expectedUser.getUsername(), expectedUser.getPassword());

        // Retrieved user should have the same username and password
        assertTrue(result);
    }

    @Test
    public void getUser_withIncorrectCredentials_returnsFalse() {
        // A user that has not been added to the database
        UserModel nonExistentUser = new UserModel(-1, "wrongUsername", "wrongPassword");

        // Try to retrieve the non-existent user from the database
        boolean result = databaseHelper.getUser(nonExistentUser.getUsername(), nonExistentUser.getPassword());

        // Result should be false
        assertFalse(result);
    }

    @After
    public void tearDown() {
        databaseHelper.close();
    }
}

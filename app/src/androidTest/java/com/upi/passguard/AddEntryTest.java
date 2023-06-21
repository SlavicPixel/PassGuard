package com.upi.passguard;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddEntryTest {

    private ActivityScenario<MainActivity> scenario;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        // Initialize Espresso Intents capturing
        Intents.init();
        // Start Activity
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void addEntryTest() {

        // 1. In MainActivity, input user credentials and click the login button
        onView(withId(R.id.username)).perform(typeText("addEntryTest"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("addentrytesting"), closeSoftKeyboard());
        onView(withId(R.id.loginbtn)).perform(click());

        // 2. After login, VaultViewActivity activity should be opened
        intended(hasComponent(VaultViewActivity.class.getName()));

        // 3. In VaultViewActivity activity, click on Add Entry button
        onView(withId(R.id.newEntryButton)).perform(click());

        // 4. After AddEntryActivity button click, AddEntryActivity activity should be opened
        intended(hasComponent(AddEntryActivity.class.getName()));

        // 5. In AddEntryActivity activity, input entry information and click the add entry button
        String Title = "Add Entry Test";
        String Username = "testUser";
        String Password = "testing";
        String URL = "https://www.passguard.com/";
        String Notes = "This is a test for adding an entry";

        onView(withId(R.id.titleEntry)).perform(typeText(Title), closeSoftKeyboard());
        onView(withId(R.id.usernameEntry)).perform(typeText(Username), closeSoftKeyboard());
        onView(withId(R.id.passwordEntry)).perform(typeText(Password), closeSoftKeyboard());
        onView(withId(R.id.urlEntry)).perform(typeText(URL), closeSoftKeyboard());
        onView(withId(R.id.notesEntry)).perform(typeText(Notes), closeSoftKeyboard());
        onView(withId(R.id.addEntryButton)).perform(click());

        // 6. New entry should appear and be clickable
        onView(withId(R.id.entriesRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        // 7. EntryViewActivity activity should appear
        intended(hasComponent(EntryViewActivity.class.getName()));

        // 8. Check if correct values were inserted
        onView(withId(R.id.titleTextView)).check(matches(withText(Title)));
        onView(withId(R.id.usernameTextView)).check(matches(withText(Username)));
        onView(withId(R.id.passwordTextView)).check(matches(withText(Password)));
        onView(withId(R.id.urlTextView)).check(matches(withText(URL)));
        onView(withId(R.id.notesTextView)).check(matches(withText(Notes)));
    }

    @After
    public void cleanup() {
        // Release Espresso Intents capturing
        Intents.release();
        // Close the activity scenario
        if (scenario != null) {
            scenario.close();
        }
    }
}


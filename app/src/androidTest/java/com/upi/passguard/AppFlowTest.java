package com.upi.passguard;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.Matchers.containsString;
import static java.util.regex.Pattern.matches;

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
public class AppFlowTest {

    private ActivityScenario<MainActivity> scenario;

    @Rule
    public ActivityScenarioRule<MainActivity> intentsTestRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        // Initialize Espresso Intents capturing
        Intents.init();
        // Start Activity
        scenario = ActivityScenario.launch(MainActivity.class);
    }
    
    @Test
    public void appFlowTest() {

        // 1. From MainActivity, click on the register button to open RegisterActivity
        onView(withId(R.id.newaccbtn)).perform(click());
        intended(hasComponent(NewAccount.class.getName()));

        // 2. In NewAccounnt activity, input user information and click the register button
        onView(withId(R.id.newUsername)).perform(typeText("AppFlowTestingUsername"), closeSoftKeyboard());
        onView(withId(R.id.newPassword)).perform(typeText("appFlowTestingPassword"), closeSoftKeyboard());
        onView(withId(R.id.newPasswordConfirm)).perform(typeText("appFlowTestingPassword"), closeSoftKeyboard());
        onView(withId(R.id.newaccbtn)).perform(click());

        // 4. In MainActivity, input user credentials and click the login button
        onView(withId(R.id.username)).perform(typeText("AppFlowTestingUsername"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("appFlowTestingPassword"), closeSoftKeyboard());
        onView(withId(R.id.loginbtn)).perform(click());

        // 5. After login, VaultView activity should be opened
        intended(hasComponent(VaultView.class.getName()));

        // 6. In VaultView activity, click on Add Entry button
        onView(withId(R.id.newEntryButton)).perform(click());

        // 7. After AddEntry button click, AddEntry activity should be opened
        intended(hasComponent(AddEntry.class.getName()));

        // 8. In AddEntry activity, input entry information and click the add entry button
        String Title = "testTitle";
        String Username = "testUsername";
        String Password = "testPassword";
        String URL = "testUrl";
        String Notes = "testNotes";

        onView(withId(R.id.titleEntry)).perform(typeText(Title), closeSoftKeyboard());
        onView(withId(R.id.usernameEntry)).perform(typeText(Username), closeSoftKeyboard());
        onView(withId(R.id.passwordEntry)).perform(typeText(Password), closeSoftKeyboard());
        onView(withId(R.id.urlEntry)).perform(typeText(URL), closeSoftKeyboard());
        onView(withId(R.id.notesEntry)).perform(typeText(Notes), closeSoftKeyboard());
        onView(withId(R.id.addEntryButton)).perform(click());

        // 9. New entry should appear and be clickable
        // This test assumes the database was empty prior to adding the first entry
        onView(withId(R.id.entriesRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        // 10. EntryView activity should appear
        intended(hasComponent(EntryView.class.getName()));

        // 11. Check if correct values were inserted
        onView(withId(R.id.titleTextView)).check(matches(withText(Title)));
        onView(withId(R.id.usernameTextView)).check(matches(withText(Username)));
        onView(withId(R.id.passwordTextView)).check(matches(withText(Password)));
        onView(withId(R.id.urlTextView)).check(matches(withText(URL)));
        onView(withId(R.id.notesTextView)).check(matches(withText(Notes)));

        // 12. Click the edit button to edit the selected entry
        onView(withId(R.id.editEntryButton)).perform(click());

        // 14. In EditEntry activity, edit the selected entry and updated it with new values
        onView(withId(R.id.titleTextView)).perform(typeText("Updated"), closeSoftKeyboard());
        onView(withId(R.id.usernameTextView)).perform(typeText("Updated"), closeSoftKeyboard());
        onView(withId(R.id.passwordTextView)).perform(typeText("Updated"), closeSoftKeyboard());
        onView(withId(R.id.urlTextView)).perform(typeText("Updated"), closeSoftKeyboard());
        onView(withId(R.id.notesTextView)).perform(typeText("Updated"), closeSoftKeyboard());
        onView(withId(R.id.updateEntryButton)).perform(click());

        // 15. Click on the same entry again with intent to delete it
        onView(withId(R.id.entriesRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        //16. EntryView activity should appear, check if values were updates
        onView(withId(R.id.titleTextView)).check(matches(withText(Title + "Updated")));
        onView(withId(R.id.usernameTextView)).check(matches(withText(Username + "Updated")));
        onView(withId(R.id.passwordTextView)).check(matches(withText(Password + "Updated")));
        onView(withId(R.id.urlTextView)).check(matches(withText(URL + "Updated")));
        onView(withId(R.id.notesTextView)).check(matches(withText(Notes + "Updated")));

        // 17. Open options menu and click delete button
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Delete entry")).perform(click());

        // 18. Open options menu and click logout button
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Logout")).perform(click());

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

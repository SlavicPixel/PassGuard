package com.upi.passguard;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegistrationTest {

    private ActivityScenario<NewAccountActivity> scenario;

    @Rule
    public ActivityScenarioRule<NewAccountActivity> activityRule =
            new ActivityScenarioRule<>(NewAccountActivity.class);

    @Before
    public void setup() {
        // Initialize Espresso Intents capturing
        Intents.init();
        // Start Activity
        scenario = ActivityScenario.launch(NewAccountActivity.class);
    }

    @Test
    public void appFlowTest() {

        // 1. In NewAccountActivity activity, input username with password which do not match and click the register button
        onView(withId(R.id.newUsername)).perform(typeText("RegistrationTest"), closeSoftKeyboard());
        onView(withId(R.id.newPassword)).perform(typeText("RegistrationTesting"), closeSoftKeyboard());
        onView(withId(R.id.newPasswordConfirm)).perform(typeText("DoNotMatch"), closeSoftKeyboard());
        onView(withId(R.id.newaccbtn)).perform(click());

        // Passwords do not match and MainActivity (login page) should not appear
        intended(not(hasComponent(MainActivity.class.getName())));

        // 2. Input a username which is already used by another user
        // In this example, testUser exists in the db
        onView(withId(R.id.newUsername)).perform(clearText());
        onView(withId(R.id.newUsername)).check(matches(withText("")));
        onView(withId(R.id.newUsername)).perform(typeText("testUser"), closeSoftKeyboard());
        onView(withId(R.id.newPassword)).perform(clearText());
        onView(withId(R.id.newPassword)).check(matches(withText("")));
        onView(withId(R.id.newPassword)).perform(typeText("RegistrationTesting"), closeSoftKeyboard());
        onView(withId(R.id.newPasswordConfirm)).perform(clearText());
        onView(withId(R.id.newPasswordConfirm)).check(matches(withText("")));
        onView(withId(R.id.newPasswordConfirm)).perform(typeText("RegistrationTesting"), closeSoftKeyboard());
        onView(withId(R.id.newaccbtn)).perform(click());

        // MainActivity (login page) should not appear
        intended(not(hasComponent(MainActivity.class.getName())));

        // 3. Enter a username which doesn't exist and passwords which match
        onView(withId(R.id.newUsername)).perform(clearText());
        onView(withId(R.id.newUsername)).check(matches(withText("")));
        onView(withId(R.id.newUsername)).perform(typeText("ThisUsernameIsNotUsed"), closeSoftKeyboard());
        onView(withId(R.id.newPassword)).perform(clearText());
        onView(withId(R.id.newPassword)).check(matches(withText("")));
        onView(withId(R.id.newPassword)).perform(typeText("RegistrationTesting"), closeSoftKeyboard());
        onView(withId(R.id.newPasswordConfirm)).perform(clearText());
        onView(withId(R.id.newPasswordConfirm)).check(matches(withText("")));
        onView(withId(R.id.newPasswordConfirm)).perform(typeText("RegistrationTesting"), closeSoftKeyboard());
        onView(withId(R.id.newaccbtn)).perform(click());
        onView(ViewMatchers.withText("I understand")).perform(ViewActions.click());

        // 4. In MainActivity, input user credentials and click the login button
        // Checking if registration was successful
        onView(withId(R.id.username)).perform(typeText("ThisUsernameIsNotUsed"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("RegistrationTesting"), closeSoftKeyboard());
        onView(withId(R.id.loginbtn)).perform(click());

        // 5. After login, VaultViewActivity activity should appear
        intended(hasComponent(VaultViewActivity.class.getName()));
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

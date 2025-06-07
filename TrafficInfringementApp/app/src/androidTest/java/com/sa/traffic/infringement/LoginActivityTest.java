package com.sa.traffic.infringement;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText; // For toolbar title check
import static org.hamcrest.CoreMatchers.allOf; // For combining matchers like withId and hasDescendant

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        // Initialize Espresso-Intents before each test
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Espresso-Intents after each test
        Intents.release();
    }

    @Test
    public void uiElements_areDisplayed() {
        // Check Toolbar
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(allOf(withText("Officer Login"), isDisplayed())); // More direct check for title

        // Check EditTexts and Button
        onView(withId(R.id.editTextUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonLogin)).check(matches(isDisplayed()));
    }

    @Test
    public void buttonLogin_navigatesToMainActivity() {
        onView(withId(R.id.buttonLogin)).perform(click());

        // Verify that MainActivity is launched
        intended(hasComponent(MainActivity.class.getName()));

        // And check if a view unique to MainActivity is displayed (e.g., its toolbar title or a button)
        onView(withId(R.id.toolbar)).check(matches(isDisplayed())); // MainActivity should also have a toolbar
        onView(allOf(withText("Dashboard"), isDisplayed())); // Check for MainActivity's title
        onView(withId(R.id.buttonScanDriverLicense)).check(matches(isDisplayed()));
    }
}

package com.sa.traffic.infringement;

import android.app.Activity;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith; // For more flexible Toast matching

@RunWith(AndroidJUnit4.class)
@LargeTest
public class IssueTicketActivityTest {

    @Rule
    public ActivityScenarioRule<IssueTicketActivity> activityRule =
            new ActivityScenarioRule<>(IssueTicketActivity.class);

    @Test
    public void testIssueTicket_validInput_showsConfirmationToast() {
        String offenseType = "Speeding";
        String location = "N1 Highway, Midrand";
        String dateTime = "2023-11-15 10:30";
        String notes = "Driver exceeded speed limit by 20km/h.";
        String amount = "500.00";

        // Type text into the fields
        onView(withId(R.id.editTextOffenseType)).perform(typeText(offenseType), closeSoftKeyboard());
        onView(withId(R.id.editTextLocation)).perform(typeText(location), closeSoftKeyboard());
        onView(withId(R.id.editTextDateTime)).perform(typeText(dateTime), closeSoftKeyboard());
        onView(withId(R.id.editTextNotes)).perform(typeText(notes), closeSoftKeyboard());
        onView(withId(R.id.editTextAmount)).perform(typeText(amount), closeSoftKeyboard());

        // Click the submit button
        onView(withId(R.id.buttonSubmitTicket)).perform(click());

        // Verify the Toast message
        // Store the activity in a final array to use it in the matcher
        final Activity[] activityHolder = new Activity[1];
        activityRule.getScenario().onActivity(activity -> {
            activityHolder[0] = activity;
        });

        // The Toast message in IssueTicketActivity is "Ticket Issued:\n" + offenseType + " at " + location
        // We should match this specific text.
        String expectedToastMessage = "Ticket Issued:\n" + offenseType + " at " + location;

        onView(withText(expectedToastMessage))
                .inRoot(withDecorView(not(is(activityHolder[0].getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        // Optional: Verify fields are cleared after submission (if this is the behavior)
        onView(withId(R.id.editTextOffenseType)).check(matches(withText("")));
        onView(withId(R.id.editTextLocation)).check(matches(withText("")));
        // ... and so on for other fields
    }

    @Test
    public void testIssueTicket_emptyFields_showsValidationErrorToast() {
        // Attempt to click submit without filling fields
        onView(withId(R.id.buttonSubmitTicket)).perform(click());

        // Store the activity in a final array to use it in the matcher
        final Activity[] activityHolder = new Activity[1];
        activityRule.getScenario().onActivity(activity -> {
            activityHolder[0] = activity;
        });

        // The Toast message in IssueTicketActivity for validation is "Please fill all required fields"
        String expectedToastMessage = "Please fill all required fields";

        onView(withText(expectedToastMessage))
                .inRoot(withDecorView(not(is(activityHolder[0].getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }
}

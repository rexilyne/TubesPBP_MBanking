package com.example.tubespbp_mbanking.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.tubespbp_mbanking.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterTest {

    @Rule
    public ActivityTestRule<AuthenticationActivity> mActivityTestRule = new ActivityTestRule<>(AuthenticationActivity.class);

    @Test
    public void registerTest() {
        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.btnRegister), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                7),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                14),
                        isDisplayed()));
        materialButton.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.et_first_name),
                                0),
                        1),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Espresso"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                14),
                        isDisplayed()));
        materialButton2.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.et_last_name),
                                0),
                        1),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("Test"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                14),
                        isDisplayed()));
        materialButton3.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.et_email),
                                0),
                        1),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("espressotest"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                14),
                        isDisplayed()));
        materialButton4.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText4 = onView(
                allOf(withText("espressotest"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.et_email),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText4.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withText("espressotest"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.et_email),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("espressotest@gmail.com"));

        ViewInteraction textInputEditText6 = onView(
                allOf(withText("espressotest@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.et_email),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                14),
                        isDisplayed()));
        materialButton5.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText7 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.et_password),
                                0),
                        1),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("espresso123"), closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                14),
                        isDisplayed()));
        materialButton6.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText8 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.et_account_number),
                                0),
                        1),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("1234567890"), closeSoftKeyboard());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                14),
                        isDisplayed()));
        materialButton7.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText10 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.et_pin),
                                0),
                        1),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                14),
                        isDisplayed()));
        materialButton8.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText11 = onView(
                allOf(withText("123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.et_pin),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText11.perform(click());

        ViewInteraction textInputEditText12 = onView(
                allOf(withText("123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.et_pin),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText12.perform(click());

        ViewInteraction textInputEditText13 = onView(
                allOf(withText("123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.et_pin),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText13.perform(replaceText("123456"));

        ViewInteraction textInputEditText14 = onView(
                allOf(withText("123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.et_pin),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText14.perform(closeSoftKeyboard());

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_auth_frag),
                                        0),
                                14),
                        isDisplayed()));
        materialButton9.perform(click());

        onView(isRoot()).perform(waitFor(3000));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return isRoot();
            }
            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }
            @Override public void perform(UiController uiController,
                                          View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }
}

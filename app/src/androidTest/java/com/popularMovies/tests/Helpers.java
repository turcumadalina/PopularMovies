package com.popularMovies.tests;

import android.os.SystemClock;
import android.support.test.espresso.AppNotIdleException;
import android.support.test.espresso.NoMatchingRootException;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularMovies.constants.Time;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by valentin.boca on 2/14/2018.
 */

public class Helpers extends EspressoTestBase {
    public static boolean checkIfUIObjectIsVisible(Matcher<View> matcher, int waitTimeInSeconds) {
        boolean isVisible = false;
        long endTime;

        endTime = System.currentTimeMillis() + waitTimeInSeconds * Time.ONE_SECOND;

        while(!isVisible && System.currentTimeMillis() <= endTime) {
            try {
                onView(matcher).check(matches(isDisplayed()));
                isVisible = true;
            } catch(NoMatchingViewException | AppNotIdleException | AssertionFailedError | NoMatchingRootException e) {
                // do nothing
            }
        }

        return isVisible;
    }

    public static boolean checkIfAlertIsVisible(String text, int waitTimeInSeconds) {
        boolean isVisible = false;
        long endTime;

        endTime = System.currentTimeMillis() + waitTimeInSeconds * Time.ONE_SECOND;

        while(!isVisible && System.currentTimeMillis() <= endTime) {
            try {
                device.findObject(new UiSelector().text(text));
                isVisible = true;
            } catch(NoMatchingViewException | AppNotIdleException | AssertionFailedError | NoMatchingRootException e) {
                // do nothing
            }
        }

        return isVisible;
    }

    public static boolean clickOnAView(int rid2, Matcher<View> matcher, int position) {
        boolean found = false;
        int i = 0;
        int MAX_SWIPES = 2;
        while(!found && i < MAX_SWIPES) {
            onView(withId(rid2)).perform(swipeUp());
            SystemClock.sleep(500);
            try {
                onView(matcher).check(matches(isDisplayed())).perform(actionOnItemAtPosition(position, click()));
                found = true;
            } catch(Exception e) {
                // The search continues
            }
            i++;
        }

        if(!found) {
            Assert.fail("The element has not been found.");
        }
        return found;
    }

    public static Matcher<View> mediaItemPosition(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with "+childPosition+" child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };
    }

    public static boolean checkIfItemIsListed(int rid2, Matcher<View> matcher) {
        boolean found = false;
        int i = 0;
        int MAX_SWIPES = 10;
        while(!found && i < MAX_SWIPES) {
            onView(withId(rid2)).perform(swipeUp());
            SystemClock.sleep(450);
            try {
                onView(matcher).check(matches(isDisplayed())).perform(click());
                found = true;
            } catch(Exception e) {
                // The search continues
            }
            i++;
        }

        if(!found) {
            Assert.fail("The element has not been found.");
        }
        return found;
    }

    public static boolean isItemDisplayed(String string) throws Exception {
        return Helpers.checkIfUIObjectIsVisible(allOf(withText(string), isCompletelyDisplayed()), 3);
    }

    public static boolean isTextDisplayed(String text, int rid) throws Exception {
        return Helpers.getText(withId(rid)).equalsIgnoreCase(text);
    }

    public static boolean isYouTubeDisplayed(String text) throws Exception {
        return Helpers.getUiObjectByPackage(text).equals(text);
    }

    public static String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view;
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    public static UiObject getUiObjectByText(String text) throws Exception {
        return device.findObject(new UiSelector().text(text));
    }

    public static UiObject getUiObjectByResourceId(String nameSpace, String resourceId) throws Exception {
        return device.findObject(new UiSelector().resourceId(nameSpace + ":id/" + resourceId));
    }

    public static UiObject getUiObjectByPackage(String text) throws Exception {
        return device.findObject(new UiSelector().text(text));
    }

    public static UiObject getUiObjectByClass(String text) throws Exception {
        return device.findObject(new UiSelector().className(text));
    }

    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }
}
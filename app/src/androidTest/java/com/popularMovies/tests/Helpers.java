package com.popularMovies.tests;

import android.support.test.espresso.AppNotIdleException;
import android.support.test.espresso.NoMatchingRootException;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.view.View;
import android.widget.TextView;

import com.popularMovies.constants.Time;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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

        while (!isVisible && System.currentTimeMillis() <= endTime) {
            try {
                onView( matcher ).check( matches( isDisplayed() ) );
                isVisible = true;
            } catch (NoMatchingViewException | AppNotIdleException | AssertionFailedError | NoMatchingRootException e) {
                // do nothing
            }
        }

        return isVisible;
    }
    public static boolean checkIfAlertIsVisible(String text, int waitTimeInSeconds) {
        boolean isVisible = false;
        long endTime;

        endTime = System.currentTimeMillis() + waitTimeInSeconds * Time.ONE_SECOND;

        while (!isVisible && System.currentTimeMillis() <= endTime) {
            try {
                device.findObject( new UiSelector().text(text));
                isVisible = true;
            } catch (NoMatchingViewException | AppNotIdleException | AssertionFailedError | NoMatchingRootException e) {
                // do nothing
            }
        }

        return isVisible;
    }
    public static boolean isItemDisplayed(String text) throws Exception {
        return Helpers.checkIfUIObjectIsVisible(allOf(withText(text), isCompletelyDisplayed()), 3);
    }
    public static boolean isTextDisplayed(String text, int rid) throws Exception{
        return Helpers.getText(withId(rid)).equalsIgnoreCase(text);
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
        return device.findObject( new UiSelector().text(text));
    }
    public static UiObject getUiObjectByResourceId(String nameSpace, String resourceId) throws Exception {
        return device.findObject( new UiSelector().resourceId( nameSpace + ":id/" + resourceId ) );
    }
}




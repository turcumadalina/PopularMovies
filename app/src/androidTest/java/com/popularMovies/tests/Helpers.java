package com.popularMovies.tests;

import android.support.test.espresso.AppNotIdleException;
import android.support.test.espresso.NoMatchingRootException;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.popularMovies.constants.Time;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ioana.hoaghia on 2/14/2018.
 */

public class Helpers extends EspressoTestBase {

    public static int getRecyclerViewChildCount(final Matcher<View> matcher) {
        final int[] count = {0};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(android.support.v7.widget.RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "getting child count";
            }

            @Override
            public void perform(UiController uiController, View view) {
                android.support.v7.widget.RecyclerView rv = (android.support.v7.widget.RecyclerView) view;
                count[0] = rv.getChildCount();
            }
        });
        return count[0];
    }


    public static int getGridViewChildCount(final Matcher<View> matcher) {
        final int[] count = {0};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(android.widget.GridView.class);
            }

            @Override
            public String getDescription() {
                return "getting child count";
            }

            @Override
            public void perform(UiController uiController, View view) {
                android.widget.GridView gv = (android.widget.GridView) view;
                count[0] = gv.getChildCount();
            }
        });
        return count[0];
    }

    public static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent) && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static void clickOnItem (int id)
    {
        onView(withId(id)).perform(click());

    }

    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
    }

    public static boolean checkIfUIObjectIsVisible(Matcher<View> matcher, int waitTimeInSeconds) {
        boolean isVisible = false;
        long endTime;

        endTime = System.currentTimeMillis() + waitTimeInSeconds * Time.ONE_SECOND;

        while (!isVisible && System.currentTimeMillis() <= endTime) {
            try {
                onView(matcher).check(matches(isDisplayed()));
                isVisible = true;
            } catch (NoMatchingViewException | AppNotIdleException | AssertionFailedError | NoMatchingRootException e) {
                // do nothing
            }
        }

        return isVisible;
    }

    /**
     * Gets the text from the matcher view
     *
     * @param matcher matcher
     */
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
}
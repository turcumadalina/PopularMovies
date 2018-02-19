package com.popularMovies.screens;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;

import com.popularMovies.tests.Helpers;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.popularMovies.tests.Helpers.mediaItemPosition;
import static com.popularMovies.tests.Helpers.withCustomConstraints;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by valentin.boca on 2/14/2018.
 */

public class Movie {
    public static void bookmarkItem() throws Exception{
        onView(withId(R.id.bookmark)).perform(click());
    }

    public static void shareMovie() throws Exception{
        onView(withId(R.id.share)).perform(click());
        UiObject messages = Helpers.getUiObjectByText("Messaging");
        messages.click();
        UiObject discardMessage = Helpers.getUiObjectByResourceId("android", "up");
        discardMessage.click();
        UiObject okMessageButton = Helpers.getUiObjectByText("OK");
        okMessageButton.click();
    }

    public static boolean isTitleDisplayed(String title) throws Exception{
        return Helpers.checkIfUIObjectIsVisible(allOf(withText(title), isCompletelyDisplayed()), 3);
    }

    public static boolean isActorDisplayed(int rid) throws Exception{
        return Helpers.checkIfUIObjectIsVisible(allOf(withId(rid), isCompletelyDisplayed()), 3);
    }

    public static boolean isMovieDisplayed(int backgroundmovieimage) throws Exception{
        return Helpers.checkIfUIObjectIsVisible(allOf(withId(backgroundmovieimage), isCompletelyDisplayed()), 3);
    }

    public static void playMovie() throws Exception{
        onView(withId(R.id.play)).perform(click());
        Helpers.isYouTubeDisplayed("com.google.android.youtube");
        Movie.navigateBackToApp();
    }

    public static void navigateBackToApp() throws Exception{
        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressRecentApps();
        UiObject clickOptiMovies = Helpers.getUiObjectByText("Opti Movies");
        clickOptiMovies.click();
    }

    public static void navigateToElementFromRecyclerView(int rid, int position) throws Exception{
        onView(withId(rid)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
        onView(mediaItemPosition(withId(rid), position)).perform( click() );
    }

    public static void navigateToElement(int rid) throws Exception{
        onView(withId(rid)).perform(ViewActions.scrollTo()).check(matches(isDisplayed())).perform(click());
    }

    public static void pullToRefresh(int rid) throws Exception{
        onView(withId(rid)).perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)));
    }
}
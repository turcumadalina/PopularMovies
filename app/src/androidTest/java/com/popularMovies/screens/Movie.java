package com.popularMovies.screens;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;

import com.popularMovies.tests.Helpers;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by valentin.boca on 2/14/2018.
 */

public class Movie {
    public static void bookmarkMovie() throws Exception{
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
}
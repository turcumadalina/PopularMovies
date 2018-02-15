package com.popularMovies.screens;

import android.support.test.uiautomator.UiObject;

import com.popularMovies.tests.Helpers;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
}

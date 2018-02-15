package com.popularMovies.screens;

import com.popularMovies.tests.Helpers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by valentin.boca on 2/14/2018.
 */

public class NowPlayingMovies {
    public static void clickAMovieNowPlayingMovies(String text) throws Exception {
        onView(withText(text)).perform(click());
        Helpers.isItemDisplayed(text);
    }
}

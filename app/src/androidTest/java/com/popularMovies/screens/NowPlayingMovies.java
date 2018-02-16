package com.popularMovies.screens;

import com.popularMovies.constants.Strings;
import com.popularMovies.tests.Helpers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by valentin.boca on 2/14/2018.
 */

public class NowPlayingMovies {
    public static void clickAMovieNowPlayingMovies(String text) throws Exception {
        onView(withText(text)).perform(click());
        Helpers.isItemDisplayed(text);
    }

    public static boolean isTitleDisplayed() throws Exception{
        return Helpers.checkIfUIObjectIsVisible(allOf(withText(Strings.NOW_PLAYING_MOVIES), isCompletelyDisplayed()), 3);
    }
}
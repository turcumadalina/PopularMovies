package com.popularMovies.screens;

import com.popularMovies.constants.Strings;
import com.popularMovies.tests.Helpers;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
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

    public static boolean isNowPlayingMovies() throws Exception{
        try{
            onView( allOf( withText(Strings.NOW_PLAYING_MOVIES), isCompletelyDisplayed())).check(matches(isDisplayed()));
        } catch(Exception e){
            return true;
        }
        return false;
    }
}
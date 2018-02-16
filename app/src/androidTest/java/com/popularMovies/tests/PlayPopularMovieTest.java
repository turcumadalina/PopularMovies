package com.popularMovies.tests;

import com.popularMovies.constants.Timeouts;
import com.popularMovies.screens.Movies;

import org.junit.Test;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ioana.hoaghia on 2/14/2018.
 */

public class PlayPopularMovieTest extends EspressoTestBase  {

    @Test(timeout = Timeouts.TEST_TIMEOUT_SHORT)
    public void navigationToGetPopularTest () throws Exception {
        // Navigate to the 'Get popular' screen
        Movies.navigationToGetPopular();
        // Tap the first video in the list
        onView(Helpers.childAtPosition(withId(R.id.gridview_movie),0)).perform(click());
        // Tap 'Play'
        onView(withId(R.id.play)).perform(click());
    }
}
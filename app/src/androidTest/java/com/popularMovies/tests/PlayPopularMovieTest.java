package com.popularMovies.tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;

import com.popularMovies.constants.Strings;
import com.popularMovies.constants.Timeouts;
import com.popularMovies.screens.Movies;

import org.junit.Test;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

/**
 * Created by ioana.hoaghia on 2/14/2018.
 */

public class PlayPopularMovieTest extends EspressoTestBase  {

    @Test(timeout = Timeouts.TEST_TIMEOUT_LONG)
    public void playPopularTest () throws Exception {
        // Navigate to the 'Get popular' screen
        Movies.navigationToGetPopular();
        // Tap the first video in the list
        onView(Helpers.childAtPosition(withId(R.id.gridview_movie),0)).perform(click());
        // Tap 'Play'
        onView(withId(R.id.play)).perform(click());
        Thread.sleep(1000);

        UiObject youtubeWindow = Helpers.getUiObjectByPackage(Strings.PACKAGE_NAME_YOUTUBE);
        //Assert the video is opened
        assertTrue(youtubeWindow.exists());
        // Go back to the Application Under Test
        Helpers.tapBack();
    }
}
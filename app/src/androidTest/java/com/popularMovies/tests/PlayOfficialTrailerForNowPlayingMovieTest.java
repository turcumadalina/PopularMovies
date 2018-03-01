package com.popularMovies.tests;

import android.support.test.uiautomator.UiObject;

import com.popularMovies.constants.Strings;
import com.popularMovies.constants.Timeouts;
import com.popularMovies.screens.Movies;

import org.junit.Test;

import work.technie.popularmovies.R;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertFalse;

/**
 * Created by ioana.hoaghia on 2/21/2018.
 */

public class PlayOfficialTrailerForNowPlayingMovieTest extends EspressoTestBase {

    @Test(timeout = Timeouts.TEST_TIMEOUT_SHORT)
    public void playOfficialTrailerForNowPlayingMovieIfAvailable() throws Exception {
        Movies.navigationToGetNowPlaying();
        // Scroll the Movies page E times (note that sometimes, the following method returns 'Target area not visible' error. If  the app is force closed before executing the test,
        // it is be ok)
        Helpers.scrollTheMoviesScreenETimes(3);
        onView(Helpers.childAtPosition(withId(R.id.gridview_movie),2)).check(matches(isCompletelyDisplayed()));
        Thread.sleep(1000);
        Movies.tapVideoItemByIndex(2);
        Thread.sleep(1000);
        // Scroll the Single Movie screen until the Official trailers recycler view is displayed.
        Helpers.scrollDownUntilObjectIsCompletelyVisible(withId(R.id.recyclerview_videos),35);
        //Check the recycler view is completely displayed
        onView(withId(R.id.recyclerview_videos)).check(matches(isCompletelyDisplayed())).perform();
        // Tap the first Item from the Videos section (where the official trailers are)
        onView(Helpers.childAtPosition(withId(R.id.recyclerview_videos),0)).perform(click());
        Thread.sleep(1000);
        UiObject youtubeWindow = Helpers.getUiObjectByPackage(Strings.PACKAGE_NAME_YOUTUBE);
        //Assert the video is opened with youtube
        assertFalse("AssertionError: Youtube not opened!",!youtubeWindow.exists());
        // Go back to the Application Under Test
        Helpers.tapBack();
    }
}
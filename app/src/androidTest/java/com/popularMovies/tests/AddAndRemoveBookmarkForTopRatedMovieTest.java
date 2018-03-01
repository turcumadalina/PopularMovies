package com.popularMovies.tests;

import com.popularMovies.constants.Strings;
import com.popularMovies.constants.Timeouts;
import com.popularMovies.screens.Movies;

import org.junit.Test;

/**
 * Created by ioana.hoaghia on 2/20/2018.
 */

public class AddAndRemoveBookmarkForTopRatedMovieTest extends EspressoTestBase {

    @Test(timeout = Timeouts.TEST_TIMEOUT_SHORT)
    public void addBookmarkForTopRatedTest() throws Exception {

        // Navigate to the 'Get TopRated' screen
        Movies.navigationToGetTopRated();
        // Tap the first video in the list
        Movies.tapVideoItemByIndex(0);
        // Tap 'Bookmark' for adding a bookmark
        Movies.tapBookmarkForVideo();
        // Check the video is bookmarked by checking the toast message 'Added to bookmarks'
        Helpers.isToastMessageWithTextDisplayed(Strings.TOAST_MESSAGE_BOOKMARK_ADDED);
        Thread.sleep(3000);
        // Tap 'Bookmark' for removing a bookmark
        Movies.tapBookmarkForVideo();
        // Check the bookmark is removed by checking the toast message 'Removed from bookmarks'
        Helpers.isToastMessageWithTextDisplayed(Strings.TOAST_MESSAGE_BOOKMARK_REMOVED);
        Thread.sleep(1000);
    }
}

package com.popularMovies.tests;

import com.popularMovies.constants.Strings;
import com.popularMovies.screens.BookmarkMovie;
import com.popularMovies.screens.Movie;
import com.popularMovies.screens.NavDrawer;
import com.popularMovies.screens.NowPlayingMovies;

import org.junit.Assert;
import org.junit.Test;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by valentin.boca on 2/14/2018.
 */

public class BookmarkTests extends EspressoTestBase {

    @Test
    public void testiIfBookmarkedMoviesIsEmpty() throws Exception {
        NavDrawer.bookmarkedMovies();
        Assert.assertTrue("The No bookmarks are available string is not displayed.", BookmarkMovie.isNoBookmarksAdded());
    }

    @Test
    public void testAddNewItemToBookmarkedMovies() throws Exception {
        NowPlayingMovies.clickAMovieNowPlayingMovies("5.9/10");
        Movie.bookmarkMovie();
        pressBack();
        NavDrawer.bookmarkedMovies();
        Assert.assertTrue("The Nutjob 2 is not displayed.", Movie.isTitleDisplayed("5.9/10"));
    }

    @Test
    public void testShareItem() throws Exception {
        NowPlayingMovies.clickAMovieNowPlayingMovies("224");
        Movie.shareMovie();
        Assert.assertTrue("The Nutjob 2 is not displayed.", Movie.isTitleDisplayed(Strings.NUTJOB2));
    }

    @Test
    public void testPlayItem() throws Exception {
        NowPlayingMovies.clickAMovieNowPlayingMovies("224");
        Movie.playMovie();
        Assert.assertTrue("The NowPlayingMovies screen is not displayed.", Movie.isTitleDisplayed(Strings.NUTJOB2));
    }

    @Test
    public void testRedirectingToHomePage() throws Exception {
        NowPlayingMovies.clickAMovieNowPlayingMovies("224");
        Helpers.checkIfItemIsListed(R.id.drawer_layout, withText("http://thenutjob.com"));
        Movie.navigateBackToApp();
        Assert.assertTrue("The NowPlayingMovies screen is not displayed.", NowPlayingMovies.isTitleDisplayed());
    }

    @Test
    public void testIfAnActorIsDisplayed() throws Exception{
        NowPlayingMovies.clickAMovieNowPlayingMovies("224");
        Helpers.clickOnAChild(R.id.drawer_layout, withId(R.id.recyclerview_cast), 1);
        Assert.assertTrue("The Katherine Heigl is not displayed.", Movie.isTitleDisplayed(Strings.KATHERINEHEIGL));
    }
}
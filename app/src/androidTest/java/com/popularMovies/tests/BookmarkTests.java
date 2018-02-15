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

/**
 * Created by valentin.boca on 2/14/2018.
 */

public class BookmarkTests extends EspressoTestBase {

    @Test
    public void testiIfBookmarkedMoviesIsEmpty() throws Exception {
        NavDrawer.bookmarkedMovies();
        Assert.assertTrue(Strings.NO_BOOKMARKS, BookmarkMovie.isNoBookmarksAdded());
    }

    @Test
    public void testAddNewItemToBookmarkedMovies() throws Exception {
        NowPlayingMovies.clickAMovieNowPlayingMovies("6.0/10");
        Movie.bookmarkMovie();
        pressBack();
        NavDrawer.bookmarkedMovies();
        Helpers.isTextDisplayed(  "6.0/10", R.id.vote_text);
    }

    @Test
    public void testShareItem() throws Exception {
        NowPlayingMovies.clickAMovieNowPlayingMovies("224");
        Movie.shareMovie();
        Helpers.isItemDisplayed("The Nut Job 2: Nutty by Nature");
    }

    @Test
    public void testPlayItem() throws Exception {
        NowPlayingMovies.clickAMovieNowPlayingMovies("224");
        Movie.playMovie();
        Assert.assertTrue(Strings.NOW_PLAYING_MOVIES, NowPlayingMovies.isNowPlayingMovies());
    }
}
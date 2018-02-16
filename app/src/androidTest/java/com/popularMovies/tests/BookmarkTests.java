package com.popularMovies.tests;

import com.popularMovies.constants.Strings;
import com.popularMovies.screens.BookmarkMovie;
import com.popularMovies.screens.Movie;
import com.popularMovies.screens.NavDrawer;
import com.popularMovies.screens.NowPlayingMovies;

import org.junit.Assert;
import org.junit.Test;

import work.technie.popularmovies.R;
import work.technie.popularmovies.data.MovieContract;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.popularMovies.tests.Helpers.mediaItemPosition;

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
    public void testAddNewMovieToBookmarkedMovies() throws Exception {
        onView(mediaItemPosition(withId(R.id.gridview_movie), 0)).perform( click() );
        Movie.bookmarkItem();
        pressBack();
        NavDrawer.bookmarkedMovies();
        Assert.assertTrue("The Nutjob 2 is not displayed.", Movie.isTitleDisplayed(Strings.NUTJOB2));
    }

    @Test
    public void testShareItem() throws Exception {
        onView(mediaItemPosition(withId(R.id.gridview_movie), 0)).perform( click() );
        Movie.shareMovie();
        Assert.assertTrue("The Nutjob 2 is not displayed.", Movie.isTitleDisplayed(Strings.NUTJOB2));
    }

    @Test
    public void testPlayItem() throws Exception {
        onView(mediaItemPosition(withId(R.id.gridview_movie), 0)).perform( click() );
        Movie.playMovie();
        Assert.assertTrue("The NowPlayingMovies screen is not displayed.", Movie.isTitleDisplayed(Strings.NUTJOB2));
    }

    @Test
    public void testRedirectingToHomePage() throws Exception {
        onView(mediaItemPosition(withId(R.id.gridview_movie), 0)).perform( click() );
        Helpers.checkIfItemIsListed(R.id.drawer_layout, withId(R.id.homepage));
        Movie.navigateBackToApp();
        Assert.assertTrue("The NowPlayingMovies screen is not displayed.", Movie.isMovieDisplayed(R.id.homepage));
    }

    @Test
    public void testIfAnActorIsDisplayed() throws Exception {
        onView(mediaItemPosition(withId(R.id.gridview_movie), 0)).perform( click() );
        Helpers.clickOnAView(R.id.drawer_layout, withId(R.id.recyclerview_cast), 1);
        Assert.assertTrue("The Katherine Heigl is not displayed.", Movie.isActorDisplayed(R.id.birthday_title));
    }

    @Test
    public void testAddNewTvShowToBookmarkedTvShows() throws Exception {
        NavDrawer.navDrawerCategories(R.id.nav_view, R.id.design_navigation_view, 11);
        onView(mediaItemPosition(withId(R.id.gridview_movie), 0)).perform( click() );
        Movie.bookmarkItem();
        pressBack();
        NavDrawer.navDrawerCategories(R.id.nav_view, R.id.design_navigation_view, 13);
        onView(mediaItemPosition(withId(R.id.gridview_movie), 0)).perform( click() );
        Assert.assertTrue("The movie is not bookmarked.", Movie.isMovieDisplayed(R.id.backdropImg));
    }
}
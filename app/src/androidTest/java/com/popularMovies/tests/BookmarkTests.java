package com.popularMovies.tests;

import com.popularMovies.screens.BookmarkMovie;
import com.popularMovies.screens.Movie;
import com.popularMovies.screens.NavDrawer;

import org.junit.Assert;
import org.junit.Test;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
        NavDrawer.clickAMediaItemFromTheList(R.id.gridview_movie, 0);
        Movie.bookmarkItem();
        pressBack();
        NavDrawer.bookmarkedMovies();
        Assert.assertTrue("The movie title is not displayed.", Movie.isTitleDisplayed(R.id.orgTitle));
    }

    @Test
    public void testShareItem() throws Exception {
        NavDrawer.clickAMediaItemFromTheList(R.id.gridview_movie, 0);
        Movie.shareMovie();
        Assert.assertTrue("The movie title is not displayed.", Movie.isTitleDisplayed(R.id.orgTitle));
    }

    @Test
    public void testPlayItem() throws Exception {
        NavDrawer.clickAMediaItemFromTheList(R.id.gridview_movie, 0);
        Movie.playMovie();
        Assert.assertTrue("The NowPlayingMovies screen is not displayed.", Movie.isTitleDisplayed(R.id.orgTitle));
    }

    @Test
    public void testRedirectingToHomePage() throws Exception {
        NavDrawer.clickAMediaItemFromTheList(R.id.gridview_movie, 0);
        Movie.navigateToElement(R.id.homepage);
        Movie.navigateBackToApp();
        Assert.assertTrue("The NowPlayingMovies screen is not displayed.", Movie.isMovieDisplayed(R.id.homepage));
    }

    @Test
    public void testIfAnActorIsDisplayed() throws Exception {
        NavDrawer.clickAMediaItemFromTheList(R.id.gridview_movie, 0);
        Helpers.clickOnAView(R.id.drawer_layout, withId(R.id.recyclerview_cast), 1);
        Assert.assertTrue("The actor details are not displayed.", Movie.isActorDisplayed(R.id.birthday_title));
    }

    @Test
    public void testAddNewTvShowToBookmarkedTvShows() throws Exception {
        NavDrawer.navDrawerCategories(R.id.nav_view, R.id.design_navigation_view, 11);
        NavDrawer.clickAMediaItemFromTheList(R.id.gridview_movie, 0);
        Helpers.pullToRefresh(R.id.detail_swipe_refresh);
        Movie.bookmarkItem();
        pressBack();
        NavDrawer.navDrawerCategories(R.id.nav_view, R.id.design_navigation_view, 13);
        NavDrawer.clickAMediaItemFromTheList(R.id.gridview_movie, 0);
        Assert.assertTrue("The movie is not bookmarked.", Movie.isMovieDisplayed(R.id.backdropImg));
    }

    @Test
    public void testIfSimiliarMoviesAreDisplayed() throws Exception{
        NavDrawer.clickAMediaItemFromTheList(R.id.gridview_movie, 1);
        Movie.clickNavigateToElementFromRecyclerView(R.id.recyclerview_cast, 0);
        Assert.assertTrue("The similiar movie is not displayed.", Movie.isMovieDisplayed(R.id.backdropImg));
    }

    @Test
    public void testIfSwipeActorsWork() throws Exception {
        NavDrawer.clickAMediaItemFromTheList(R.id.gridview_movie, 0);
        //Helpers.clickOnAView(R.id.drawer_layout, withId(R.id.recyclerview_cast), 1);
        Movie.navigateToElement(R.id.recyclerview_cast);
        Movie.navigateHorizontally(R.id.recyclerview_cast,2);
        Assert.assertTrue("The actor details are not displayed.", Movie.isActorDisplayed(R.id.birthday_title));
    }
}
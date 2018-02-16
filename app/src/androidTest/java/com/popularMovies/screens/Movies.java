package com.popularMovies.screens;

import android.support.test.espresso.contrib.RecyclerViewActions;

import com.popularMovies.tests.Helpers;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.popularMovies.constants.Strings.NAV_DRAWER;
import static com.popularMovies.constants.Strings.NOW_PLAYING_MOVIES;
import static com.popularMovies.constants.Strings.POPULAR_MOVIES;
import static com.popularMovies.constants.Strings.TOP_RATED_MOVIES;
import static com.popularMovies.constants.Strings.UPCOMING_MOVIES;

/**
 * Created by ioana.hoaghia on 2/14/2018.
 */

public class Movies {

    //Navigation to 'Get Now playing' Screen
    public static void navigationToGetNowPlaying() throws Exception {
        // Tap the Navigation Drawer
        onView(withContentDescription(NAV_DRAWER)).perform(click());
        // Tap 'Get Now playing'
        onView(withId(R.id.design_navigation_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        // Assert the navigation to the 'Get Now Playing' screen
        onView(Helpers.childAtPosition(withId(R.id.toolbar),1)).check(matches(withText(NOW_PLAYING_MOVIES)));
    }

    //Navigation to 'Get Popular' Screen
    public static void navigationToGetPopular() throws Exception {
        // Tap the Navigation Drawer
        onView(withContentDescription(NAV_DRAWER)).perform(click());
        // Tap 'Get Popular'
        onView(withId(R.id.design_navigation_view)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        // Assert the navigation to the 'Get popular' screen
        onView(Helpers.childAtPosition(withId(R.id.toolbar),1)).check(matches(withText(POPULAR_MOVIES)));
    }

    //Navigation to 'Get Top Rated' Screen
    public static void navigationToGetTopRated() throws Exception {
        // Tap the Navigation Drawer
        onView(withContentDescription(NAV_DRAWER)).perform(click());
        // Tap 'Get Top Rated'
        onView(withId(R.id.design_navigation_view)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        // Assert the navigation to the 'Get popular' screen
        onView(Helpers.childAtPosition(withId(R.id.toolbar),1)).check(matches(withText(TOP_RATED_MOVIES)));
    }

    //Navigation to 'Get Upcoming' Screen
    public static void navigationToGetUpcoming() throws Exception {
        // Tap the Navigation Drawer
        onView(withContentDescription(NAV_DRAWER)).perform(click());
        // Tap 'Get Upcoming'
        onView(withId(R.id.design_navigation_view)).perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        // Assert the navigation to the 'Get Upcoming' screen
        onView(Helpers.childAtPosition(withId(R.id.toolbar),1)).check(matches(withText(UPCOMING_MOVIES)));
    }
}
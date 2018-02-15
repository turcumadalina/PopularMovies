package com.popularMovies.screens;

import com.popularMovies.tests.Helpers;

import work.technie.popularmovies.R;

import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by valentin.boca on 2/15/2018.
 */

public class BookmarkMovie {
    public static boolean isNoBookmarksAdded() throws Exception {
        return Helpers.checkIfUIObjectIsVisible(allOf(withText(R.string.bookmarks_empty), isCompletelyDisplayed()), 3);
    }
}
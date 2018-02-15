package com.popularMovies.screens;

import work.technie.popularmovies.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by valentin.boca on 2/15/2018.
 */

public class BookmarkMovie {
    public static boolean isNoBookmarksAdded() throws Exception{
        try{
            onView( allOf( withText(R.string.bookmarks_empty), isCompletelyDisplayed())).check(matches(isDisplayed()));
        } catch(Exception e){
            return true;
        }
        return false;
    }
}
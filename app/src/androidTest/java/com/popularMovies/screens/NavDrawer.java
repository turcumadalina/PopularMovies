package com.popularMovies.screens;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by valentin.boca on 2/14/2018.
 */

public class NavDrawer {
    public static void bookmarkedMovies() throws Exception {
        onView(withContentDescription("Open navigation drawer")).perform(click());
        onView(withText("Bookmarked Movies")).perform(click());
    }
}

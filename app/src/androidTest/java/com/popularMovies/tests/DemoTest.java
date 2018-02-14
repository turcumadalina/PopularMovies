package com.popularMovies.tests;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;

public class DemoTest extends EspressoTestBase {

    @Test
    public void testDemo() throws Exception {
        onView(withContentDescription("Open navigation drawer")).perform(click());
    }
}

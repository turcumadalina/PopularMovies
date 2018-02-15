package com.popularMovies.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.Rule;
import org.junit.runner.RunWith;
import work.technie.popularmovies.activity.BaseActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTestBase {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule = new ActivityTestRule<>(BaseActivity.class);

    public static UiDevice device = UiDevice.getInstance(getInstrumentation());
}

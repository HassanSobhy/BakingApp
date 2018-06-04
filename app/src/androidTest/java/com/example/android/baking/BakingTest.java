package com.example.android.baking;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by hassa on 4/15/2018.
 */
@RunWith(AndroidJUnit4.class)
public class BakingTest {
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;
    private IdlingRegistry idlingRegistry = IdlingRegistry.getInstance();

    @Before
    public void setUp() throws Exception {

        idlingResource = mMainActivityTestRule.getActivity().getIdlingResource();
        idlingRegistry.register(idlingResource);

    }

    @Test
    public void clickRecyclerViewItem_OpensDetailsActivity() throws Exception {
        IdlingPolicies.setMasterPolicyTimeout(6, TimeUnit.MINUTES);
        IdlingPolicies.setIdlingResourceTimeout(6, TimeUnit.MINUTES);

        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        if (idlingResource != null) {
            idlingRegistry.unregister(idlingResource);
        }
    }


}

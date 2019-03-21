package com.yanwenli.prd_2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.ar.sceneform.ux.ArFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device
 */
@RunWith(AndroidJUnit4.class)
public class AugmentedImageActivityTest {

    private Context appContext;
    private Context arContext;

    private ArFragment arFragment;

    @Rule
    public ActivityTestRule<AugmentedImageActivity> mActivityRule = new ActivityTestRule<>(AugmentedImageActivity.class);

    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        arContext = InstrumentationRegistry.getContext();
        arFragment = new ArFragment();

    }

    /**
     * Test for moreInfo Button action
     */
    @Test
    public void moreInfoButtonClickActionTest() {

        onView(withId(R.id.btn_more_info)).perform(click());

    }


    /**
     * Test for preparing necessary components
     */
    @Test
    public void prepareTest() {

        onView(withId(R.id.ux_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.image_view_fit_to_scan2)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_more_info)).check(matches(is(isDisplayed())));
        onView(withId(R.id.image_intro_layout2)).check(matches(not(isDisplayed())));

    }

    /**
     * Test of creating the arScene
     */
    @Test
    public void getArSceneTest() {
        mActivityRule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.ux_fragment, arFragment).commit();
//        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);

    }

}
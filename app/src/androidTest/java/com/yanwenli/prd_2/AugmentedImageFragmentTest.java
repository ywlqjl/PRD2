package com.yanwenli.prd_2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device
 */
@RunWith(AndroidJUnit4.class)
public class AugmentedImageFragmentTest {

    private Context appContext;
    private AugmentedImageFragment augmentedImageFragment;

    @Rule
    public ActivityTestRule<AugmentedImageActivity> mActivityRule = new ActivityTestRule<>(AugmentedImageActivity.class);

    @Test
    public void onAttachTest() throws InterruptedException {

        appContext = InstrumentationRegistry.getTargetContext();

//        augmentedImageFragment.onAttach(appContext);

//        Thread.sleep(1000);
//        onView(withText(R.string.toast_sceneform_opengl_version))
//                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
//
////        Thread.sleep(1000);
//        onView(withText(R.string.toast_build_version))
//                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));


    }

    @Test
    public void onCreateView() {
    }

    @Test
    public void getSessionConfiguration() {
    }
}
package com.yanwenli.prd_2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {


    private MainActivity mainActivity;
    private MenuItem selectedItem;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        toolbar = mainActivity.findViewById(R.id.toolbar);
    }

    @Test
    public void onNavigationItemSelected() {


        toolbar.getCurrentContentInsetStart();
        drawerLayout = toolbar.findViewById(R.id.drawer_layout);
        navigationView = mainActivity.findViewById(R.id.nav_view);
        selectedItem = navigationView.getMenu().getItem(0);

        assertTrue("onNavigationItemSelected Passed!", mainActivity.onNavigationItemSelected(selectedItem));

        ShadowActivity shadowActivity = Shadows.shadowOf(mainActivity);
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        assertEquals(nextIntent.getComponent().getClassName(), AugmentedImageActivity.class.getName());

    }
}
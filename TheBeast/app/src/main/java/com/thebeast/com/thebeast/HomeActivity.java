package com.thebeast.com.thebeast;

import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    static final String FRAGMENT_CLASS = "fragmentClass";

    public enum NavigationScreen {
        FIRST,
        SECOND,
        THIRD
    }
    public NavigationScreen currentNavScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nvView);
        setUpNavigationView(navigationView);
        navigationView.setCheckedItem(R.id.nav_first_fragment);

        if (savedInstanceState == null) {
            currentNavScreen = NavigationScreen.FIRST;
        }
        else {
            currentNavScreen = (NavigationScreen)savedInstanceState.get(FRAGMENT_CLASS);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (currentNavScreen) {
            case FIRST:
                fragmentTransaction.add(R.id.content_frame, new TabFragment());
                break;
            case SECOND:
                fragmentTransaction.add(R.id.content_frame, new TestFragment2());
                break;
            case THIRD:
                fragmentTransaction.add(R.id.content_frame, new TestFragment3());
                break;
            default:
                fragmentTransaction.add(R.id.content_frame, new TabFragment());
        }

        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void setUpNavigationView(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectItem(item);
                return true;
            }
        });
    }

    public void selectItem(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = TabFragment.class;
                currentNavScreen = NavigationScreen.FIRST;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = TestFragment2.class;
                currentNavScreen = NavigationScreen.SECOND;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = TestFragment3.class;
                currentNavScreen = NavigationScreen.THIRD;
                break;
            default:
                fragmentClass = TabFragment.class;
                currentNavScreen = NavigationScreen.FIRST;
        }

        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();

        item.setChecked(true);
        mDrawerLayout.closeDrawers();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(FRAGMENT_CLASS, currentNavScreen);
        super.onSaveInstanceState(outState);
    }
}

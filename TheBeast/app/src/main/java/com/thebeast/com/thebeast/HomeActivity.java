package com.thebeast.com.thebeast;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (savedInstanceState == null) {
            currentNavScreen = NavigationScreen.FIRST;
            fragmentTransaction.add(R.id.content_frame, new TabFragment()).addToBackStack(null);
        }
        else {
            currentNavScreen = (NavigationScreen)savedInstanceState.get(FRAGMENT_CLASS);
            assert currentNavScreen != null;
            switch (currentNavScreen) {
                case FIRST:
                    fragmentTransaction.replace(R.id.content_frame, new TabFragment());
                    break;
                case SECOND:
                    fragmentTransaction.replace(R.id.content_frame, new TabFragment());
                    break;
                case THIRD:
                    fragmentTransaction.replace(R.id.content_frame, new TestFragment3());
                    break;
                default:
                    fragmentTransaction.replace(R.id.content_frame, new TabFragment());
            }
        }
        fragmentTransaction.commit();


        final Intent intent = new Intent(this, CreateGameActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
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
                fragmentClass = TabFragment.class;
                startActivity(new Intent(this, LoginToFacebook.class));
                break;
            case R.id.nav_third_fragment:
                fragmentClass = TabFragment.class;
                startActivity(new Intent(this, MapsActivity.class));
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

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TabFragment())
                    .commit();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached HomeActivity.onPause();");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached HomeActivity.onResume();");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached HomeActivity.onStop();");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached HomeActivity.onStart();");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached HomeActivity.onDestroy();");
    }
}

package com.thebeast.com.thebeast;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends Fragment {

    private CoordinatorLayout mCoordinatorLayout;
    private TextView tvUsername;
    private TextView totalGamesPlayed;
    private TextView overallRecord;
    //private TextView tvPointsScored;
    private ProfilePictureView profilePic;
    private DotIndicator dotIndicator;
    private ViewPager mViewPager;
    private PageAdapter mPageAdapter;
    private final int STAT_PAGE_NUM = 4;
    private Firebase mRef;

    @Override
    public void onStart() {
        super.onStart();

        if (!Utility.isNetworkAvailable(getContext())){
            Snackbar snackbar = Snackbar.make(mCoordinatorLayout,
                    "No connection",
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.activity_user_profile_layout, container, false);

        mCoordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.coordinator);

        tvUsername = (TextView) view.findViewById(R.id.username);
        totalGamesPlayed = (TextView) view.findViewById(R.id.total_games_played);
        overallRecord = (TextView) view.findViewById(R.id.overall_record);
        //tvPointsScored = (TextView) view.findViewById(R.id.tv_player_points_scored);
        profilePic = (ProfilePictureView)view.findViewById(R.id.user_profile_pic);
        dotIndicator = (DotIndicator)view.findViewById(R.id.dot_indicator);

        SharedPreferences prefs = getActivity().getSharedPreferences(Utility.prefsFile, Context.MODE_PRIVATE);
        final String userName = prefs.getString("userName", null);
        final String userId = prefs.getString("userId", null);

        profilePic.setProfileId(userId);
        tvUsername.setText(userName);

        mViewPager = (ViewPager)view.findViewById(R.id.sport_specific_stats);
        setupViewPager();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotIndicator.setSelectedItem(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/profiles");
        Query queryRef = mRef.orderByChild("name").equalTo(userName);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Profile dbProfile = postSnapshot.getValue(Profile.class);

                    int wins = dbProfile.getWins();
                    int losses = dbProfile.getLosses();
                    int totalGames = wins+losses;
                    String recordText = wins + "-" + losses;

                    totalGamesPlayed.setText(String.valueOf(totalGames));
                    overallRecord.setText(recordText);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("SHTUFF", "The read failed: " + firebaseError.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupViewPager() {
        mPageAdapter = new PageAdapter(getChildFragmentManager());

        Fragment basketballFragment = SportStatFragment.newInstance(Utility.SportFilter.BASKETBALL);
        mPageAdapter.addFragment(basketballFragment);

        Fragment footballFragment = SportStatFragment.newInstance(Utility.SportFilter.FOOTBALL);
        mPageAdapter.addFragment(footballFragment);

        Fragment soccerFragment = SportStatFragment.newInstance(Utility.SportFilter.SOCCER);
        mPageAdapter.addFragment(soccerFragment);

        Fragment volleyballFragment = SportStatFragment.newInstance(Utility.SportFilter.VOLLEYBALL);
        mPageAdapter.addFragment(volleyballFragment);

        mViewPager.setAdapter(mPageAdapter);
    }

    private class PageAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return STAT_PAGE_NUM;
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }
}

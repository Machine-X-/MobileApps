package com.thebeast.com.thebeast;


import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends Fragment {

    private TextView tvUsername;
    private TextView tvWins;
    private TextView tvLosses;
    private TextView tvPointsScored;
    private ProfilePictureView profilePic;
    private ViewPager mViewPager;
    private PageAdapter mPageAdapter;
    private final int STAT_PAGE_NUM = 4;
    private Firebase mRef;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.activity_user_profile_layout, container, false);

        tvUsername = (TextView) view.findViewById(R.id.username);
        tvWins = (TextView) view.findViewById(R.id.tv_player_wins);
        tvLosses = (TextView) view.findViewById(R.id.tv_player_losses);
        tvPointsScored = (TextView) view.findViewById(R.id.tv_player_points_scored);
        profilePic = (ProfilePictureView)view.findViewById(R.id.user_profile_pic);

        SharedPreferences prefs = getActivity().getSharedPreferences(Utility.prefsFile, Context.MODE_PRIVATE);
        final String userName = prefs.getString("userName", null);
        final String userId = prefs.getString("userId", null);

        profilePic.setProfileId(userId);
        tvUsername.setText(userName);

        mViewPager = (ViewPager)view.findViewById(R.id.sport_specific_stats);
        setupViewPager();

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/profiles");
        Query queryRef = mRef.orderByChild("name").equalTo(userName);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    com.thebeast.com.thebeast.Profile dbProfile = postSnapshot.getValue(com.thebeast.com.thebeast.Profile.class);
                    tvWins.append(String.valueOf(dbProfile.getWins()));
                    tvLosses.append(String.valueOf(dbProfile.getLosses()));
                    tvPointsScored.append(String.valueOf(dbProfile.getPointsScored()));
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

package com.thebeast.com.thebeast;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {

    private CoordinatorLayout mCoordinatorLayout;
    public TabLayout mTabLayout;
    public ViewPager mViewPager;
    private PageAdapter mPageAdapter;
    private FloatingActionButton fab;
    static final int TAB_NUM = 5;
    static final String[] TAB_NAMES = {"ALL SPORTS", "BASKETBALL", "FOOTBALL", "SOCCER", "VOLLEYBALL"};

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout, container, false);

        mCoordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.coordinator);

        mViewPager = (ViewPager)view.findViewById(R.id.viewPager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        final Intent intent = new Intent(getActivity(), CreateGameActivity.class);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        mPageAdapter = new PageAdapter(getChildFragmentManager());

        Fragment allFragment = GameListFragment.newInstance(Utility.SportFilter.ALL);
        mPageAdapter.addFragment(allFragment);

        Fragment basketballFragment = GameListFragment.newInstance(Utility.SportFilter.BASKETBALL);
        mPageAdapter.addFragment(basketballFragment);

        Fragment footballFragment = GameListFragment.newInstance(Utility.SportFilter.FOOTBALL);
        mPageAdapter.addFragment(footballFragment);

        Fragment soccerFragment = GameListFragment.newInstance(Utility.SportFilter.SOCCER);
        mPageAdapter.addFragment(soccerFragment);

        Fragment volleyballFragment = GameListFragment.newInstance(Utility.SportFilter.VOLLEYBALL);
        mPageAdapter.addFragment(volleyballFragment);

        viewPager.setAdapter(mPageAdapter);
    }

    public class PageAdapter extends FragmentPagerAdapter {

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
            return TAB_NUM;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_NAMES[position];
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }
}

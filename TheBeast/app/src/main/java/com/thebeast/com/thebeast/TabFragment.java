package com.thebeast.com.thebeast;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {

    public TabLayout mTabLayout;
    public ViewPager mViewPager;
    static final int TAB_NUM = 5;
    static final String[] TAB_NAMES = {"ALL SPORTS", "BASKETBALL", "FOOTBALL", "SOCCER", "VOLLEYBALL"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout, container, false);

        mViewPager = (ViewPager)view.findViewById(R.id.viewPager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        PageAdapter adapter = new PageAdapter(getChildFragmentManager());

        Fragment allFragment = GameListFragment.newInstance(Utility.ListFilter.ALL);
        adapter.addFragment(allFragment);

        Fragment basketballFragment = GameListFragment.newInstance(Utility.ListFilter.BASKETBALL);
        adapter.addFragment(basketballFragment);

        Fragment footballFragment = GameListFragment.newInstance(Utility.ListFilter.FOOTBALL);
        adapter.addFragment(footballFragment);

        Fragment soccerFragment = GameListFragment.newInstance(Utility.ListFilter.SOCCER);
        adapter.addFragment(soccerFragment);

        Fragment volleyballFragment = GameListFragment.newInstance(Utility.ListFilter.VOLLEYBALL);
        adapter.addFragment(volleyballFragment);

        viewPager.setAdapter(adapter);
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

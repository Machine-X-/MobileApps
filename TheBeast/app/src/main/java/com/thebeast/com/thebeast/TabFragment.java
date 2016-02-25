package com.thebeast.com.thebeast;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment extends Fragment {

    public TabLayout mTabLayout;
    public ViewPager mViewPager;
    static final int TAB_NUM = 3;
    static final String[] TAB_NAMES = {"TAB 1", "TAB 2", "TAB 3"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout, container, false);

        mViewPager = (ViewPager)view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new PageAdapter(getChildFragmentManager()));

        mTabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    public static class PageAdapter extends FragmentPagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TestFragment();
                case 1:
                    return new TestFragment2();
                case 2:
                    return new TestFragment3();
            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_NUM;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_NAMES[position];
        }
    }
}

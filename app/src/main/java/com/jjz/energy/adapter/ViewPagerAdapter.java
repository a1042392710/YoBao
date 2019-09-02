package com.jjz.energy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jjz.energy.ui.community.CommunityFragment;
import com.jjz.energy.ui.home.HomeFragment;
import com.jjz.energy.ui.mine.MineFragment;
import com.jjz.energy.ui.notice.NoticeFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
        private Fragment[] mFragments = new Fragment[]{new HomeFragment(), new CommunityFragment(),
                    new NoticeFragment(), new MineFragment()};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) { return mFragments[position];}

        @Override
        public int getCount() { return mFragments.length;}
    }
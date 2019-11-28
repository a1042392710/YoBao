package com.jjz.energy.adapter.jiusu;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhao 2018/9/25
 * @function 我的订单
 */
public class JiuSuMineOrderAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments ;
    private String[] titles;
    private FragmentManager fm;

    public JiuSuMineOrderAdapter(FragmentManager fm, ArrayList<Fragment> mFragments, String[] titles ){
        super(fm);
       this. mFragments = mFragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if (i<mFragments.size()){
            fragment = mFragments.get(i);
        }else {
            fragment = mFragments.get(0);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (titles!=null&&titles.length>0){
            return  titles[position];
        }
        return null;
    }

    public void notifyChangeData(ArrayList<Fragment> mFragments, String[] titles ) {
        this.mFragments.clear();
        this.mFragments.addAll(mFragments);
        this.titles=titles;
        super.notifyDataSetChanged();
    }
}

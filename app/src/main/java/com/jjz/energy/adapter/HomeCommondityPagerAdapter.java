package com.jjz.energy.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jjz.energy.entry.commodity.GoodsClassificationBean;
import com.jjz.energy.ui.home.commodity.HomeCommodityFragment;

import java.util.List;

/**
 * @Features: 商品分类viewpager 适配器
 * @author: create by chenhao on 2019/10/11
 */
public class HomeCommondityPagerAdapter extends FragmentStatePagerAdapter {

    List<GoodsClassificationBean> list;

    public HomeCommondityPagerAdapter(FragmentManager fm ,List<GoodsClassificationBean>list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        //创建fragment对象并返回
        Bundle bundle = new Bundle();
        bundle.putInt(HomeCommodityFragment.GOODS_TYPE, list.get(i).getId());
        //实例化Fragment
        HomeCommodityFragment fragment = new HomeCommodityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getMobile_name();
    }

    @Override
    public int getCount() {
        return list.size();
    }



}

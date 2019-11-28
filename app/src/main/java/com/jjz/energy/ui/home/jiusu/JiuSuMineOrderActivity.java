package com.jjz.energy.ui.home.jiusu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.jiusu.JiuSuMineOrderAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我的订单
 * @author: create by chenhao on 2019/4/15
 */
public class JiuSuMineOrderActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tl_layout)
    TabLayout tlLayout;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;
    /**
     *  我的订单 fragment 集合
     */
    private ArrayList<Fragment> mFragments;
    /**
     * Tablayout 适配器
     */
    private JiuSuMineOrderAdapter mOrderAdapter;
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_order;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我的订单");
        //初始化数据
        mFragments = new ArrayList<>();
        //未完成
        Fragment fragment1 = new JiuSuMineOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status","0");
        fragment1.setArguments(bundle);
        //已完成
        Fragment fragment2 = new JiuSuMineOrderFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("status","1");
        fragment2.setArguments(bundle2);
        //添加数据
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mOrderAdapter = new JiuSuMineOrderAdapter(getSupportFragmentManager(), mFragments, new String[]{"未完成", "已完成"});
        vpOrder.setAdapter(mOrderAdapter);
        //tablayout 绑定viewpager
        tlLayout.setupWithViewPager(vpOrder);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }


    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}

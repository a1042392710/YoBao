package com.jjz.energy.ui.mine;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我的粉丝和我的关注
 * @author: create by chenhao on 2019/8/14
 */
public class MineFansAndLikeActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp_fans)
    ViewPager vpFans;
    /**
     * 0 默认选中关注的人  1 默认我的粉丝
     */
    private int vpSelectIndex ;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_fans;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("南昌彭于晏");
        vpSelectIndex  = getIntent().getIntExtra("index",0);
        initVpAndVp();
    }


    /**
     * 初始化tblayout列表和vp
     */
    private void initVpAndVp() {
        //初始化ViewPager 并与TabLayout绑定
        vpFans.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(vpFans);
        //设置选中
        vpFans.setCurrentItem(vpSelectIndex);
    }



    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }



    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private String[] title = {"关注的人", "我的粉丝"};

        private Fragment[] mFragments = new Fragment[]{new MineLikeFragment(), new MineFansFragment()};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) { return mFragments[position];}

        @Override
        public int getCount() { return mFragments.length;}

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}

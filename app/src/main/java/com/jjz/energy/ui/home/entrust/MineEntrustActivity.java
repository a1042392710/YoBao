package com.jjz.energy.ui.home.entrust;

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
 * @Features: 我接受的委托和我发布的委托
 * @author: create by chenhao on 2019/8/14
 */
public class MineEntrustActivity extends BaseActivity {

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



    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_entrust;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我的委托");
        initVpAndVp();
    }


    /**
     * 初始化tblayout列表和vp
     */
    private void initVpAndVp() {
        //初始化ViewPager 并与TabLayout绑定
        vpFans.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(vpFans);
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
        private String[] title = {"我接受的", "我发布的"};

        private Fragment[] mFragments = new Fragment[]{new AccpetEntrustFragment(), new PutEntrustFragment()};

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

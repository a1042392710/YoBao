package com.jjz.energy.ui;

import android.content.pm.ActivityInfo;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.MainEvent;
import com.jjz.energy.ui.home.HomeFragment;
import com.jjz.energy.util.NoScrollViewPager;
import com.jude.swipbackhelper.SwipeBackHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * @Features: 主页面
 * @author: create by chenhao on 2019/3/21
 */
public class MainActivity extends BaseActivity  {
    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;
    @BindView(R.id.bottom_toolbar)
    BottomNavigationView bottomNavigationView;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_main;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);//设置是否可滑动
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId){
                case R.id.tab_one:
                    vpMain.setCurrentItem(0);
                    item.setChecked(true);
                    break;
                case R.id.tab_two:
                    vpMain.setCurrentItem(1);
                    item.setChecked(true);
                    break;
                case R.id.tab_three:
                    //未登录
//                    if (!UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
//                        startActivity(new Intent(mContext, PhoneLoginActivity.class));
//                    }else{
                        vpMain.setCurrentItem(2);
                        item.setChecked(true);
//                    }
                    break;
                case R.id.tab_four:
                    vpMain.setCurrentItem(3);
                    item.setChecked(true);
                    break;
                case R.id.tab_five:
                    vpMain.setCurrentItem(4);
                    item.setChecked(true);
                    break;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.tab_one);
        vpMain.setOffscreenPageLimit(3);
        vpMain.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCurrentItem(MainEvent event) {
        if (event.getEventMsg()== MainEvent.GO_MINE){
            bottomNavigationView.setSelectedItemId(R.id.tab_three);
        }else if (event.getEventMsg()== MainEvent.GO_HOT){
            bottomNavigationView.setSelectedItemId(R.id.tab_two);
        }else{
            bottomNavigationView.setSelectedItemId(R.id.tab_one);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private Fragment[] mFragments = new Fragment[]{new HomeFragment(), new HomeFragment(),
                new HomeFragment(), new HomeFragment(), new HomeFragment()};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position){ return mFragments[position];}

        @Override
        public int getCount() { return 3;}
    }

    @Override
    public void showLoading( ) {}
    @Override
    public void stopLoading() {}
}

package com.jjz.energy.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.MainEvent;
import com.jjz.energy.ui.home.HomeFragment;
import com.jjz.energy.ui.logistics.ReleaseLogisticsActivity;
import com.jjz.energy.ui.mine.MineFragment;
import com.jjz.energy.ui.notice.NoticeFragment;
import com.jjz.energy.util.NoScrollViewPager;
import com.jude.swipbackhelper.SwipeBackHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 主页面
 * @author: create by chenhao on 2019/3/21
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_cimmundity)
    RadioButton rbCimmundity;
    @BindView(R.id.img_home_go)
    ImageView imgHomeGo;
    @BindView(R.id.rb_notice)
    RadioButton rbNotice;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.rg_bottom)
    RadioGroup rgBottom;

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

        rgBottom.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId==R.id.rb_home){
                vpMain.setCurrentItem(0);
            }else if (checkedId==R.id.rb_cimmundity){
                vpMain.setCurrentItem(1);
            }else if (checkedId==R.id.rb_notice){
                vpMain.setCurrentItem(2);
            }else if (checkedId==R.id.rb_mine){
                vpMain.setCurrentItem(3);
            }
        });
//        vpMain.setOffscreenPageLimit(3);
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
//        if (event.getEventMsg() == MainEvent.GO_MINE) {
//        } else if (event.getEventMsg() == MainEvent.GO_HOT) {
//            bottomNavigationView.setSelectedItemId(R.id.tab_two);
//        } else {
//            bottomNavigationView.setSelectedItemId(R.id.tab_one);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }



    @OnClick(R.id.img_home_go)
    public void onViewClicked() {
        //发布物流
       startActivity(new Intent(mContext, ReleaseLogisticsActivity.class));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private Fragment[] mFragments = new Fragment[]{new HomeFragment(), new HomeFragment(),
                    new NoticeFragment(), new MineFragment()};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) { return mFragments[position];}

        @Override
        public int getCount() { return mFragments.length;}
    }

    @Override
    public void showLoading() {}

    @Override
    public void stopLoading() {}
}

package com.jjz.energy.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.MainEvent;
import com.jjz.energy.ui.community.CommunityFragment;
import com.jjz.energy.ui.community.PutCommunityActivity;
import com.jjz.energy.ui.home.HomeFragment;
import com.jjz.energy.ui.home.PutCommodityActivity;
import com.jjz.energy.ui.logistics.ReleaseLogisticsActivity;
import com.jjz.energy.ui.mine.MineFragment;
import com.jjz.energy.ui.notice.NoticeFragment;
import com.jjz.energy.util.NoScrollViewPager;
import com.jjz.energy.util.PopWindowUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
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

    /**
     * 选中的按钮下标
     */
    private int selectIndex = 0;

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
            if (checkedId == R.id.rb_home) {
                selectIndex = 0;
                vpMain.setCurrentItem(0);
            } else if (checkedId == R.id.rb_cimmundity) {
                selectIndex = 1;
//                if (UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
                    vpMain.setCurrentItem(1);
//                }else{
//                    startActivityForResult(new Intent(mContext, LoginActivity.class),0);
//                }
            } else if (checkedId == R.id.rb_notice) {
                selectIndex = 2;
//                if (UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
                vpMain.setCurrentItem(2);
//                }else{
//                    startActivityForResult(new Intent(mContext, LoginActivity.class),0);
//                }
            }else if (checkedId==R.id.rb_mine){
                selectIndex = 3;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //未登录状态，回到主页都切换到HomeFragment
        if (requestCode==0&&!UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
            selectIndex=0;
            vpMain.setCurrentItem(0);
            rbHome.setChecked(true);
        }
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
        View view = View.inflate(mContext,R.layout.item_put_commodity,null);
        ImageView item_img_close = view.findViewById(R.id.item_img_close);
        //取消弹窗
        RelativeLayout item_rl_cancle = view.findViewById(R.id.item_rl_cancle);
        //发布帖子
        LinearLayout item_ll_put_post = view.findViewById(R.id.item_ll_put_post);
        //发布物流
        LinearLayout item_ll_put_logistics = view.findViewById(R.id.item_ll_put_logistics);
        //发布宝贝
        LinearLayout item_ll_put_idle = view.findViewById(R.id.item_ll_put_idle);
        PopupWindow  popupWindow= PopWindowUtil.getInstance().showAllWindow(mContext,view);
        //关闭弹窗
        item_img_close.setOnClickListener(v -> popupWindow.dismiss());
        item_rl_cancle.setOnClickListener(v -> popupWindow.dismiss());
        //发布物流
        item_ll_put_logistics.setOnClickListener(v -> {
            popupWindow.dismiss();
            //发布物流
            startActivity(new Intent(mContext, ReleaseLogisticsActivity.class));
        });
        //发布宝贝
        item_ll_put_idle.setOnClickListener(v -> {
            popupWindow.dismiss();
            //发布宝贝
            startActivity(new Intent(mContext, PutCommodityActivity.class));
        });
        //发布帖子
        item_ll_put_post.setOnClickListener(v -> {
            popupWindow.dismiss();
            //发布宝贝
            startActivity(new Intent(mContext, PutCommunityActivity.class));
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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

    @Override
    public void showLoading() {}

    @Override
    public void stopLoading() {}
}

package com.jjz.energy.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jjz.energy.R;
import com.jjz.energy.adapter.ViewPagerAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.event.LocationEvent;
import com.jjz.energy.ui.community.PutCommunityActivity;
import com.jjz.energy.ui.home.PutCommodityActivity;
import com.jjz.energy.ui.home.login.LoginActivity;
import com.jjz.energy.ui.home.logistics.ReleaseLogisticsActivity;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.widgets.NoScrollViewPager;
import com.jude.swipbackhelper.SwipeBackHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features: 主页面
 * @author: create by chenhao on 2019/3/21
 */
@RuntimePermissions
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


    //定位
    public LocationClient mLocationClient = null;
    //定位监听
    public MyLocationListener mMyLocationListener = new MyLocationListener();
    //定位配置
    private LocationClientOption option = new LocationClientOption();

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
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);//设置是否可滑动
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initListener();
        initLocation();
        vpMain.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        vpMain.setOffscreenPageLimit(3);
        //开启消息监听事件
        JMessageClient.registerEventReceiver(this);
    }

    /**
     *  消息点击事件
     **/
    public void onEventMainThread(NotificationClickEvent event) {
        //进入消息列表
        rbNotice.setChecked(true);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
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
                if (UserLoginBiz.getInstance(mContext).detectUserLoginStatus()) {
                    vpMain.setCurrentItem(2);
                } else {
                    startActivityForResult(new Intent(mContext, LoginActivity.class), 0);
                }
            } else if (checkedId == R.id.rb_mine) {
                selectIndex = 3;
                if (UserLoginBiz.getInstance(mContext).detectUserLoginStatus()) {
                    vpMain.setCurrentItem(3);
                } else {
                    startActivityForResult(new Intent(mContext, LoginActivity.class), 0);
                }
            }
        });
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



    //=============================================================== 定位
    /**
     * 初始化定位设置
     */
    private void initLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(Objects.requireNonNull(mContext).getApplicationContext());
        //是否需要地址信息，默认为不需要
        option.setIsNeedAddress(true);
        //定位模式为高精度
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //只定位一次
        option.setScanSpan(0);
        //是否需要位置描述信息
        option.setIsNeedLocationDescribe(true);
        //将配置好的参数绑定
        mLocationClient.setLocOption(option);
        //注册监听函数
        mLocationClient.registerLocationListener(mMyLocationListener);
        MainActivityPermissionsDispatcher.startLocationWithCheck(this);
    }


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void startLocation() {
        mLocationClient.start();
    }

    //用户选择的不再询问后回调  非必须的方法
    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    public void multiNeverAsk() {

        View view = View.inflate(mContext, R.layout.item_pop_defult_view, null);
        TextView pop_tv_message = view.findViewById(R.id.pop_tv_message);
        TextView pop_tv_ok = view.findViewById(R.id.pop_tv_ok);
        pop_tv_message.setText("没有定位权限将无法定位当前城市哦，请您前往设置页面打开定位权限！");
        pop_tv_ok.setText("去设置");

        PopWindowUtil.getInstance().showPopupWindow(mContext, view);
        pop_tv_ok.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri1 = Uri.parse("package:" + getPackageName());
            intent.setData(uri1);
            startActivityForResult(intent, 20);
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    /**
     * 定位信息回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //定位成功
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
                String locationDescribe = location.getLocationDescribe();
                //发送一个包含市区信息的消息
                EventBus.getDefault().post(new LocationEvent(location.getCity()));
                //获取到地址后取消定位
                mLocationClient.unRegisterLocationListener(mMyLocationListener);
                mLocationClient.stop();
            }else{
                showToast("定位失败，请打开位置信息");
            }
        }
    }

    //================================================================= 生命周期和其他

    @Override
    protected void onResume() {
        super.onResume();
        //再次显示的时候重新定位
        if (null != mLocationClient && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消定位
        mLocationClient.unRegisterLocationListener(mMyLocationListener);
        mLocationClient.stop();
    }

    //禁止返回
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
            //记录选中的按钮
            selectIndex=0;
            vpMain.setCurrentItem(0);
            rbHome.setChecked(true);
        }
    }
    @Override
    public void showLoading() {}

    @Override
    public void stopLoading() {}
}

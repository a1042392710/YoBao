package com.jjz.energy.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.blankj.utilcode.util.ActivityUtils;
import com.jjz.energy.R;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.ui.MainActivity;
import com.jjz.energy.ui.home.login.LoginActivity;
import com.jjz.energy.util.networkUtil.AesUtils;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.LoadingDialogUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.util.system.StatusBarUtils;
import com.jjz.energy.view.OnLoadSirCallback;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by chenhao on 2018/8/28.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements
        IBaseView {

    /**
     * 通用加载dialog
     */
    private LoadingDialogUtil mLoadingDialogUtil;
    /**
     * Presenter
     */
    protected P mPresenter;
    /**
     * 上下文对象
     */
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸状态栏
        StatusBarUtils.setColorNoTranslucent(this, Color.TRANSPARENT);
        //右滑返回
        swipeBackInit();
        mContext = this;
        initEnterTransition();
        //布局绑定
        setContentView(layoutId());
        //注解绑定
        ButterKnife.bind(this);
        //获取presenter
        mPresenter = getPresenter();
        //初始化进度条
        mLoadingDialogUtil = new LoadingDialogUtil();
        //初始化数据
        initView();
    }

    protected  void initEnterTransition(){

    };

    /**
     * 初始化右滑手势返回
     */
    private void swipeBackInit() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.1f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(Color.WHITE)//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(false)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(500);//activity联动时的偏移量。默认500px。
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }


    //======================================== 实用方法

    /**
     * 没登录就前往登录页面
     *
     * @return
     */
    protected void loginStartActivity(Class<?> cls) {
        //未登录
        if (!UserLoginBiz.getInstance(mContext).detectUserLoginStatus()) {
            ActivityUtils.startActivity(LoginActivity.class);
        } else {
            Intent intent = new Intent();
            intent.setClass(mContext, cls);
            startActivity(intent);
        }
    }
    /**
     * 没登录就前往登录页面
     *
     * @return
     */
    protected void loginStartActivity(Class<?> cls,Bundle bundle) {
        //未登录
        if (!UserLoginBiz.getInstance(mContext).detectUserLoginStatus()) {
            ActivityUtils.startActivity(LoginActivity.class);
        } else {
            Intent intent = new Intent();
            intent.setClass(mContext, cls);
            if (bundle!=null){
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }
    /**
     * 登录成功，保存用户信息
     */
    protected void loginSuc(UserInfo data){
        showToast("登录成功");
        //解密token
        String decode_token = AesUtils.decrypt(data.getToken(), AesUtils.KEY, AesUtils.IV);
        //去除时间戳
        decode_token= decode_token.substring(0,decode_token.length()-data.getTime().length());
        //存储用户Toke
        SpUtil.init(BaseApplication.getAppContext()).commit(Constant.LOGIN_ID, decode_token);
        //存储用户信息
        UserLoginBiz.getInstance(BaseApplication.getAppContext()).loginSuccess(data);
        //恢复极光推送功能
        JPushInterface.resumePush(mContext);
        //登录极光IM
        JMessageClient.login(data.getMobile(), data.getJmessage_password(),new BasicCallback(){
            @Override
            public void gotResult(int i, String s) {
                String msg =  i ==0?"登录成功":"登录失败:"+s;
                LogUtil.e("久速","极光推送:"+msg);
            }
        });
        //跳转首页
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    /**
     * 收回软键盘
     */
    protected  void  disMissSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 提示
     */
    protected Toast mTasot;

    public void showToast(String msg) {
        if (mTasot == null) {
            mTasot = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        mTasot.setText(msg);
        mTasot.setDuration(Toast.LENGTH_SHORT);
        mTasot.show();
    }


    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        mLoadingDialogUtil.showDialogForLoading(this, true);
    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        mLoadingDialogUtil.cancelDialogForLoading();
    }

    /**
     * 半透明窗体，给Dialog实现背景变暗效果
     */
    public View mDialogView = null;

    /**
     * window背景是否变暗
     *
     * @param isDark
     */
    public void setDarkWindow(boolean isDark) {

        if (mDialogView == null) {
            mDialogView = new View(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mDialogView.setLayoutParams(layoutParams);
            mDialogView.setVisibility(View.VISIBLE);
            mDialogView.setBackgroundColor(getResources().getColor(R.color.black));
            mDialogView.setAlpha(0.6f);
            FrameLayout rootView = getWindow().getDecorView().findViewById(android.R.id.content);
            rootView.addView(mDialogView);
        }
        //添加一个半透明的view 通过显示隐藏的方式来产生背景变暗效果
        if (isDark) {
            mDialogView.setVisibility(View.VISIBLE);
        } else {
            mDialogView.setVisibility(View.GONE);
        }
    }

    /**
     * 关闭刷新
     */
    protected void closeRefresh(SmartRefreshLayout view) {
        view.finishRefresh(300);
        view.finishLoadMore(300);
    }

    /**
     * 加载页面
     * @param drawable 指定图片
     * @param msg    指定文字
     * @param callback  点击回调
     * @return
     */
    protected View getLoadSirView(int drawable, String msg, boolean isAgan,
                                  OnLoadSirCallback callback) {
        View defaultView = View.inflate(mContext, R.layout.loadsir_default_view, null);
        //加载图片
        ((ImageView) defaultView.findViewById(R.id.img_loadsir)).setImageResource(drawable);
        //加载内容
        ((TextView) defaultView.findViewById(R.id.tv_loadsir_msg)).setText(msg);
        //是否可重新获取数据
        defaultView.findViewById(R.id.tv_loadsir_agan).setVisibility(isAgan ? View.VISIBLE :
                View.GONE);
        if (callback != null) {
            defaultView.setOnClickListener(v -> callback.onClick(v));
        }
        return defaultView;
    }





    // ====================================== 生命周期和子类实现


    @Override
    protected void onResume() {
        /**
         * 强制竖屏
         */
        if (Build.VERSION.SDK_INT != 26) {
            // 设置横竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
        SwipeBackHelper.onDestroy(this);
        //关闭软键盘
        disMissSoftKeyboard();
        //修复键盘内存泄漏  暂时未出现
//        FixInputMethodMemory.fixFocusedViewLeak(this, null);
        //取消 view 的绑定
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        //关闭页面时关闭弹窗
        mLoadingDialogUtil.cancelDialogForLoading();
    }

    /**
     * 退出app的时间
     */
    private long mExitTime;
    //退出app
    protected void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            showToast(getString(R.string.exit_str));
            mExitTime = System.currentTimeMillis();
        } else {
            //用户退出处理
            finish();
            System.exit(0);
        }
    }


    //获得presenter
    protected abstract P getPresenter();

    protected abstract int layoutId();

    protected abstract void initView();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

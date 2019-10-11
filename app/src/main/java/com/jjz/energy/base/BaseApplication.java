package com.jjz.energy.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.jjz.energy.R;
import com.jjz.energy.ui.notice.IMActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;


public class BaseApplication extends Application {
    public static Context AppContext;


    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        initBaiduSDK();
        initJPushJMessage();
        setupLeakCanary();
    }



    /**
     * 百度SDK  临时放一下bugly
     */
    private void initBaiduSDK() {
        //bugly 参数3  调试开关 /测试时true 发布时false
        Bugly.init(getApplicationContext(), Constant.BUGLY_ID, true);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * 极光推送和极光Im
     */
    private void initJPushJMessage() {
        //极光推送
        JPushInterface.setDebugMode(true);// true则会打印debug级别的日志，false则只会打印warning级别以上的日志
        JPushInterface.init(this);
        //极光IM  指定是否开启消息漫游 默认不开启
        JMessageClient.init(this, true);
        //开启全局监听事件
        JMessageClient.registerEventReceiver(this);
    }

    /**
     * 内存泄漏检测工具
     */
    protected void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    /**
     *  全局聊天消息点击事件
     **/
    public void onEvent(NotificationClickEvent event) {
        String userName = event.getMessage().getFromUser().getUserName();
        //点击后直接跳转聊天页面
        Intent intent = new Intent(this,IMActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userName",userName);
        startActivity(intent);
    }


    //static 代码段可以防止内存泄露
    static {
        //SmartRefreshLayout 刷新教程
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, R.color.red_fe8977);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context);
        });
    }

    public static Context getAppContext() {
        return AppContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}

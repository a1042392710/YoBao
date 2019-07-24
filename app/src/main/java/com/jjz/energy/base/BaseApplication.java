package com.jjz.energy.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.jjz.energy.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;


public class BaseApplication extends Application {
    public static Context AppContext;
    private final static String TAG = "BaseApplication";


    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        //bugly 参数3  调试开关 /测试时true 发布时false
        Bugly.init(getApplicationContext(), "549524cc16", true);

        //内存泄漏工具
        if (LeakCanary.isInAnalyzerProcess(this)) {//1
            return;
        }
        LeakCanary.install(this);
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

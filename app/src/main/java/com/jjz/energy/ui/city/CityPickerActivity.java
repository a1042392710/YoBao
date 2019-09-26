package com.jjz.energy.ui.city;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.ToastUtils;
import com.jjz.energy.R;
import com.jjz.energy.entry.event.LocationEvent;
import com.jjz.energy.ui.city.adapter.CityListAdapter;
import com.jjz.energy.ui.city.bean.AreasBean;
import com.jjz.energy.ui.city.bean.City;
import com.jjz.energy.ui.city.bean.CityPickerBean;
import com.jjz.energy.ui.city.bean.LocateState;
import com.jjz.energy.ui.city.util.PinyinUtils;
import com.jjz.energy.ui.city.util.ReadAssetsFileUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.city.widget.SideLetterBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class CityPickerActivity extends FragmentActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.listview_all_city)
    ListView listviewAllCity;
    @BindView(R.id.tv_letter_overlay)
    TextView tvLetterOverlay;
    @BindView(R.id.side_letter_bar)
    SideLetterBar sideLetterBar;

    private CityListAdapter mCityAdapter;

    //定位
    public LocationClient mLocationClient = null;
    //定位监听
    public MyLocationListener mMyLocationListener = new MyLocationListener();
    //定位配置
    private LocationClientOption option = new LocationClientOption();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_activity_city_list);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("选择城市");
        initView();
        initData();
        initLocation();
    }
    //初始化控件
    protected void initView() {
        //设置悬浮的Text
        sideLetterBar.setOverlay(tvLetterOverlay);
        //改变悬浮的文字
        sideLetterBar.setOnLetterChangedListener(letter -> {
            int position = mCityAdapter.getLetterPosition(letter);
            listviewAllCity.setSelection(position);
        });
        mCityAdapter = new CityListAdapter(this);
        listviewAllCity.setAdapter(mCityAdapter);
    }


    /**
     * 初始化
     */
    protected void initData() {
        getCityData();
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                //选择城市
                EventBus.getDefault().post(new LocationEvent(name));
                finish();
            }

            @Override
            public void onLocateClick() {//点击定位按钮
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                initLocation();//重新定位
            }
        });
    }

    /**
     * 获取数据并整理数据
     */
    public void getCityData() {
        String json = ReadAssetsFileUtil.getJson(this, "city.json");
        CityPickerBean bean = JSON.parseObject(json, CityPickerBean.class);

        HashSet<City> citys = new HashSet<>();
        for (AreasBean areasBean : bean.data.areas) {
            String name = areasBean.name.replace("　", "");
            citys.add(new City(areasBean.id, name, PinyinUtils.getPinYin(name),
                    areasBean.is_hot == 1));
            for (AreasBean.ChildrenBeanX childrenBeanX : areasBean.children) {
                citys.add(new City(childrenBeanX.id, childrenBeanX.name,
                        PinyinUtils.getPinYin(childrenBeanX.name), childrenBeanX.is_hot == 1));
            }
        }
        //set转换list
        ArrayList<City> cities = new ArrayList<>(citys);
        //按照字母排序
        Collections.sort(cities, (city, t1) -> city.getPinyin().compareTo(t1.getPinyin()));
        mCityAdapter.setData(cities);
    }

    //=============================================================== 定位

    /**
     * 初始化定位设置
     */
    private void initLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(Objects.requireNonNull(this).getApplicationContext());
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
        CityPickerActivityPermissionsDispatcher.startLocationWithCheck(this);
    }


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void startLocation() {
        mLocationClient.start();
    }

    //用户选择的不再询问后回调  非必须的方法
    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    public void multiNeverAsk() {

        View view = View.inflate(this, R.layout.item_pop_defult_view, null);
        TextView pop_tv_message = view.findViewById(R.id.pop_tv_message);
        TextView pop_tv_ok = view.findViewById(R.id.pop_tv_ok);
        pop_tv_message.setText("没有定位权限将无法定位当前城市哦，请您前往设置页面打开定位权限！");
        pop_tv_ok.setText("去设置");

        PopWindowUtil.getInstance().showPopupWindow(this, view);
        pop_tv_ok.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri1 = Uri.parse("package:" + getPackageName());
            intent.setData(uri1);
            startActivityForResult(intent, 20);
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CityPickerActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
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

                if (location.getCity() != null && !location.getCity().equals("")) {
                    mCityAdapter.updateLocateState(LocateState.SUCCESS,
                            location.getCity().replace("市", ""));
                } else {
                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
                }
                //获取到地址后取消定位
                mLocationClient.unRegisterLocationListener(mMyLocationListener);
                mLocationClient.stop();
            } else {
                mCityAdapter.updateLocateState(LocateState.FAILED, null);
                ToastUtils.showShort("定位失败，请打开位置信息");
            }
        }
    }


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
        //解绑
        ButterKnife.bind(this).unbind();
        //取消定位
        mLocationClient.unRegisterLocationListener(mMyLocationListener);
        mLocationClient.stop();
    }


}

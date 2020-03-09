package com.jjz.energy.ui.home.logistics;

import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.TextureMapView;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

/**
 * @Features: 地图选点功能
 * @author: create by chenhao on 2020/3/9
 */
public class MapSelectActivity extends BaseActivity {

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor bitmap;

    private TextView tvAddress;

    @Override
    public void initView() {
        mBaiduMap = mMapView.getMap();
//        initMap();
//        addMaker(bitmap);
    }


    /**
     * 初始化配置地图
     */
//    private void initMap() {
//        //生成maker点图标
//        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_select_mark);
//        // 隐藏LOGO
//        View child = mMapView.getChildAt(1);
//        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
//            child.setVisibility(View.INVISIBLE);
//        }
//        // 隐藏比例尺
//        mMapView.showScaleControl(false);
//        // 隐藏缩放控件
//        mMapView.showZoomControls(false);
//
//        //开启交通图
////        mBaiduMap.setTrafficEnabled(true);
//        //设置地图显示类型   普通/卫星/空白
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//
//        //设置地图的默认显示地点和缩放级别
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory
//                .newLatLngZoom(new LatLng(Double.valueOf(Config.latitude),
//                        Double.valueOf(Config.longitude)), 18.0f));
//
//        //监听
//        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                //获取经纬度
//                double latitude = latLng.latitude;
//                double longitude = latLng.longitude;
//                //清除图层
//                mBaiduMap.clear();
//                // 定义Maker坐标点
//                LatLng point = new LatLng(latitude, longitude);
//                //定义options设置maker属性
//                OverlayOptions options = new MarkerOptions().position(point).icon(bitmap);
//                //将maker添加到地图
//                mBaiduMap.addOverlay(options);
//                //实例化一个地理编码查询对象
//                GeoCoder geoCoder = GeoCoder.newInstance();
//                //设置反地理编码位置坐标
//                ReverseGeoCodeOption op = new ReverseGeoCodeOption();
//                op.location(point);
//                //发起反地理编码请求(经纬度->地址信息)
//                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//                    @Override
//                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//                        if (geoCodeResult == null
//                                || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                            Toast.makeText(getContext(), "没有检测到结果", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//                        if (reverseGeoCodeResult == null
//                                || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                            Toast.makeText(getContext(), "没有检测到结果", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //获取点击的坐标地址
//                            String address = reverseGeoCodeResult.getAddressDetail().countryName
//                                    + reverseGeoCodeResult.getAddressDetail().province
//                                    + reverseGeoCodeResult.getAddressDetail().city
//                                    + reverseGeoCodeResult.getAddressDetail().district
//                                    + reverseGeoCodeResult.getAddressDetail().street
//                                    + reverseGeoCodeResult.getAddressDetail().town;
//                            tvAddress.setText(address);
////                            Toast.makeText(MainActivity.this, "位置：" + address, Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                geoCoder.reverseGeoCode(op);
//                // 释放实例
//                geoCoder.destroy();
//            }
//
//            @Override
//            public boolean onMapPoiClick(MapPoi mapPoi) {
//                return false;
//            }
//        });
//    }

//    private void addMaker(BitmapDescriptor bitmap) {
//        //构建中心点
//        LatLng point = new LatLng(Double.valueOf(Config.latitude), Double.valueOf(Config.longitude));
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).draggable(true);
//        //在地图上添加Marker，并显示
//        mBaiduMap.addOverlay(option);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_map_select;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}

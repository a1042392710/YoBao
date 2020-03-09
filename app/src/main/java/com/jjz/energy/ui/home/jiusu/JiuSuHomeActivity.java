package com.jjz.energy.ui.home.jiusu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.jjz.energy.R;
import com.jjz.energy.alipay.PayResult;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.entry.jiusu.MapMarkerBean;
import com.jjz.energy.entry.jiusu.ShopMarkerBean;
import com.jjz.energy.presenter.jiusu.JiuSuHomePresenter;
import com.jjz.energy.util.MapUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.jiusu.IJiuSuHomeView;
import com.jjz.energy.wxapi.OrderPayTypeBean;
import com.jjz.energy.wxapi.WxPayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features: 主页
 * @author: create by chenhao on 2019/3/28
 */
@RuntimePermissions
public class  JiuSuHomeActivity extends BaseActivity<JiuSuHomePresenter> implements IJiuSuHomeView {

    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_service_point_name)
    TextView tvServicePointName;
    @BindView(R.id.tv_service_point_distance)
    TextView tvServicePointDistance;
    @BindView(R.id.tv_location_description)
    TextView tvLocationDescription;
    @BindView(R.id.ll_location_distance)
    LinearLayout llLocationDistance;
    @BindView(R.id.tv_business_hours)
    TextView tvBusinessHours;
    @BindView(R.id.ll_business_hours)
    LinearLayout llBusinessHours;
    @BindView(R.id.img_call)
    ImageView imgCall;
    @BindView(R.id.tv_oil_price)
    TextView tvOilPrice;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.et_oil_sum)
    EditText etOilSum;
    @BindView(R.id.tv_oil_money)
    TextView tvOilMoney;
    @BindView(R.id.tv_now_buy)
    TextView tvNowBuy;
    @BindView(R.id.ll_other_info)
    LinearLayout llOtherInfo;
    @BindView(R.id.img_look_all)
    ImageView imgLookAll;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.tv_look_all)
    TextView tvLookAll;
    @BindView(R.id.ll_look_all)
    LinearLayout llLookAll;
    @BindView(R.id.rl_location_info)
    RelativeLayout rlLocationInfo;

    @Override
    protected void initView() {
        tvToolbarRight.setText("会员中心");
        tvToolbarTitle.setText("久速销售网点");
        etOilSum.setEnabled(false);
        etOilSum.setSelection(etOilSum.getText().toString().length());
        EventBus.getDefault().register(this);
        //配置地图
        initBaiduMap();
    }

    @Override
    public void isSuccess(List<MapMarkerBean> data) {
        //查询服务网点，只要请求成功一次，下次就不再请求
        isSucGetMapData = true;
        //初始化服务网点
        initMarker(data);
    }

    /**
     * 油卡余额
     */
    private  double oil_balance = 0;
    /*
     * 选中的网点信息
     */
    private ShopMarkerBean mIndexMarkerBean;

    @SuppressLint("SetTextI18n")
    @Override
    public void isShopInfoSuccess(ShopMarkerBean data) {
        mIndexMarkerBean = data;
        //查询具体商户信息
        mSelectMarkerInfo.setSelectPhoneNumber(data.getShop_phone());
        mSelectMarkerInfo.setSelectOilMoney(data.getGoods_price());
        mSelectMarkerInfo.setSelectShopId(data.getShop_id());
        mSelectMarkerInfo.setGoods_name(data.getGoods_name());
        //显示网点地址
        tvLocationDescription.setText(data.getShop_address());
        //显示营业时间
        tvBusinessHours.setText(data.getShop_desc());
        //通过经纬度计算点位之间的距离
        tvServicePointDistance.setText(Utils.getDistance(mLng,mLat,data.getShop_lng(),data.getShop_lat())+"公里");
        //显示油价
        tvOilPrice.setText(data.getGoods_price()+"元（升)");
        //计算总价
        int sum = Integer.valueOf(etOilSum.getText().toString());
        double money = (sum*(mSelectMarkerInfo.getSelectOilMoney()*1000))/1000;
        tvOilMoney.setText(money+"元");
        //显示商品名称
        tvGoodsName.setText(data.getGoods_name());
        //保存油卡余额
        oil_balance = data.getOil_balance();
    }


    //****************************** 支付 ******************************

    //返回支付类型 和预生成的订单编号
    private String mOrderSn = "" ;
    @Override
    public void isCreateOrderSuccess(OrderPayTypeBean data) {
        //记录 订单编号
        mOrderSn = data.getOrder_sn();
        WxPayUtil wxPayUtil = new WxPayUtil();
        //微信支付
        if (mPayType==2) {
            wxPayUtil.pay(mContext, data,"shop");
        } else if (mPayType==1) {
            //支付宝支付
            if (!StringUtil.isEmpty(data.getZfb())) {
                new Thread(() -> {
                    PayTask mPayTask = new PayTask(this);
                    Map<String, String> result = mPayTask.payV2(data.getZfb(), true);
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }).start();
            }
        }else{
            goPaySuc();
        }
    }

    /**
     * 处理微信的支付回调
     * @param loginEventBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEventBean loginEventBean) {
        if (loginEventBean.getLoginStatus() == LoginEventBean.WEIXIN_PAYSUC) {//全部
            goPaySuc();
        }
    }

    /**
     * 处理支付宝的支付回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultStatus = payResult.getResultStatus();
            if ("9000".equals(resultStatus)) {
                //支付成功
                goPaySuc();
            } else if ("6001".equals(resultStatus)) {
                showToast( "支付取消");
            } else {
                showToast( "支付失败");
            }
        }


    };

    /**
     * 支付成功
     */
    private void goPaySuc() {
        //关闭支付的弹窗 跳转到支付成功页面
        if (mPayWindow != null && mPayWindow.isShowing()) {
            mPayWindow.dismiss();
        }
        startActivity(new Intent(mContext, JiuSuPaySucActivity.class).putExtra("money",
                tvOilMoney.getText().toString().replace("元", "")).putExtra("pay_type", mPayType));

    }

    //支付方式  1 支付宝  2 微信 3领取赠品
    int mPayType = 2 ;
    /**
     * 弹出支付页面
     */
    private PopupWindow mPayWindow;
    /*
     * 计算价格 判断是否满足积分抵扣的条件
     */
    private boolean  showIntegralToast (TextView money , TextView toast){

        //计算价格 判断是否满足积分抵扣的条件
        if (mIndexMarkerBean != null) {
            double old_money =  Double.valueOf(tvOilMoney.getText().toString().replace("元", ""));
            //打完折多少钱
            double new_money =( (mIndexMarkerBean.getRate()*10)*(old_money * 10) )/100;
            //折扣多少钱
            double integral_money = old_money -new_money;
            //和剩余积分进行比较，积分多，则该单可打折 并显示折扣后价格
            if (integral_money < mIndexMarkerBean.getPay_points()){
                money.setText(new_money+"");
                //并显示提示
                toast.setVisibility(View.VISIBLE);
                toast.setText("该单已使用积分抵扣" + integral_money + "元，您总积分为" + mIndexMarkerBean.getPay_points());
                return true;
            }else{
                money.setText(tvOilMoney.getText().toString().replace("元", ""));
                toast.setVisibility(View.GONE);
                return false;
            }
        } else {
            money.setText(tvOilMoney.getText().toString().replace("元", ""));
            toast.setVisibility(View.GONE);
            return false;
        }
    }

    private void showNowPay() {
        View popView = LayoutInflater.from(mContext).inflate(R.layout.pop_map_oil_pay_one, null);
        ((TextView) popView.findViewById(R.id.pop_tv_title)).setText(tvGoodsName.getText().toString() + "*" + etOilSum.getText().toString());
        //使用积分的提示
        TextView pop_tv_integral_toast = popView.findViewById(R.id.pop_tv_integral_toast);
        TextView pop_tv_money = popView.findViewById(R.id.pop_tv_money);
        //展示积分抵扣提示 并得到是否满足积分抵扣的条件
        boolean isIntegral = showIntegralToast(pop_tv_money, pop_tv_integral_toast);
        //记录选中的状态
        if (mPayType == 1) {
            ((RadioButton) popView.findViewById(R.id.pop_rb_alipay)).setChecked(true);
        } else if (mPayType == 2) {
            ((RadioButton) popView.findViewById(R.id.pop_rb_wechat)).setChecked(true);
        } else {
            ((RadioButton) popView.findViewById(R.id.pop_rb_free_get)).setChecked(true);
        }
        //支付宝和微信的点击事件
        popView.findViewById(R.id.pop_ll_alipay).setOnClickListener(v -> {
            mPayType = 1;
            ((RadioButton) popView.findViewById(R.id.pop_rb_alipay)).setChecked(true);
            ((RadioButton) popView.findViewById(R.id.pop_rb_wechat)).setChecked(false);
            ((RadioButton) popView.findViewById(R.id.pop_rb_free_get)).setChecked(false);
            //选中的时候计算价格
            showIntegralToast(pop_tv_money, pop_tv_integral_toast);
        });
        popView.findViewById(R.id.pop_ll_wechat).setOnClickListener(v -> {
            mPayType = 2;
            ((RadioButton) popView.findViewById(R.id.pop_rb_wechat)).setChecked(true);
            ((RadioButton) popView.findViewById(R.id.pop_rb_alipay)).setChecked(false);
            ((RadioButton) popView.findViewById(R.id.pop_rb_free_get)).setChecked(false);
            //选中的时候计算价格
            showIntegralToast(pop_tv_money, pop_tv_integral_toast);
        });
        //如果还有剩余可领取的油，则显示赠品领取
        if (oil_balance > 0) {
            popView.findViewById(R.id.pop_ll_free_get).setVisibility(View.VISIBLE);
            ((TextView) popView.findViewById(R.id.pop_tv_remaining)).setText("（剩余" + oil_balance + "L）");
            popView.findViewById(R.id.pop_ll_free_get).setOnClickListener(v -> {
                mPayType = 3;
                ((RadioButton) popView.findViewById(R.id.pop_rb_free_get)).setChecked(true);
                ((RadioButton) popView.findViewById(R.id.pop_rb_wechat)).setChecked(false);
                ((RadioButton) popView.findViewById(R.id.pop_rb_alipay)).setChecked(false);
                pop_tv_integral_toast.setVisibility(View.GONE);
                //领取赠品不可使用积分 并将价格变回
                pop_tv_integral_toast.setVisibility(View.GONE);
                pop_tv_money.setText(tvOilMoney.getText().toString().replace("元",""));
            });

        }
        //确认支付 先创建订单 再调起支付
        popView.findViewById(R.id.pop_tv_pay).setOnClickListener(v -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("shop_id", mSelectMarkerInfo.getSelectShopId());
            hashMap.put("goods_num", etOilSum.getText().toString());
            hashMap.put("order_amount", pop_tv_money.getText().toString());
            if (mPayType == 3) {
                hashMap.put("pay_code", "oil_balance");
            } else {
                hashMap.put("pay_code", mPayType == 1 ? "alipayApp" : "appWeixinPay");
            }
            mPresenter.createOrder(PacketUtil.getRequestPacket(hashMap));
        });
        mPayWindow = PopWindowUtil.getInstance().showPopupWindow(mContext,popView);
        popView.findViewById(R.id.pop_img_close).setOnClickListener(v -> {
            mPayWindow.dismiss();
        });
    }
    // **********************************  地图 **********************************

    /**
     * 地图控制器
     */
    private BaiduMap mBaiduMap;
    /**
     * 定位控制器
     */
    private LocationClient mLocationClient;
    /**
     * 是否第一次定位，如果是第一次定位的话要将自己的位置显示在地图 中间
     */
    private boolean isFirstLocation = true;

    /**
     * 配置地图
     */
    private void initBaiduMap() {
        //得到地图控制器
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //设置最大和最小缩放级别
        mBaiduMap.setMaxAndMinZoomLevel(22f,5f);
        //移除百度地图logo
        mMapView.removeViewAt(1);
        //不显示放大缩小控件
        mMapView.showZoomControls(false);
        //不显示比例尺
        mMapView.showScaleControl(false);
        mBaiduMap.setMyLocationEnabled(true);
        //初始化地图监听
        initListener();
        //初始化定位
        initLocation();
    }

    /**
     * 是否成功请求网点数据
     */
    private boolean isSucGetMapData = false;
    /**
     * 记录点击放大的点位
     */
    private Marker mBigMarker ;
    /**
     * 记录当前位置的经纬度
     */
    private double mLat,mLng;
    /**
     * 选中的点位信息
     */
    private SelectMarkerInfo mSelectMarkerInfo  =new SelectMarkerInfo();;

    /**
     * 初始化地图等监听
     */
    private void initListener() {
        //设置地图单击事件监听
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            /**
             * 地图单击事件回调函数
             *
             * @param point 点击的地理坐标
             */
            @Override
            public void onMapClick(LatLng point) {
                rlLocationInfo.setVisibility(View.GONE);
            }

            /**
             * 地图内 Poi 单击事件回调函数
             *
             * @param mapPoi 点击的 poi 信息
             */
            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
            }
        });

        //marker被点击时回调的方法 若响应点击事件，返回true，否则返回false
        mBaiduMap.setOnMarkerClickListener(marker -> {
            onMarkerClick(marker);
            return true;
        });

        //监听购买油的数量
        etOilSum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtil.isEmpty(s.toString())){
                    tvOilMoney.setText("0.00元");
                    return;
                }
                //计算总价
                int sum = Integer.valueOf(etOilSum.getText().toString());
                double money = (sum*(mSelectMarkerInfo.getSelectOilMoney()*1000))/1000;
                tvOilMoney.setText(money+"元");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        //定位初始化
        mLocationClient = new LocationClient(mContext);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//刷新时间
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
    }

    /**
     * 点位点击事件
     * @param marker
     */
    private void onMarkerClick(Marker marker){
        //大图标
        if (mBigMarker!=null){
            mBigMarker.setIcon(BitmapDescriptorFactory
                    .fromResource(R.mipmap.ic_map_gas_marker));
        }
        mBigMarker = marker;
        marker.setIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_map_gas_marker_big));
        //动画的方式到中间
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(marker.getPosition()));
        //再放大
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
        //获取点位信息
        Bundle bundle = marker.getExtraInfo();
        //显示网点信息
        rlLocationInfo.setVisibility(View.VISIBLE);
        //设置网点的标题
        tvServicePointName.setText(bundle.getString("title"));
        mPresenter.getShopInfo(PacketUtil.getRequestPacket(Utils.stringToMap("shop_id",
                bundle.getInt("shop_id")+"")));
    }

    /**
     * 初始化点位
     * @param data
     */
    private void initMarker(List<MapMarkerBean> data) {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_map_gas_marker);
        //创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<>();
        for (int i = 0; i <data.size() ; i++) {
            //点位信息存入 bundle
            Bundle bundle = new Bundle();
            bundle.putString("title",data.get(i).getShop_name());
            bundle.putInt("shop_id",data.get(i).getShop_id());
            //创建OverlayOptions属性
            options.add(new MarkerOptions()
                    .position(new LatLng(data.get(i).getShop_lat(), data.get(i).getShop_lng())) //位置坐标
                    .icon(bitmap) //图标
                    .extraInfo(bundle)//点位存储的信息
                    .animateType(MarkerOptions.MarkerAnimateType.jump));//动画
        }
        //在地图上批量添加
        mBaiduMap.addOverlays(options);
        stopLoading();
    }


    // *************************** 生命周期及权限适配 ***************************

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callPhone() {
        // 拨号：激活系统的拨号组件
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作

        Uri data = Uri.parse("tel:" + mSelectMarkerInfo.getSelectPhoneNumber()); // 设置数据
        intent.setData(data);
        startActivity(intent); // 激活Activity组件
    }
    //给用户解释为什么要申请权限
    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showCallPhone(final PermissionRequest request) {
        //唤起打电话权限
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有电话权限可不能打电话哦", () -> {
            request.proceed();//继续执行请求
        });
    }

    /**
     * 是否已经展开
     */
    private boolean isLookAll ;
    @OnClick({R.id.ll_location,R.id.ll_toolbar_left,R.id.tv_toolbar_right, R.id.img_call, R.id.ll_look_all, R.id.img_close, R.id.tv_now_buy, R.id.rl_location_info, R.id.tv_add, R.id.tv_remove , R.id.tv_navigation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //定位
            case R.id.ll_location:
                isFirstLocation = true;
                break;
            //导航
            case R.id.tv_navigation:
                goNavigation();
                break;
            //打电话
            case R.id.img_call:
                JiuSuHomeActivityPermissionsDispatcher.callPhoneWithCheck(this);
                break;
            //立即购买
            case R.id.tv_now_buy:
                if (StringUtil.isEmpty(etOilSum.getText().toString()) || etOilSum.getText().toString().equals("0")) {
                    showToast("请输入购买数量且不能为0");
                    return;
                }
                showNowPay();
                break;
            //关闭位置信息
            case R.id.img_close:
                rlLocationInfo.setVisibility(View.GONE);
                break;
            //查看所有
            case R.id.ll_look_all:
                //闭合
                if (isLookAll){
                    isLookAll=false;
                    llOtherInfo.setVisibility(View.GONE);
                    tvLookAll.setText("点击展开");
                    imgLookAll.setImageResource(R.mipmap.ic_map_down_arrow);

                }else{
                    //展开
                    isLookAll=true;
                    llOtherInfo.setVisibility(View.VISIBLE);
                    tvLookAll.setText("点击收回");
                    imgLookAll.setImageResource(R.mipmap.ic_map_up_arrow);

                }
                break;
            //加  每次最少 10 升
            case R.id.tv_add:
                int sum2 = Integer.valueOf(etOilSum.getText().toString());
                if (sum2<=500){
                    etOilSum.setText(Integer.valueOf(etOilSum.getText().toString()) + 10+"");
                }
                break;
            //减
            case R.id.tv_remove:
                int sum = Integer.valueOf(etOilSum.getText().toString());
                if (sum>=20){
                    etOilSum.setText(sum - 10+"");
                }
                break;

            case R.id.rl_location_info:
                break;
            case R.id.ll_toolbar_left:
                finish();
                break;
                //会员中心
            case R.id.tv_toolbar_right:
                //进入久速1.0的 我的页面
                    startActivity(new Intent(mContext,JiuSuMineActivity.class));
                break;
        }
    }

    /**
     * 去导航
     */
    private void goNavigation() {
        //获取点位信息
        Bundle bundle = mBigMarker.getExtraInfo();
        //选择地图
        View navigationView = View.inflate(mContext, R.layout.pop_go_map,null);
        TextView baiduMap = navigationView.findViewById(R.id.item_tv_baidu);
        TextView gaodeMap = navigationView.findViewById(R.id.item_tv_gaode);
        TextView tengxunMap = navigationView.findViewById(R.id.item_tv_tengxun);
        TextView cancle = navigationView.findViewById(R.id.item_tv_cancle);
        PopupWindow popupWindow =  PopWindowUtil.getInstance().showBottomWindow(mContext,navigationView);
        cancle.setOnClickListener(v -> popupWindow.dismiss());
        //腾讯
        tengxunMap.setOnClickListener(v -> {
            if (MapUtil.isTencentMapInstalled()){
                popupWindow.dismiss();
                MapUtil.openTencentMap(mContext, 0, 0, null, mBigMarker.getPosition().latitude, mBigMarker.getPosition().longitude, bundle.getString("title"));
            } else {
                //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                showToast("尚未安装腾讯地图");
            }
        });
        //高德
        gaodeMap.setOnClickListener(v -> {
            if (MapUtil.isGdMapInstalled()) {
                popupWindow.dismiss();
                MapUtil.openGaoDeNavi(mContext, 0, 0, null, mBigMarker.getPosition().latitude, mBigMarker.getPosition().longitude, bundle.getString("title"));
            } else {
                //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                showToast("尚未安装高德地图");
            }
        });
        //百度
        baiduMap.setOnClickListener(v -> {
            if (MapUtil.isBaiduMapInstalled()){
                popupWindow.dismiss();
                MapUtil.openBaiDuNavi(mContext, 0, 0, null,  mBigMarker.getPosition().latitude, mBigMarker.getPosition().longitude, bundle.getString("title"));
            } else {
                showToast("尚未安装百度地图");
            }
        });


    }


    /**
     * 定位监听
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            Log.i("定位状态:", location.getLocType() + "");
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            //是否刷新位置
            if (isFirstLocation) {
                //保存经纬度
                mLat = location.getLatitude();
                mLng = location.getLongitude();
                //动画的方式到中间
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                //再放大
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(13).build()));
                isFirstLocation = false;
            }
        }
    }


    class SelectMarkerInfo{
        /**
         * 记录选中的点位的电话
         */
        private String mSelectPhoneNumber = "";
        /**
         * 记录选中的服务点油价
         */
        private double mSelectOilMoney = 4.7 ;
        /**
         * 选中的点位的shopId
         */
        private String mSelectShopId ;
        /**
         * 商品名称
         */
        private String goods_name ;

        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getSelectPhoneNumber() {
            return mSelectPhoneNumber == null ? "" : mSelectPhoneNumber;
        }

        public void setSelectPhoneNumber(String selectPhoneNumber) {
            mSelectPhoneNumber = selectPhoneNumber;
        }

        public double getSelectOilMoney() {
            return mSelectOilMoney;
        }

        public void setSelectOilMoney(double selectOilMoney) {
            mSelectOilMoney = selectOilMoney;
        }

        public String getSelectShopId() {
            return mSelectShopId == null ? "" : mSelectShopId;
        }

        public void setSelectShopId(String selectShopId) {
            mSelectShopId = selectShopId;
        }
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_jiusu_home;
    }
    @Override
    protected JiuSuHomePresenter getPresenter() {
        return new JiuSuHomePresenter(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        JiuSuHomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    @Override
    public void onResume() {
        if (mLocationClient!=null){
            mLocationClient.start();
        }
        mMapView.onResume();
        if (!isSucGetMapData){
            mPresenter.getServiceMarkerInfo(PacketUtil.getRequestPacket(null));
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        mLocationClient.stop();
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}

package com.jjz.energy.ui.jiusu_shop.shop_order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.jjz.energy.R;
import com.jjz.energy.alipay.PayResult;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.entry.commodity.GoodsBean;
import com.jjz.energy.presenter.order.SureBuyPresenter;
import com.jjz.energy.ui.mine.shipping_address.AddressManagerActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.order.ISureBuyView;
import com.jjz.energy.widgets.singlepicker.SinglePicker;
import com.jjz.energy.wxapi.OrderPayTypeBean;
import com.jjz.energy.wxapi.WxPayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 确定购买页面
 * @author: create by chenhao on 2019/9/26
 */
public class SureBuyActivity extends BaseActivity<SureBuyPresenter>implements ISureBuyView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.tv_commodity_title)
    TextView tvCommodityTitle;
    @BindView(R.id.tv_commodity_price)
    TextView tvCommodityPrice;
    @BindView(R.id.tv_commodity_old_price)
    TextView tvCommodityOldPrice;
    @BindView(R.id.tv_shipping_address)
    TextView tvShippingAddress;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_price_title)
    TextView tvPriceTitle;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_pay_type)
    LinearLayout llPayType;
    @BindView(R.id.tv_integral_toast)
    TextView tvIntegralToast;

    /**
     * 记录付款方式  默认为微信支付
     */
    private String selectPayType  = Constant.PAYTYPE_WECHAT;

    /**
     * 付款方式
     */
    private String[] mPayTypes = {"微信支付", "支付宝支付"};

    //单项选择器 (选择付款方式）
    private SinglePicker<String> pickerPayType;

    /**
     * 订单编号
     */
    private String order_sn;
    /**
     * 商品信息
     */
    private GoodsBean mGoodsBean;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("购买商品");
        EventBus.getDefault().register(this);
        initSingerPicker();
        //查询商品信息
        mPresenter.getGoodsInfo(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.GOODS_ID,getIntent().getIntExtra(Constant.GOODS_ID,0)+"")));
    }

    /**
     * 初始化单项选择器
     */
    private void initSingerPicker() {
        //性别
        pickerPayType = new SinglePicker<>(this, mPayTypes);
        pickerPayType.setItemWidth(200);
        pickerPayType.setTitleText("请选择");
        pickerPayType.setOnItemPickListener((index, item) -> {
               selectPayType = index==0?Constant.PAYTYPE_WECHAT:Constant.PAYTYPE_ALIPAY;
               tvPayType.setText(item);
        });
    }

    /**
     * 地址id
     */
    private int address_id;

    @SuppressLint("SetTextI18n")
    @Override
    public void isGetGoodsInfoSuc(GoodsBean data) {
        mGoodsBean = data;
        address_id = data.getAddress_id();
        //图片
        GlideUtils.loadRoundCircleImage(mContext,data.getGoods_images(),imgCommodity);
        //原价
        tvCommodityOldPrice.setText("原价￥"+data.getMarket_price());
        //现价
        tvCommodityPrice.setText("￥"+data.getShop_price());
        //商品名称
        tvCommodityTitle.setText(data.getGoods_name());
        //运费
        tvFreight.setText("￥"+data.getShopping_price());
        //获取剩余积分
        String pay_ponits = SpUtil.init(mContext).readString(Constant.PAY_POINTS);
        if (!StringUtil.isEmpty(pay_ponits)){
            //总价
            double old_money = data.getShop_price() + data.getShopping_price();
            //打完折多少钱
            double new_money =( (data.getRebate()*10)*(old_money * 10) )/100;
            //折扣多少钱
            double integral_money = old_money -new_money;
            //和剩余积分进行比较，积分多，则该单可打折 并显示折扣后价格
            if (integral_money <= Double.valueOf(pay_ponits)){
                tvPriceTitle.setText(new_money+"");
                //并显示提示
                tvIntegralToast.setVisibility(View.VISIBLE);
                tvIntegralToast.setText("该单已使用积分抵扣" + integral_money + "元，您总积分为" + pay_ponits);
            }else{
                //总价 = 现价 + 运费
                tvPriceTitle.setText("￥"+(data.getShop_price() + data.getShopping_price()));
            }
        }else{
            //总价 = 现价 + 运费
            tvPriceTitle.setText("￥"+(data.getShop_price() + data.getShopping_price()));
        }

        //地址 如果没有地址 则需要填写
        if (StringUtil.isEmpty(data.getFull_address())){
            tvShippingAddress.setHint("您还没有添加收货地址");
        }else{
            tvShippingAddress.setText(data.getFull_address());
        }
    }

    //获取支付信息
    @Override
    public void isGetBuyInfoSuccess(OrderPayTypeBean data) {
        order_sn = data.getOrder_sn();
        WxPayUtil wxPayUtil = new WxPayUtil();
        //微信支付
        if (selectPayType.equals(Constant.PAYTYPE_WECHAT)) {
            wxPayUtil.pay(mContext, data, "shop");
        } else if (selectPayType.equals(Constant.PAYTYPE_ALIPAY)) {
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
            if (Constant.PAYSUC_ALIPAY_CODE.equals(resultStatus)) {
                //支付成功
                paySuc();
            } else if (Constant.PAYCANCLE_ALIPAY_CODE.equals(resultStatus)) {
                showToast("支付取消");
            } else {
                showToast("支付失败");
            }
        }
    };

    /**
     * 处理微信的支付回调
     *
     * @param loginEventBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEventBean loginEventBean) {
        if (loginEventBean.getLoginStatus() == LoginEventBean.WEIXIN_PAYSUC) {
            paySuc();
        }
    }


    //支付成功 进入订单详情页面
    private void paySuc() {
        startActivity(new Intent(mContext,OrderDetailsActivity.class).putExtra(Constant.ORDER_SN,order_sn).putExtra(Constant.USER_TYPE,0));
        finish();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.ll_address,
             R.id.tv_integral, R.id.ll_pay_type ,R.id.tv_sure_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_toolbar_left:
                finish();
                break;
                //收货地址
            case R.id.ll_address:
                startActivityForResult(new Intent(mContext,AddressManagerActivity.class).putExtra(AddressManagerActivity.SELECT_ADDRESS,true),10);
                break;
                //支付方式
            case R.id.ll_pay_type:
                pickerPayType.show();
                break;
                //积分抵扣
            case R.id.tv_integral:
                break;
                //确认购买
            case R.id.tv_sure_buy:
                //收货地址
                if (StringUtil.isEmpty(tvShippingAddress.getText().toString())){
                    showToast("请添加收货地址");
                    return;
                }
                if (mGoodsBean==null){
                    showToast("商品信息获取失败");
                    return;
                }
                //调起支付
                HashMap<String,String>map = new HashMap<>();
                map.put(Constant.GOODS_ID,mGoodsBean.getGoods_id()+"");
                map.put("address_id",address_id+"");
                map.put("pay_code", selectPayType);
                map.put("goods_num", "1");//数量
                mPresenter.getBuyGoodsInfo(PacketUtil.getRequestPacket(map));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10&&resultCode==AddressManagerActivity.RESULT_ADDRESS){
            AddressBean.ListBean bean = (AddressBean.ListBean) data.getSerializableExtra(Constant.INTENT_KEY_OBJECT);
            //显示收货地址
            String addressStr = bean.getArea()+bean.getAddress();
            //地址
            tvShippingAddress.setText(addressStr.replace(" ", ""));
            address_id = bean.getAddress_id();
        }
    }

    @Override
    protected SureBuyPresenter getPresenter() {
        return new SureBuyPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_sure_buy;
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }
}

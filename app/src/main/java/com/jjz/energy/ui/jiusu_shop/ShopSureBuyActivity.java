package com.jjz.energy.ui.jiusu_shop;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.jjz.energy.R;
import com.jjz.energy.alipay.PayResult;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;
import com.jjz.energy.presenter.order.SureBuyPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.order.ISureBuyView;
import com.jjz.energy.widgets.singlepicker.SinglePicker;
import com.jjz.energy.wxapi.OrderPayTypeBean;
import com.jjz.energy.wxapi.WxPayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 确定购买页面
 * @author: create by chenhao on 2019/9/26
 */
public class ShopSureBuyActivity extends BaseActivity<SureBuyPresenter> implements ISureBuyView {


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
    @BindView(R.id.tv_commodity_old_price)
    TextView tvCommodityOldPrice;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.ll_pay_type)
    LinearLayout llPayType;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_price_title)
    TextView tvPriceTitle;
    @BindView(R.id.tv_sure_buy)
    TextView tvSureBuy;
    @BindView(R.id.et_pay_price)
    EditText etPayPrice;
    /**
     * 记录付款方式  默认为微信支付
     */
    private String selectPayType = Constant.PAYTYPE_WECHAT;
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
     * 商户的数据
     */
    private ShopHomePageBean mShopHomePageBean;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("店内买单");
        EventBus.getDefault().register(this);
        mShopHomePageBean = (ShopHomePageBean) getIntent().getSerializableExtra(Constant.INTENT_KEY_OBJECT);
        initData();
        initSingerPicker();
        //金额发生变化后，和折扣相乘等于结果
        etPayPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //计算折扣价
                String price = etPayPrice.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (mShopHomePageBean == null) {
            return;
        }
        List<String> list = Arrays.asList(mShopHomePageBean.getHeader_img().split(","));
        if (!StringUtil.isListEmpty(list)){
            GlideUtils.loadRoundCircleImage(mContext,list.get(0),imgCommodity);
        }
        //商店名称
        tvCommodityTitle.setText(mShopHomePageBean.getShop_name());
        //地址
        tvCommodityOldPrice.setText(mShopHomePageBean.getPoiaddress());
        //折扣

    }

    /**
     * 初始化单项选择器
     */
    private void initSingerPicker() {
        //支付方式
        pickerPayType = new SinglePicker<>(this, mPayTypes);
        pickerPayType.setItemWidth(200);
        pickerPayType.setTitleText("请选择");
        pickerPayType.setOnItemPickListener((index, item) -> {
            selectPayType = index == 0 ? Constant.PAYTYPE_WECHAT : Constant.PAYTYPE_ALIPAY;
            tvPayType.setText(item);
        });
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
        //进入店内买单的详情页面
//        startActivity(new Intent(mContext, OrderDetailsActivity.class).putExtra(Constant.ORDER_SN
//                , order_sn).putExtra(Constant.USER_TYPE, 0));
//        finish();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.ll_pay_type, R.id.tv_sure_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_toolbar_left:
                finish();
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
                //调起支付
                HashMap<String, String> map = new HashMap<>();
                map.put("pay_code", selectPayType);
                map.put("pay_price", etPayPrice.getText().toString());
                map.put("shop_id", mShopHomePageBean.getId()+"");
                mPresenter.getBuyGoodsInfo(PacketUtil.getRequestPacket(map));
                break;
        }
    }



    @Override
    protected SureBuyPresenter getPresenter() {
        return new SureBuyPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_shop_sure_buy;
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

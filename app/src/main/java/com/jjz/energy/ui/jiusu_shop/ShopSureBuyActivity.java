package com.jjz.energy.ui.jiusu_shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
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
import com.jjz.energy.entry.jiusu_shop.JiuSuShop;
import com.jjz.energy.presenter.order.SureBuyPresenter;
import com.jjz.energy.util.DecimalDigitsInputFilter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.order.ISureBuyView;
import com.jjz.energy.widgets.singlepicker.SinglePicker;
import com.jjz.energy.wxapi.OrderPayTypeBean;
import com.jjz.energy.wxapi.WxPayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.HashMap;
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
    @BindView(R.id.tv_pay_toast)
    TextView tvPayToast;
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
     * 商家 id
     */
    private int shop_id ;


    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        tvToolbarTitle.setText("店内买单");
        shop_id = getIntent().getIntExtra(Constant.SHOP_ID,0);
        initEditHight();
        initSingerPicker();
        //查询商户信息
        mPresenter.getShopsInfo(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.SHOP_ID,shop_id+"")));
    }
    /**
     * 监听键盘
     */
    private void initEditHight() {
        //设置小数点后最多输入一位
        etPayPrice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(1)});

        etPayPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtil.isEmpty(etPayPrice.getText().toString())) {
                    //键盘收回时计算金额
                    calculationMoney();
                } else {
                    tvPriceTitle.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    /**
     * 计算折扣金额
     */
    private void calculationMoney() {
        tvPriceTitle.setText(etPayPrice.getText().toString());
        //买单金额
        float price = Float.valueOf(etPayPrice.getText().toString());
        if (mJiuSuShop != null) {
            //计算折扣价  先拿到折扣
            if (mJiuSuShop.getPay_points() > 0) {
                //打完折多少钱
                double new_money =((mJiuSuShop.getRebate()*10)*(price * 10) )/100;
                //折扣多少钱
                float integral_money = (float) (price - new_money);
                //和剩余积分进行比较，积分多，则该单可打折 并显示折扣后价格
                if (integral_money <=  mJiuSuShop.getPay_points()){
                    tvPriceTitle.setText("￥"+new_money);
                    //并显示提示
                    tvPayToast.setText("该单已使用积分抵扣" + integral_money + "元，您总积分为" + mJiuSuShop.getPay_points());
                }
            }
        }
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

    /**
     * 数据存下来
     */
    private JiuSuShop mJiuSuShop ;

    @Override
    public void isGetShopsInfoSuc(JiuSuShop data) {
        mJiuSuShop = data;
        GlideUtils.loadRoundCircleImage(mContext, data.getShop_img(), imgCommodity);
        //商店名称
        tvCommodityTitle.setText(data.getShop_name());
        //地址
        tvCommodityOldPrice.setText(data.getPoiaddress());
        //折扣
        tvFreight.setText((data.getRebate()*10)+"折");
        //剩余积分 和提示
        tvPayToast.setText("您还剩余"+data.getPay_points()+"积分");

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

    /**
     * 支付成功携带参数进下个页面
     */
    public  class Parms  implements Serializable {
        private String shop_name;
        private String shop_img;
        private String price_title;
        private long pay_time;
        public String getShop_name() {
            return shop_name == null ? "" : shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_img() {
            return shop_img == null ? "" : shop_img;
        }

        public void setShop_img(String shop_img) {
            this.shop_img = shop_img;
        }

        public String getPrice_title() {
            return price_title == null ? "" : price_title;
        }

        public void setPrice_title(String price_title) {
            this.price_title = price_title;
        }

        public long getPay_time() {
            return pay_time;
        }

        public void setPay_time(long pay_time) {
            this.pay_time = pay_time;
        }
    }


    //支付成功 进入订单详情页面
    private void paySuc() {

      Parms parms = new Parms();
        parms.setPay_time(System.currentTimeMillis());
        parms.setPrice_title(tvPriceTitle.getText().toString());
        parms.setShop_img(mJiuSuShop.getShop_img());
        parms.setShop_name(mJiuSuShop.getShop_name());
        //进入店内买单的详情页面
        startActivity(new Intent(mContext, JiuSuShopPaySucActivity.class).putExtra(Constant.INTENT_KEY_OBJECT,parms));
        finish();
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
                map.put("shop_id",shop_id+"");
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

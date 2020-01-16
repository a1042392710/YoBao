package com.jjz.energy.ui.home.entrust;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.jjz.energy.R;
import com.jjz.energy.alipay.PayResult;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.presenter.home.EntrustListPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.home.IEntrustListView;
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
 * @Features: 发布委托
 * @author: create by chenhao on 2019/11/25
 */
public class PutEntrustActivity extends BaseActivity<EntrustListPresenter> implements IEntrustListView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_entrust)
    EditText etEntrust;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.et_people)
    EditText etPeople;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_put_entrust)
    TextView tvPutEntrust;

    private String address;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("发布委托");
        EventBus.getDefault().register(this);
        //设置定位
        address = SpUtil.init(mContext).readString(Constant.LOCATION_ADDRESS);
        if (!StringUtil.isEmpty(address)){
            tvLocation.setText(address.replace("/", " "));
        }

    }

    /**
     * 发布委托
     */
    private void submit() {
        if (StringUtil.isEmpty(etEntrust.getText().toString())){
            showToast("请输入委托系信息");
            return;
        }
        if (StringUtil.isEmpty(etPeople.getText().toString())){
            showToast("请输入联系人");
            return;
        }
        if (StringUtil.isEmpty(etMoney.getText().toString())){
            showToast("请输入赏金");
            return;
        }
        showNowPay();
    }



    //****************************** 支付 ******************************

    //返回支付类型 和预生成的订单编号
    private String mOrderSn = "" ;
    @Override
    public void isPutEntrustSuc(OrderPayTypeBean data) {
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
        startActivity(new Intent(mContext,MineEntrustActivity.class));
    }

    //支付方式  1 支付宝  2 微信 3领取赠品
    int mPayType = 2 ;
    /**
     * 弹出支付页面
     */
    private PopupWindow mPayWindow;


    private void showNowPay() {
        View popView = LayoutInflater.from(mContext).inflate(R.layout.pop_map_oil_pay_one, null);
        ((TextView) popView.findViewById(R.id.pop_tv_title)).setText("预付赏金");
        //使用积分的提示
        TextView pop_tv_money = popView.findViewById(R.id.pop_tv_money);
        pop_tv_money.setText(etMoney.getText().toString());
        //记录选中的状态
        if (mPayType == 1) {
            ((RadioButton) popView.findViewById(R.id.pop_rb_alipay)).setChecked(true);
        } else if (mPayType == 2) {
            ((RadioButton) popView.findViewById(R.id.pop_rb_wechat)).setChecked(true);
        }
        //支付宝和微信的点击事件
        popView.findViewById(R.id.pop_ll_alipay).setOnClickListener(v -> {
            mPayType = 1;
            ((RadioButton) popView.findViewById(R.id.pop_rb_alipay)).setChecked(true);
            ((RadioButton) popView.findViewById(R.id.pop_rb_wechat)).setChecked(false);

        });
        popView.findViewById(R.id.pop_ll_wechat).setOnClickListener(v -> {
            mPayType = 2;
            ((RadioButton) popView.findViewById(R.id.pop_rb_wechat)).setChecked(true);
            ((RadioButton) popView.findViewById(R.id.pop_rb_alipay)).setChecked(false);

        });

        //确认支付 先创建订单 再调起支付
        popView.findViewById(R.id.pop_tv_pay).setOnClickListener(v -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("demand_content", etEntrust.getText().toString());
            hashMap.put("consignee", etPeople.getText().toString());
            hashMap.put("money", etMoney.getText().toString());
            hashMap.put("pay_code", mPayType == 1 ? "alipayApp" : "appWeixinPay");
            //根据逗号分隔到List数组中
            List<String> list= Arrays.asList(address.split("/"));
            if (list.size()>2){
                //地区
                hashMap.put("province",list.get(0));
                hashMap.put("city",list.get(1));
                hashMap.put("district",list.get(2));
            }
            mPresenter.putEntrust(PacketUtil.getRequestPacket(hashMap));
        });
        mPayWindow = PopWindowUtil.getInstance().showPopupWindow(mContext,popView);
        popView.findViewById(R.id.pop_img_close).setOnClickListener(v -> {
            mPayWindow.dismiss();
        });
    }


    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
    protected int layoutId() {
        return R.layout.act_put_entrust;
    }

    @Override
    protected EntrustListPresenter getPresenter() {
        return new EntrustListPresenter(this);
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_put_entrust})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //发布委托
            case R.id.tv_put_entrust:
                submit();
                break;
        }
    }


}

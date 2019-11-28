package com.jjz.energy.ui.home.jiusu;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
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
import com.jjz.energy.entry.enums.VipLevelEnum;
import com.jjz.energy.entry.jiusu.LoginBean;
import com.jjz.energy.entry.jiusu.VipListInfo;
import com.jjz.energy.presenter.jiusu.UpgradeMemberShipPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.jiusu.IUpgradeMemberShipView;
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

/**
 * @Features: 升级会员
 * @author: create by chenhao on 2019/5/13
 */
public class JiuSuUpVipActivity extends BaseActivity<UpgradeMemberShipPresenter> implements IUpgradeMemberShipView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_vip_level)
    TextView tvVipLevel;
    @BindView(R.id.ll_lable_one)
    LinearLayout llLableOne;
    @BindView(R.id.ll_lable_two)
    LinearLayout llLableTwo;
    @BindView(R.id.ll_lable_three)
    LinearLayout llLableThree;
    @BindView(R.id.cv_up_vip)
    CardView cvUpVip;
    @BindView(R.id.tv_vip_name_one)
    TextView tvVipNameOne;
    @BindView(R.id.tv_vip_money_one)
    TextView tvVipMoneyOne;
    @BindView(R.id.tv_vip_name_two)
    TextView tvVipNameTwo;
    @BindView(R.id.tv_vip_money_two)
    TextView tvVipMoneyTwo;
    @BindView(R.id.tv_vip_name_three)
    TextView tvVipNameThree;
    @BindView(R.id.tv_vip_money_three)
    TextView tvVipMoneyThree;
    @BindView(R.id.text_one)
    TextView textOne;
    @BindView(R.id.text_two)
    TextView textTwo;
    /**
     * 选中的目标vip等级 默认下标第一个
     */
    private int mVipLevelSelectIndex = 0;

    /**
     * 当前会员等级
     */
    private int mVipLevel;
    /**
     * 会员信息
     */
    private List<VipListInfo> mVipList = new ArrayList<>();
    /**
     * 传过来的数据
     */
    private LoginBean loginBean;

    @Override
    protected int layoutId() {
        return R.layout.act_member_benefits;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("升级会员");
        EventBus.getDefault().register(this);
        //从本地拿会员信息
        loginBean = (LoginBean) getIntent().getSerializableExtra(Constant.INTENT_KEY_OBJECT);
        tvVipLevel.setText(VipLevelEnum.getName(loginBean.getLevel_id()));
        //当前会员等级
        mVipLevel = loginBean.getLevel_id();
        //设置VIP的VIew
        initVipView();
        //获取vip信息
        mPresenter.getVipInfo(PacketUtil.getRequestPacket(null));
    }

    /**
     * 设置Vip的UI
     */
    private void initVipView() {

        switch (mVipLevel) {
            //代理
            case 5:
                cvUpVip.setEnabled(false);
                cvUpVip.setCardBackgroundColor(getResources().getColor(R.color.bg_bf));
                break;
            //市场经销商
            case 4:
                cvUpVip.setEnabled(false);
                cvUpVip.setCardBackgroundColor(getResources().getColor(R.color.bg_bf));
                break;
            //经销商
            case 3:
                selectVipLevel(2);
                break;
            //VIP
            case 2:
                selectVipLevel(1);
                break;

        }
    }

    /**
     * 选择目标VIP等级
     */
    private void selectVipLevel(int index) {

        llLableOne.setBackgroundResource(R.mipmap.ic_vip_unchecked);
        llLableTwo.setBackgroundResource(R.mipmap.ic_vip_unchecked);
        llLableThree.setBackgroundResource(R.mipmap.ic_vip_unchecked);
        //当前等级大与选中等级的话
        if (mVipLevel > index + 1) {
            cvUpVip.setEnabled(false);
            cvUpVip.setCardBackgroundColor(getResources().getColor(R.color.bg_bf));
        } else {
            cvUpVip.setEnabled(true);
            cvUpVip.setCardBackgroundColor(getResources().getColor(R.color.bg_ffc2a7));
        }
        //下标
        switch (index) {
            //VIP
            case 0:
                mVipLevelSelectIndex = 0;
                llLableOne.setBackgroundResource(R.mipmap.ic_vip_checked);

                break;
            //经销商
            case 1:
                mVipLevelSelectIndex = 1;
                llLableTwo.setBackgroundResource(R.mipmap.ic_vip_checked);
                break;
            //市场经销商
            case 2:
                mVipLevelSelectIndex = 2;
                llLableThree.setBackgroundResource(R.mipmap.ic_vip_checked);
                break;

        }
    }

    @Override
    public void isVipInfoSuccess(List<VipListInfo> data) {
        mVipList = data;
        tvVipNameOne.setText(data.get(0).getLevel_name());
        tvVipNameTwo.setText(data.get(1).getLevel_name());
        tvVipNameThree.setText(data.get(2).getLevel_name());

        tvVipMoneyOne.setText(data.get(0).getLevel_price()+"");
        tvVipMoneyTwo.setText(data.get(1).getLevel_price()+"");
        tvVipMoneyThree.setText(data.get(2).getLevel_price()+"");

    }

    @OnClick({R.id.ll_toolbar_left, R.id.ll_lable_one, R.id.ll_lable_two, R.id.ll_lable_three,
            R.id.cv_up_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_lable_one:
                selectVipLevel(0);
                break;
            case R.id.ll_lable_two:
                selectVipLevel(1);
                break;
            case R.id.ll_lable_three:
                selectVipLevel(2);
                break;
            //升级会员
            case R.id.cv_up_vip:
                showPayView();
                break;
        }
    }

    //======================================== 支付 =======================

    private int mPayType = 2;
    private PopupWindow mPayWindow;

    /**
     * 展示支付弹窗
     */
    private void showPayView() {
        //充值价格
        String recharge_amount = "0";
        String level  = "2";
        String name  = "VIP";
        if (mVipList != null &&mVipList.size()==3) {
            recharge_amount = mVipList.get(mVipLevelSelectIndex).getLevel_price()+"";
            level =mVipList.get(mVipLevelSelectIndex).getLevel_id()+"";
            name =mVipList.get(mVipLevelSelectIndex).getLevel_name()+"";
        }
        View popView = LayoutInflater.from(mContext).inflate(R.layout.pop_map_oil_pay, null);
        ((TextView) popView.findViewById(R.id.pop_tv_title)).setText("久速"+name);
        //根据选中的列表来展示金额
        ((TextView) popView.findViewById(R.id.pop_tv_money)).setText(recharge_amount);
        //记录选中的状态
        if (mPayType == 1) {
            ((RadioButton) popView.findViewById(R.id.pop_rb_alipay)).setChecked(true);
        } else {
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
        //充值金额
        String finalRecharge_amount = recharge_amount;
        //目标等级
        String finalLevel = level;
        popView.findViewById(R.id.pop_tv_pay).setOnClickListener(v -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("recharge_amount", finalRecharge_amount);
            hashMap.put("level_id", finalLevel);
            hashMap.put("buy_vip", "1");
            hashMap.put("pay_code", mPayType == 1 ? "alipayApp" : "appWeixinPay");
            mPresenter.upVip(PacketUtil.getRequestPacket(hashMap),null);
        });
        mPayWindow = PopWindowUtil.getInstance().showPopupWindow(mContext, popView);
        popView.findViewById(R.id.pop_img_close).setOnClickListener(v -> {
            mPayWindow.dismiss();
        });
    }

    /**
     * 支付成功
     */
    private void paySuc() {
        //关闭支付的弹窗 跳转到支付成功页面
        if (mPayWindow != null && mPayWindow.isShowing()) {
            mPayWindow.dismiss();
        }
        View view = View.inflate(mContext, R.layout.pop_up_vip_suc, null);
        TextView tv_finish = view.findViewById(R.id.pop_tv_finish);
        //标题
        TextView tv_title = view.findViewById(R.id.pop_tv_title);
        if (mVipList!=null&&mVipList.size()==3){
            tv_title.setText(mVipList.get(mVipLevelSelectIndex).getLevel_name());
        }
        PopupWindow popupWindow = PopWindowUtil.getInstance().showPopupWindow(mContext, view);
        popupWindow.setOutsideTouchable(true);
        //提示升级成功
        tv_finish.setOnClickListener(v -> {
            popupWindow.dismiss();
            finish();
        });
    }

    //********************************** 支付 **********************************
    @Override
    public void isUpVipSuccess(OrderPayTypeBean data) {
        WxPayUtil wxPayUtil = new WxPayUtil();
        //微信支付
        if (mPayType == 2) {
            wxPayUtil.pay(mContext, data, "vip");
        } else if (mPayType == 1) {
            //支付宝支付
            if (!StringUtil.isEmpty(data.getZfb())) {//支付宝
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
     * 处理微信的支付回调
     *
     * @param loginEventBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEventBean loginEventBean) {
        if (loginEventBean.getLoginStatus() == LoginEventBean.WEIXIN_VIP_PAYSUC) {//全部
            paySuc();
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
                paySuc();
            } else if ("6001".equals(resultStatus)) {
                showToast("支付取消");
            } else {
                showToast("支付失败");
            }
        }
    };


    //=========================================  方法重写

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected UpgradeMemberShipPresenter getPresenter() {
        return new UpgradeMemberShipPresenter(this);
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

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}

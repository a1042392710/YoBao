package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 提现
 * @author: create by chenhao on 2019/4/1
 */
public class JiuSuWithdrawDepositActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_withdraw_deposit_number)
    TextView etWithdrawDepositNumber;
    @BindView(R.id.tv_all_withdraw_deposit)
    TextView tvAllWithdrawDeposit;
    @BindView(R.id.rb_wechat)
    RadioButton rbWechat;
    @BindView(R.id.ll_wechat)
    LinearLayout llWechat;
    @BindView(R.id.rb_alipay)
    RadioButton rbAlipay;
    @BindView(R.id.ll_alipay)
    LinearLayout llAlipay;
    @BindView(R.id.tv_withdraw_deposit)
    TextView tvWithdrawDeposit;
    @BindView(R.id.tv_apply_commisson)
    TextView tvApplyCommisson;

    /**
     * 支付方式  0 微信  1 支付宝  3银行卡
     */
    private int mPayType = 0;


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_withdraw_deposit;
    }

    /**
     * 可提现佣金
     */
    private String mApplyCommissions;

    /**
     * 全部佣金
     */
    private String mMoneyTotal;
    @Override
    protected void initView() {
        tvToolbarTitle.setText("提现");
        tvToolbarRight.setText("提现记录");
        mApplyCommissions=  getIntent().getStringExtra("apply_commissions");
        mMoneyTotal=  getIntent().getStringExtra("total_commissions");
        etWithdrawDepositNumber.setText(mApplyCommissions);
        tvApplyCommisson.setText("全部佣金 ￥"+mMoneyTotal+" , 可提现佣金 ￥"+mApplyCommissions+"");
    }



    @OnClick({R.id.tv_all_withdraw_deposit, R.id.tv_withdraw_deposit, R.id.ll_toolbar_left, R.id.ll_wechat, R.id.ll_alipay, R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //全部提现
            case R.id.tv_all_withdraw_deposit:
//                etWithdrawDepositNumber.setText(mApplyCommissions+"");
//                etWithdrawDepositNumber.setSelection(etWithdrawDepositNumber.getText().toString().length());
                break;
            //立即提现
            case R.id.tv_withdraw_deposit:
                if (mApplyCommissions==null||"0".equals(mApplyCommissions)){
                    showToast("您当前无佣金可提现");
                    return;
                }
                startActivity(new Intent(mContext, JiuSuWithdrawDepositApplicationActivity.class).putExtra("money",etWithdrawDepositNumber.getText().toString().trim()));
                break;
            //提现记录
            case R.id.tv_toolbar_right:
                startActivity(new Intent(mContext, JiuSuWithdrawDepositListActivity.class));
                break;
            case R.id.ll_toolbar_left:
                finish();
                break;
            //选中微信
            case R.id.ll_wechat:
//                selectPayType(0);
                break;
            //选中支付宝
            case R.id.ll_alipay:
//                selectPayType(1);
                break;
        }
    }
    /**
     * 选择支付方式
     * @param payType  0 微信  1 支付宝
     */
    private void selectPayType(int payType) {
        mPayType = payType;
        rbWechat.setChecked(false);
        rbAlipay.setChecked(false);
        if (payType==0){
            rbWechat.setChecked(true);
        }else{
            rbAlipay.setChecked(true);
        }
    }

    @Override
    public void showLoading() {}

    @Override
    public void stopLoading() {

    }
}

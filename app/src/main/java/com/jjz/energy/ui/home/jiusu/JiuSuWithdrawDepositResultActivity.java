package com.jjz.energy.ui.home.jiusu;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 提现结果返回页面
 * @author: create by chenhao on 2019/4/1
 */
public class JiuSuWithdrawDepositResultActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_text_one)
    TextView tvTextOne;
    @BindView(R.id.tv_account_number)
    TextView tvAccountNumber;
    @BindView(R.id.tv_text_two)
    TextView tvTextTwo;
    @BindView(R.id.tv_service_charge)
    TextView tvServiceCharge;
    @BindView(R.id.tv_text_three)
    TextView tvTextThree;
    @BindView(R.id.tv_withdraw_deposit_money)
    TextView tvWithdrawDepositMoney;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.tv_real_money)
    TextView tvRealMoney;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_withdraw_deposit_result;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("余额提现");
        tvAccountNumber.setText(getIntent().getStringExtra("account"));
        tvWithdrawDepositMoney.setText("￥"+getIntent().getStringExtra("money"));
        tvRealMoney.setText("￥"+getIntent().getStringExtra("real_money"));
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //完成
            case R.id.tv_finish:
                //关闭申请页面和提现页面
                ActivityUtils.finishActivity(JiuSuWithdrawDepositActivity.class);
                ActivityUtils.finishActivity(JiuSuWithdrawDepositApplicationActivity.class);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //关闭申请页面和提现页面
            ActivityUtils.finishActivity(JiuSuWithdrawDepositActivity.class);
            ActivityUtils.finishActivity(JiuSuWithdrawDepositApplicationActivity.class);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}

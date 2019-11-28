package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features:
 * @author: create by chenhao on 2019/4/3
 */
public class JiuSuPaySucActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_pay_account)
    TextView tvPayAccount;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_finish)
    TextView tvFinish;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_pay_suc;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("支付详情");
        String money = getIntent().getStringExtra("money");
        int pay_type = getIntent().getIntExtra("pay_type", 2);
        tvPayMoney.setText("￥" + money);
        if (pay_type == 3) {
            tvPayType.setText("赠品领取");
        } else {
            tvPayType.setText(pay_type == 1 ? "支付宝" : "微信");
        }

    }



    @OnClick({R.id.ll_toolbar_left, R.id.tv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_finish:
                //买油
                startActivity(new Intent(mContext, JiuSuMineOrderActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}

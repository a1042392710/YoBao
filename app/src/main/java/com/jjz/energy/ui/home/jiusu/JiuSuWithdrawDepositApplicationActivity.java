package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu.WithdrawInfoBean;
import com.jjz.energy.presenter.jiusu.MineWalletPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IMineWalletView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 输出提现账号申请提现
 * @author: create by chenhao on 2019/4/1
 */
public class JiuSuWithdrawDepositApplicationActivity extends BaseActivity<MineWalletPresenter> implements IMineWalletView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_withdraw_deposit_number)
    TextView tvWithdrawDepositNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_account_type)
    TextView tvAccountType;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_warm_prompt)
    TextView tvWarmPrompt;
    @BindView(R.id.tv_id_card)
    TextView tvIdCard;
    @BindView(R.id.et_id_card)
    EditText etIdCard;
    @BindView(R.id.tv_bank_crad)
    TextView tvBankCrad;
    @BindView(R.id.et_bank_crad)
    EditText etBankCrad;
    @BindView(R.id.et_bank_phone)
    EditText etBankPhone;
    @BindView(R.id.et_bank_name)
    EditText etBankName;



    @Override
    protected void initView() {
        tvToolbarTitle.setText("输入提现信息");
        tvWithdrawDepositNumber.setText(getIntent().getStringExtra("money"));
        mPresenter.getWithdrawInfo(PacketUtil.getRequestPacket(null));
    }


    @Override
    public void isGetWithdrawSuc(WithdrawInfoBean data) {
        //赋值
        etBankCrad.setText(data.getBank_card());
        etBankName.setText(data.getBank_name());
        etBankPhone.setText(data.getBank_phone());
    }

    @Override
    public void isPutWithdrawSuccess(String data) {
        showToast("申请成功");
        startActivity(new Intent(mContext, JiuSuWithdrawDepositResultActivity.class).putExtra("money",tvWithdrawDepositNumber.getText().toString())
                .putExtra("account",etBankCrad.getText().toString())
                .putExtra("real_money",data));
        finish();
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;

            //确定提现
            case R.id.tv_submit:
                if (StringUtil.isEmpty(etBankName.getText().toString())) {
                    showToast("请输入银行名称");
                    return;
                }
                if (StringUtil.isEmpty(etBankCrad.getText().toString())) {
                    showToast("请输入银行卡号");
                    return;
                }
                if (StringUtil.isEmpty(etBankPhone.getText().toString())) {
                    showToast("请输入银行预留手机号");
                    return;
                }
                HashMap<String, String> map = new HashMap<>();
                map.put("bank_name", etBankName.getText().toString().trim());
                map.put("bank_card", etBankCrad.getText().toString().trim());
                map.put("bank_phone", etBankPhone.getText().toString().trim());
                map.put("type", "3");//1 支付宝  2微信 3银行卡
                mPresenter.putWithdrawInfo(PacketUtil.getRequestPacket(map));
                break;
        }
    }

    @Override
    protected MineWalletPresenter getPresenter() {
        return new MineWalletPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_withdraw_deposit_application;
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


}

package com.jjz.energy.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.click.SafeClickListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 手机号登陆
 * @author: create by chenhao on 2019/3/28
 */
public class PhoneLoginActivity extends BaseActivity {
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_phone_login;
    }

    @Override
    protected void initView() {
        //弹起软键盘
        showSoftInputFromWindow(etPhone);
        //手机号码位数验证后启用下一步按钮
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtil.isMobileNO(etPhone.getText().toString())) {
                    tvNext.setEnabled(true);
                }else{
                    tvNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvNext.setOnClickListener(new SafeClickListener() {
            @Override
            public void safeClick(View view) {
                if (StringUtil.isEmpty(etPhone.getText().toString())) {
                    showToast("手机号不能为空");
                    return;
                }
                if (!StringUtil.isMobileNO(etPhone.getText().toString())) {
                    showToast("手机号格式不正确");
                    return;
                }
                if(etPhone.getText().toString().equals("13812345678")){
                    //测试通道
                    startActivity(new Intent(mContext, PasswordLoginActivity.class)
                            .putExtra("phone_number", etPhone.getText().toString()));
                }else{
                    startActivity(new Intent(mContext, GetVCodeActivity.class)
                            .putExtra("phone_number", etPhone.getText().toString()));
                }

            }
        });

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;

        }
    }
}

package com.jjz.energy.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ author CH
 * @ date  2019/8/6  10:51
 * @ fuction  验证手机号码
 */
public class LoginVerifyMobileNumberActivity extends BaseActivity {


    //输入手机号码
    @BindView(R.id.et_verify_mobile)
    EditText etVerifyMobile;
    //确定
    @BindView(R.id.tv_verify_mobile_number_sure)
    TextView tvVerifyMobileNumberSure;
    @BindView(R.id.img_close)
    ImageView imgClose;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_login_verify_mobile_number;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick({R.id.img_close, R.id.tv_verify_mobile_number_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            //确定提交
            case R.id.tv_verify_mobile_number_sure:
                Intent intent = new Intent(mContext, LoginInputCodeActivity.class);
                intent.putExtra("mobile", etVerifyMobile.getText().toString().trim());
                intent.putExtra("scene", 2);
                //获取验证码页面
                startActivity(intent);
                break;
        }
    }

}

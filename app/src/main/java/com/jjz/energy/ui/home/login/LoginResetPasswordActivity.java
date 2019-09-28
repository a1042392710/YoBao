package com.jjz.energy.ui.home.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.presenter.login.LoginResetPasswordPresenter;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.login.ILoginResetPasswordView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ author CH
 * @ date  2018/12/14  11:07
 * @ fuction  重置密码
 */
public class LoginResetPasswordActivity extends BaseActivity<LoginResetPasswordPresenter> implements ILoginResetPasswordView {


    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.et_reset_password_input_new_pw)
    EditText etResetPasswordInputNewPw;
    @BindView(R.id.et_reset_password_again_input_new_pw)
    EditText etResetPasswordAgainInputNewPw;
    @BindView(R.id.tv_reset_password_login)
    TextView tvResetPasswordLogin;
    /**
     * 上个页面传来的验证码
     */
    private String mCode ;
    /**
     * 上个页面传来的手机号
     */
    private String mMobile;
    @Override
    protected LoginResetPasswordPresenter getPresenter() {
        return new LoginResetPasswordPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_login_reset_password;
    }

    @Override
    protected void initView() {
        mCode = getIntent().getStringExtra("code");
        mMobile = getIntent().getStringExtra("mobile");
    }


    @OnClick({R.id.img_close, R.id.tv_reset_password_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            //确定
            case R.id.tv_reset_password_login:
                //回到登录页面
                String password = etResetPasswordInputNewPw.getText().toString();//新密码
                String new_password = etResetPasswordAgainInputNewPw.getText().toString();//再次输入密码
                if (StringUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
                if (password.length() < 6 || password.length() > 16) {
                    showToast("密码为6–16位，数字或密码组成");
                    return;
                }
                if (!password.equals(new_password)) {
                    showToast("密码不一致，请重新输入");
                    return;
                }
                submit();
                break;
        }
    }

    /**
     * 重置密码 提交数据
     */
    private void submit() {
        long time  = System.currentTimeMillis();
        Map<String,String> map = new HashMap<>();
        //新密码
        String newPassword = etResetPasswordInputNewPw.getText().toString().trim();
        map.put("new_password", Utils.MD5Encode(Constant.APP_KEY+newPassword));
        //验证码
        map.put("code", mCode);
        //手机号
        map.put("mobile", mMobile);
        //时间戳
        map.put("timestamp", time+"");
        mPresenter.resetPassword(PacketUtil.getRequestPacket(map));
    }

    @Override
    public void isSuccess(UserInfo loginBean) {
        showToast("成功重置密码");
        //清空栈 再跳转首页
        startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void isFail(String msg ,boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

}

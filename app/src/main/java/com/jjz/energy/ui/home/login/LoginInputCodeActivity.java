package com.jjz.energy.ui.home.login;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.presenter.login.LoginInputCodePresenter;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.login.ILoginInputCodeView;
import com.jjz.energy.widgets.SeparatedEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @ author CH
 * @ date  2019/9/14  14:29
 * @ fuction  输入验证码
 */
public class LoginInputCodeActivity extends BaseActivity<LoginInputCodePresenter>implements ILoginInputCodeView {

    @BindView(R.id.view_status)
    View viewStatus;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.tv_login_input_code_phone)
    TextView tvLoginInputCodePhone;
    @BindView(R.id.login_input_code_underline)
    SeparatedEditText loginInputCodeUnderline;
    @BindView(R.id.tv_login_input_code_sure)
    TextView tvLoginInputCodeSure;
    @BindView(R.id.tv_login_input_code_get)
    TextView tvLoginInputCodeGet;
    /**
     * 手机号
     */
    private String mMobile;

    /**
     * 管理订阅
     */
    private Disposable mDisposable;

    /**
     * 发送场景
     * 1 登录，2 找回密码
     */
    private int scene = 1;

    /**
     * 记录 提交的验证码
     */
    private String mCode;

    @Override
    protected LoginInputCodePresenter getPresenter() {
        return new LoginInputCodePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_home_login_input_code;
    }

    @Override
    protected void initView() {
        //电话
        mMobile = getIntent().getStringExtra("mobile");
        //验证码的类型
        scene = getIntent().getIntExtra("scene", 1);
        tvLoginInputCodePhone.setText(mMobile);
        //获取验证码
        getVCode();
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @OnClick({R.id.img_close, R.id.tv_login_input_code_sure, R.id.tv_login_input_code_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            //登录
            case R.id.tv_login_input_code_sure:
                //验证码不为空并且长度为4
                String code = loginInputCodeUnderline.getText().toString().trim();
                if (!StringUtils.isEmpty(code) && code.length() == 4) {
                    //登录验证
                    if (scene == 1) {
                        loginVCode();
                    } else {
                        //忘记密码的验证
                        forgotPassword();
                    }

                } else {
                    showToast("请输入验证码");
                }

                break;
            //获取验证码
            case R.id.tv_login_input_code_get:
                getVCode();
                break;
        }
    }
    /**
     * 获取验证码
     */
    private void getVCode(){
        //禁止点击
        tvLoginInputCodeGet.setEnabled(false);
        Map<String,String> map = new HashMap<>();
        map.put("scene", scene+"");
        map.put("mobile", mMobile);
        mPresenter.getVCode(PacketUtil.getRequestPacket(map));
    }

    /**
     * 带验证码登录
     */
    private void loginVCode(){
        Map<String,String> map = new HashMap<>();
        map.put("code",loginInputCodeUnderline.getText().toString().trim());
        map.put("mobile", mMobile);
        mPresenter.loginVCode(PacketUtil.getRequestPacket(map));
    }

    /**
     * 忘记密码之提交验证码
     */
    private void forgotPassword(){
        mCode = loginInputCodeUnderline.getText().toString().trim();
        Map<String,String> map = new HashMap<>();
        map.put("code",mCode);
        map.put("mobile", mMobile);
        mPresenter.forgotPasswordPutVCode(PacketUtil.getRequestPacket(map));
    }


    /**
     * 验证码登录成功
     */
    @Override
    public void loginVCodeSuc(UserInfo userInfo) {
        loginSuc(userInfo);
    }

    /**
     * 获取验证码失败
     */
    @Override
    public void getAuthCodeFail(String msg) {
        tvLoginInputCodeGet.setEnabled(true);
        showToast(msg);
    }

    /**
     * 验证码登录失败
     */
    @Override
    public void loginVCodeFail(String msg) {
        showToast(msg);
    }

    /**
     * 忘记密码 提交验证码 成功
     */
    @Override
    public void forgotPasswordSuc(UserInfo loginBean) {
        showToast("验证成功，请重置密码");
        startActivity(new Intent(mContext,LoginResetPasswordActivity.class).putExtra("code", mCode).putExtra("mobile",mMobile ));
    }
    /**
     * 忘记密码 提交验证码 失败
     */
    @Override
    public void forgotPasswordFail(String msg) {
        showToast(msg);
    }

    /**
     * 获取验证码成功
     */
    @Override
    public void getAuthCodeSuc(String loginBean) {
        //请求成功 开始倒计时
        mDisposable = Observable.interval(0L, 1L, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(aLong -> {
            //剩余秒数
            long offset = 60 - aLong;
            if (offset <= 0) {
                //取消
                mDisposable.dispose();
                if (null!=tvLoginInputCodeGet) {
                    tvLoginInputCodeGet.setText("重新发送");
                    tvLoginInputCodeGet.setEnabled(true);
                }
            }else {
                if (null != tvLoginInputCodeGet) {
                    tvLoginInputCodeGet.setText("重新发送（"+String.valueOf(offset) + "秒）");
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏，页面销毁时关闭DIsposable
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

    }

}

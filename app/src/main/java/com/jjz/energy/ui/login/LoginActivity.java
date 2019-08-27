package com.jjz.energy.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.presenter.LoginPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.ILoginView;
import com.jude.swipbackhelper.SwipeBackHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ author CH
 * @ date  2018/12/14  14:26
 * @ fuction  登录
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {

    //手机号输入框
    @BindView(R.id.et_home_login_mobile)
    EditText etHomeLoginMobile;
    //密码输入框
    @BindView(R.id.et_home_login_password)
    EditText etHomeLoginPassword;
    //登录
    @BindView(R.id.tv_home_login_login)
    TextView tvHomeLoginLogin;
    //忘记密码
    @BindView(R.id.tv_home_login_forgot_password)
    TextView tvHomeLoginForgotPassword;
    //密码登录
    @BindView(R.id.tv_home_login_password_login)
    TextView tvHomeLoginPasswordLogin;
    //验证码登录
    @BindView(R.id.tv_home_login_auth_code_login)
    TextView tvHomeLoginAuthCodeLogin;
    //微信登录
    @BindView(R.id.img_login_way_wx)
    ImageView imgLoginWayWx;
    //QQ登录
    @BindView(R.id.img_login_way_qq)
    ImageView imgLoginWayQq;
    //华源隐私政策
    @BindView(R.id.tv_login_policy)
    TextView tvLoginPolicy;
    //登录提示
    @BindView(R.id.tv_login_toast)
    TextView tvLoginToast;

    /**
     * 是否是验证码登录 默认验证码登录
     */
    private boolean isCodeLogin = true;

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_home_login;
    }

    @Override
    protected void initView() {
        //取消滑动关闭
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        //初始化监听器
        initListener();
    }


    /**
     * 初始化监听器
     */
    private void initListener() {
        //监听手机号码输入框
        etHomeLoginMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果是验证码登录
                if (isCodeLogin) {
                    if (s.toString().length() == 11) {
                        tvHomeLoginLogin.setEnabled(true);
                    } else {
                        tvHomeLoginLogin.setEnabled(false);
                    }
                } else {
                    //如果是密码登录 需要手机号为11和密码不为空
                    if (s.toString().length() == 11 && etHomeLoginPassword.getText().toString().length() > 0) {
                        tvHomeLoginLogin.setEnabled(true);
                    } else {
                        tvHomeLoginLogin.setEnabled(false);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //密码框监听
        etHomeLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果是密码登录 需要手机号为11位和密码不为空
                if (etHomeLoginMobile.getText().toString().length() == 11 && s.toString().length() > 0) {
                    tvHomeLoginLogin.setEnabled(true);
                } else {
                    tvHomeLoginLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    //监听返回按钮
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R.id.tv_home_login_forgot_password, R.id.tv_home_login_auth_code_login, R.id.img_close,
            R.id.tv_login_policy, R.id.tv_home_login_login, R.id.img_login_way_wx, R.id.img_login_way_qq,
            R.id.tv_home_login_password_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //忘记密码
            case R.id.tv_home_login_forgot_password:
                //验证手机号码
                startActivity(new Intent(mContext,LoginVerifyMobileNumberActivity.class));
                break;
            //验证码登录按钮
            case R.id.tv_home_login_auth_code_login:
                //切换登录状态
                changeLoginType("验证码");
                break;
            //密码登录按钮
            case R.id.tv_home_login_password_login:
                //切换登录状态
                changeLoginType("密码");
                break;
            //Yo宝隐私政策
            case R.id.tv_login_policy:
                break;
                //登录
            case R.id.tv_home_login_login:
                //验证码登录 跳转到验证码页面
                if (isCodeLogin){
                    startActivity(new Intent(mContext,LoginInputCodeActivity.class).putExtra("mobile",
                            etHomeLoginMobile.getText().toString().trim()).putExtra("scene", "1"));
                }else{
                    //密码登录 直接验证账号密码
//                    initLoginInterFace(etHomeLoginMobile.getText().toString().trim(), etHomeLoginPassword.getText().toString().trim());
                    finish();
                }
                break;
//                //微信登录
//            case R.id.img_login_way_wx:
//                WechatLogin();
//                break;
//                //QQ登录
//            case R.id.img_login_way_qq:
//                qqLogin();
//                break;
//                //退出
            case R.id.img_close:
                setResult(0);
                finish();
                break;
        }
    }

    //================================== 辅助方法

    /**
     * 登录接口
     *
     * @param username  手机号
     * @param password  密码
     */
    private void initLoginInterFace(String username, String password) {
        long time = System.currentTimeMillis(); //当前时间
        String passwordValue = Utils.MD5Encode(Utils.MD5Encode(Utils.MD5Encode(password) + time) + username);//MD5加密后的密码
        AtomicReference<Map<String, String>> hashMap = new AtomicReference<>(new HashMap<>());
        hashMap.get().put("username", username);
        hashMap.get().put("password", passwordValue);
        hashMap.get().put("timestamp", time+"");
        mPresenter.getLoginDate(PacketUtil.getRequestPacket(hashMap));

    }

    /**
     * 切换登录状方式
     */
    private void changeLoginType(String type){
        //验证码登录
        if ("验证码".equals(type)){
            isCodeLogin= true;
            tvHomeLoginLogin.setText("获取验证码");
            //显示登录提示
            tvLoginToast.setVisibility(View.VISIBLE);
            //隐藏密码框
            etHomeLoginPassword.setVisibility(View.GONE);
            //隐藏忘记密码
            tvHomeLoginForgotPassword.setVisibility(View.GONE);
            //显示密码登录
            tvHomeLoginPasswordLogin.setVisibility(View.VISIBLE);
            //隐藏验证码登录按钮
            tvHomeLoginAuthCodeLogin.setVisibility(View.GONE);
            //设置按钮的禁用和启用
            if (StringUtil.isMobile(etHomeLoginMobile.getText().toString())){
                //手机号长度合格，就启用
                tvHomeLoginLogin.setEnabled(true);
            }else{
                tvHomeLoginLogin.setEnabled(false);
            }
        }else{
            //密码登录
            isCodeLogin= false;
            tvHomeLoginLogin.setText("登录");
            //关闭登录提示
            tvLoginToast.setVisibility(View.INVISIBLE);
            //显示密码框
            etHomeLoginPassword.setVisibility(View.VISIBLE);
            //显示忘记密码
            tvHomeLoginForgotPassword.setVisibility(View.VISIBLE);
            //显示验证码登录
            tvHomeLoginAuthCodeLogin.setVisibility(View.VISIBLE);
            //隐藏密码登录
            tvHomeLoginPasswordLogin.setVisibility(View.GONE);
            //设置按钮的禁用和启用
            if (StringUtil.isMobile(etHomeLoginMobile.getText().toString())&& !StringUtils.isEmpty(etHomeLoginPassword.getText().toString())){
                //手机号长度合格并且密码不为空，就启用
                tvHomeLoginLogin.setEnabled(true);
            }else{
                tvHomeLoginLogin.setEnabled(false);
            }
        }
    }




    //================================================ 方法重写

    @Override
    public void getLoginFail(String msg) {
        showToast(msg);
    }

    //登录
    @Override
    public void getLoginSuc(LoginBean data) {
        saveLoginData(data);
    }

    /**
     * 保存登录信息
     * @param data
     */
    private void saveLoginData(LoginBean data){
        //登录成功，保存用户信息
        showToast("登录成功");
        loginSuc(data);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exit();
    }

    @Override
    public void showLoading() {startProgressDialog();}

    @Override
    public void stopLoading() {stopProgressDialog();}


}

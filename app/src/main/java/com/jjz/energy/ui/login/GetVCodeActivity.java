package com.jjz.energy.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.BaseWebActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.entry.MainEvent;
import com.jjz.energy.presenter.GetCodePresenter;
import com.jjz.energy.util.AesUtils;
import com.jjz.energy.util.SpUtil;
import com.jjz.energy.util.click.SafeClickListener;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.view.IGetCodeView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @Features: 获取验证码页面
 * @author: create by chenhao on 2019/3/28
 */
public class GetVCodeActivity extends BaseActivity<GetCodePresenter> implements IGetCodeView {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.et_vcode)
    EditText etVcode;
    @BindView(R.id.tv_resend)
    TextView tvResend;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_privacy_agreement)
    TextView tvPrivacyAgreement;

    /**
     * 电话号码
     */
    private String mPhoneNumStr;

    @Override
    protected GetCodePresenter getPresenter() {
        return new GetCodePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_get_vcode;
    }

    @Override
    protected void initView() {
        //弹起软键盘
        showSoftInputFromWindow(etVcode);
        //输入电话号码
        mPhoneNumStr = getIntent().getStringExtra("phone_number");
        tvPhoneNumber.setText(mPhoneNumStr);
        //获取验证码
        getCode();
        initListener();
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        HashMap<String,String> map = new HashMap<>();
        map.put("mobile",mPhoneNumStr);
        //使用场景，scene 1 登录注册
        map.put("scene","1");
        mPresenter.getVCode(PacketUtil.getRequestPacket(map));
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //手机号码位数验证后启用下一步按钮
        etVcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==4) {
                    tvLogin.setEnabled(true);
                }else{
                    tvLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //跳转首页
        tvLogin.setOnClickListener(new SafeClickListener() {
            @Override
            public void safeClick(View view) {
                login();
            }
        });
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    /**
     * 管理RxJava的订阅
     */
    private Disposable mDisposable;

    @OnClick({R.id.img_back, R.id.tv_resend, R.id.tv_privacy_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            //重新发送验证码
            case R.id.tv_resend:
                getCode();
                break;
            //隐私政策
            case R.id.tv_privacy_agreement:
                startActivity(new Intent(mContext, BaseWebActivity.class).putExtra
                        (BaseWebActivity.WEB_TITLE, "金玖洲隐私政策").putExtra(BaseWebActivity.WEB_URL,
                        Constant.PRIVACY_POLICY_URL));
                break;
        }
    }

    /**
     * 登录
     */
    private void login(){
        HashMap<String ,String> map = new HashMap<>();
        map.put("mobile",mPhoneNumStr);
        map.put("code",etVcode.getText().toString());
        mPresenter.login(PacketUtil.getRequestPacket(map));
    }


    /**
     * 设置指定位置的字体颜色
     * @param aLong
     */
    private void setTextColor(Long aLong) {
        String toast = "没有收到验证码？" + (60 - aLong) + "s  以后可重新发送";
        tvResend.setTextColor(getResources().getColor(R.color.text_black75));
        //先构造SpannableString
        SpannableString spanString = new SpannableString(toast);
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.text_green_9ace32));
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, toast.indexOf("？")+1, toast.indexOf("s")+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给EditText显示出来
        tvResend.setText(spanString);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }


    @Override
    public void isLoginSuccess(LoginBean loginBean) {
        //解密token
        String decode_token = AesUtils.decrypt(loginBean.getToken(), AesUtils.KEY, AesUtils.IV);
        //去除时间戳
        decode_token= decode_token.substring(0,decode_token.length()-loginBean.getTime().length());
        //存储用户Toke
        SpUtil.init(BaseApplication.getAppContext()).commit(Constant.LOGIN_ID, decode_token);
        //存储用户信息
        UserLoginBiz.getInstance(BaseApplication.getAppContext()).loginSuccess(loginBean);
        //用户为非会员并且未填写推荐码时，发送通知，提醒弹出推荐码的窗口
        if (loginBean.getLevel_id()==1&&loginBean.getIsolate()==1){
            EventBus.getDefault().post(new LoginEventBean(LoginEventBean.SHOW_TUIJIAN));
        }
        //跳转首页
        EventBus.getDefault().post(new MainEvent(MainEvent.GO_MINE));
        ActivityUtils.finishActivity(PhoneLoginActivity.class);
        finish();
    }

    @Override
    public void isGetCodeSuccess(String data) {
        //点击后置为不可点击状态
        tvResend.setEnabled(false);
        //从0开始发射11个数字为：0-10依次输出，延时0s执行，每1s发射一次。
        mDisposable = Flowable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> setTextColor(aLong))
                .doOnComplete(() -> {
                    //倒计时完毕置为可点击状态
                    tvResend.setEnabled(true);
                    tvResend.setTextColor(getResources().getColor(R.color
                            .text_green_9ace32));
                    tvResend.setText("重新发送");
                })
                .subscribe();
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }
}

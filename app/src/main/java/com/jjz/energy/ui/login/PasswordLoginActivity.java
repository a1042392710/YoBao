package com.jjz.energy.ui.login;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.Constant;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.entry.MainEvent;
import com.jjz.energy.presenter.GetCodePresenter;
import com.jjz.energy.util.AesUtils;
import com.jjz.energy.util.SpUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.view.IGetCodeView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 密码登录
 * @author: create by chenhao on 2019/4/24
 */
public class PasswordLoginActivity extends BaseActivity<GetCodePresenter>implements IGetCodeView {

    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_next)
    TextView tvNext;

    private  String mPhoneNumStr;

    @Override
    protected GetCodePresenter getPresenter() {
        return new GetCodePresenter(this);
    }

    @Override
    protected void initView() {
        mPhoneNumStr = getIntent().getStringExtra("phone_number");
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
    protected int layoutId() {
        return R.layout.act_password_login;
    }

    @OnClick({R.id.img_close, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.tv_next:
                //登录
                HashMap<String,String> map = new HashMap<>();
                map.put("mobile",mPhoneNumStr);
                map.put("password", Utils.MD5Encode(etPhone.getText().toString()));
                mPresenter.pwdLogin(PacketUtil.getRequestPacket(map));
                break;
        }
    }

    @Override
    public void isPwdLoginSuccess(LoginBean loginBean) {
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
    public void isFail(String msg) {
        showToast(msg);
    }
}

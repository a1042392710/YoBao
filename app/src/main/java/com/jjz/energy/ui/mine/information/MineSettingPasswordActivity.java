package com.jjz.energy.ui.mine.information;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.blankj.utilcode.util.StringUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.presenter.login.LoginResetPasswordPresenter;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.login.ILoginResetPasswordView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 设置登录密码
 * @author: create by chenhao on 2019/8/8
 */
public class MineSettingPasswordActivity extends BaseActivity<LoginResetPasswordPresenter>implements ILoginResetPasswordView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_input_one)
    EditText etInputOne;
    @BindView(R.id.ll_input_one)
    LinearLayout llInputOne;
    @BindView(R.id.et_input_two)
    EditText etInputTwo;
    @BindView(R.id.et_input_three)
    EditText etInputThree;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    /**
     * 是否是修改密码
     */
    private boolean isMotify = false;

    @Override
    protected LoginResetPasswordPresenter getPresenter() {
        return new LoginResetPasswordPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_setting_password;
    }

    @Override
    protected void initView() {
        isMotify = getIntent().getBooleanExtra("isMotify", false);
        if (isMotify) {
            tvToolbarTitle.setText("修改登录密码");
            llInputOne.setVisibility(View.VISIBLE);
            etInputTwo.setHint("请输入新密码");
            etInputThree.setHint("请再次输入新密码");
        } else {
            tvToolbarTitle.setText("设置登录密码");
        }

    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //提交
            case R.id.tv_submit:
                if (!isMotify) {
                    //设置新密码
                    String login_password = etInputTwo.getText().toString();

                    if (login_password.length() < 6 || login_password.length() > 16) {
                        showToast("密码为6–16位，数字或密码组成");
                        return;
                    }
                    if (!login_password.equals(etInputThree.getText().toString())){
                        showToast("两次输入密码不一致");
                        return;
                    }
                    //初始化设置登录密码接口
                    initLoginPassWordInterFace("", login_password);
                } else {
                    String old_password = etInputOne.getText().toString();//旧密码
                    String new_password = etInputTwo.getText().toString();//原密码
                    String sure_password = etInputThree.getText().toString();//确认密码
                    if (StringUtils.isEmpty(old_password)) {
                        showToast("原密码不能为空");
                        return;
                    }
                    if (StringUtils.isEmpty(new_password)) {
                        showToast("新密码不能为空");
                        return;
                    }
                    if (new_password.length() < 6 || new_password.length() > 16) {
                        showToast("密码为6–16位，数字或密码组成");
                        return;
                    }
                    if (StringUtils.isEmpty(sure_password)) {
                        showToast("确认密码不能为空");
                        return;
                    }
                    if (!sure_password.equals(new_password)) {
                        showToast("密码不一致");
                        return;
                    }
                    //初始化修改登录密码接口
                    initLoginPassWordInterFace(old_password, new_password);
                }
                break;
        }
    }

    /**
     * 设置/修改登录密码接口
     *
     * @param old_password 原密码
     * @param new_password 新密码
     */
    private void initLoginPassWordInterFace(String old_password, String new_password) {
        //原密码加密
        String passwordValue = Utils.MD5Encode(Constant.APP_KEY+old_password);
        //新密码加密
        String new_pass = Utils.MD5Encode(Constant.APP_KEY+new_password);
        LogUtil.e("加密密码",new_pass);
        AtomicReference<Map<String, String>> hashMap = new AtomicReference<>(new HashMap<>());
        if (!StringUtils.isEmpty(old_password)) {
            //原密码
            hashMap.get().put("old_password", passwordValue);
        }else{

        }
        hashMap.get().put("new_password", new_pass);//新密码
        hashMap.get().put("confirm_password", new_pass);//新密码
        mPresenter.settingPassword(PacketUtil.getRequestPacket(hashMap));
    }

    @Override
    public void isSettingPwSuccess(String msg) {
        if (!isMotify) {
            showToast("密码设置成功");
        } else {
            showToast("密码修改成功");
        }
        finish();
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
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

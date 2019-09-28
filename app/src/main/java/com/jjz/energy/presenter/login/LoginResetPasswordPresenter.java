package com.jjz.energy.presenter.login;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.model.login.LoginResetPasswordModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.login.ILoginResetPasswordView;

/**
 * @author CH
 * @fuction 忘记密码 重置密码
 */
public class LoginResetPasswordPresenter extends BasePresenter<LoginResetPasswordModel, ILoginResetPasswordView> {


    public LoginResetPasswordPresenter(ILoginResetPasswordView view) {
        initPresenter(view);
    }

    /**
     * 重置密码
     */
    @SuppressLint("CheckResult")
    public void resetPassword(String data) {

        addSubscribe(mModel.resetPassword(data)
                .subscribeWith(new CommonSubscriber<UserInfo>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(UserInfo registerBean) {
                        mView.stopLoading();
                        mView.isSuccess(registerBean);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));
    }

    /**
     * 设置新密码/修改密码
     */
    @SuppressLint("CheckResult")
    public void settingPassword(String data) {

        addSubscribe(mModel.settingPassword(data)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String registerBean) {
                        mView.stopLoading();
                        mView.isSettingPwSuccess(registerBean);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));
    }
    @Override
    protected LoginResetPasswordModel createModel() {
        return new LoginResetPasswordModel();
    }
}

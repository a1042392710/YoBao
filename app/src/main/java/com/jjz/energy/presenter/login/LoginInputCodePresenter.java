package com.jjz.energy.presenter.login;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.model.login.LoginInputCodeModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.login.ILoginInputCodeView;

/**
 * @ author CH
 * @ fuction 获取验证码
 */
public class LoginInputCodePresenter extends BasePresenter<LoginInputCodeModel, ILoginInputCodeView> {


    public LoginInputCodePresenter(ILoginInputCodeView view) {
        initPresenter(view);
    }

    /**
     * 获取验证码
     */
    @SuppressLint("CheckResult")
    public void getVCode(String data) {

        addSubscribe(mModel.requestAuthCode(data)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String loginBean) {
                        mView.stopLoading();
                        mView.getAuthCodeSuc(loginBean);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.getAuthCodeFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 验证码登录
     */
    @SuppressLint("CheckResult")
    public void loginVCode(String data) {

        addSubscribe(mModel.loginVCode(data)
                .subscribeWith(new CommonSubscriber<UserInfo>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(UserInfo loginBean) {
                        mView.stopLoading();
                        mView.loginVCodeSuc(loginBean);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.loginVCodeFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 忘记密码  提交验证码
     */
    @SuppressLint("CheckResult")
    public void forgotPasswordPutVCode(String data) {

        addSubscribe(mModel.forgotPasswordPutVCode(data)
                .subscribeWith(new CommonSubscriber<UserInfo>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(UserInfo loginBean) {
                        mView.stopLoading();
                        mView.forgotPasswordSuc(loginBean);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.loginVCodeFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }



    @Override
    protected LoginInputCodeModel createModel() {
        return new LoginInputCodeModel();
    }
}

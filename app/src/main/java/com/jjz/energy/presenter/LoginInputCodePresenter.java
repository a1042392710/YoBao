package com.jjz.energy.presenter;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.model.LoginInputCodeModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.ILoginInputCodeView;

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
                .subscribeWith(new CommonSubscriber<LoginBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        mView.stopLoading();
                        mView.getAuthCodeSuc(loginBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
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
                .subscribeWith(new CommonSubscriber<LoginBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        mView.stopLoading();
                        mView.loginVCodeSuc(loginBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
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
                .subscribeWith(new CommonSubscriber<LoginBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        mView.stopLoading();
                        mView.forgotPasswordSuc(loginBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.forgotPasswordFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }



    @Override
    protected LoginInputCodeModel createModel() {
        return new LoginInputCodeModel();
    }
}

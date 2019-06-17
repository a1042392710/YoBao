package com.jjz.energy.presenter;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.model.GetCodeModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.IGetCodeView;

/**
 * create by: 获取验证码
 * Date: 2018/9/17 下午4:22
 */
public class GetCodePresenter extends BasePresenter<GetCodeModel, IGetCodeView> {


    public GetCodePresenter(IGetCodeView view) {
        initPresenter(view);
    }


    /**
     * 获取验证码
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getVCode(String map) {

        addSubscribe(mModel.getVCode(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isGetCodeSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 登录
     * @param map
     */
    @SuppressLint("CheckResult")
    public void login(String map) {

        addSubscribe(mModel.login(map)
                .subscribeWith(new CommonSubscriber<LoginBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(LoginBean response) {
                        mView.stopLoading();
                        mView.isLoginSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }
    /**
     * 密码登录
     * @param map
     */
    @SuppressLint("CheckResult")
    public void pwdLogin(String map) {

        addSubscribe(mModel.pwdLogin(map)
                .subscribeWith(new CommonSubscriber<LoginBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(LoginBean response) {
                        mView.stopLoading();
                        mView.isPwdLoginSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }
    @Override
    protected GetCodeModel createModel() {
        return new GetCodeModel();
    }

}
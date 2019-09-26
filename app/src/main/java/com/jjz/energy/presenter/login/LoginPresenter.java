package com.jjz.energy.presenter.login;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.entry.ModifyLoginPassWordBean;
import com.jjz.energy.model.login.LoginModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.login.ILoginView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 登录注册
 */
public class LoginPresenter extends BasePresenter<LoginModel, ILoginView> {


    public LoginPresenter(ILoginView view) {
        initPresenter(view);
    }


    /**
     * 登录
     */
    @SuppressLint("CheckResult")
    public void getLoginDate(String data) {

        addSubscribe(mModel.requestLogin(data)
                .subscribeWith(new CommonSubscriber<LoginBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        mView.stopLoading();
                        mView.getLoginSuc(loginBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.getLoginFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }
    /**
     * 获取验证码
     */
    @SuppressLint("CheckResult")
    public void getAuthCodeDate(String data) {

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
                    protected void onFail(String errorMsg) {
                        mView.getAuthCodeFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }
    /**
     *  修改登录密码
     */
    @SuppressLint("CheckResult")
    public void getModifyLoginPassWordDate(String data) {

        addSubscribe(mModel.requestModifyLoginPassWord(data)
                .subscribeWith(new CommonSubscriber<ModifyLoginPassWordBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(ModifyLoginPassWordBean modifyLoginPassWordBean) {
                        mView.stopLoading();
                        mView.getModifyLoginPassWordSuc(modifyLoginPassWordBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.getModifyLoginPassWordFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     *  将获取的第三方登录数据传回后台
     */
    public void apiLogin(String data) {

//        addSubscribe(mModel.apiLogin(data)
//                .subscribeWith(new CommonSubscriber<LoginBean>() {
//
//                    @Override
//                    protected void startLoading() {
//                        mView.showLoading();
//                    }
//
//                    @Override
//                    protected void onSuccess(LoginBean modifyLoginPassWordBean) {
//                        mView.stopLoading();
//                        mView.isApiLoginSuc(modifyLoginPassWordBean);
//                    }
//
//                    @Override
//                    protected void onFail(String errorMsg) {
//                        mView.getLoginFail(errorMsg);
//                        mView.stopLoading();
//                    }
//                }));

    }




    @Override
    protected LoginModel createModel() {
        return new LoginModel();
    }
}

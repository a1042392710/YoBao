package com.jjz.energy.presenter.login;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.model.login.LoginModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.login.ILoginView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 登录
 */
public class LoginPresenter extends BasePresenter<LoginModel, ILoginView> {


    public LoginPresenter(ILoginView view) {
        initPresenter(view);
    }


    /**
     * 登录
     */
    @SuppressLint("CheckResult")
    public void passWordLogin(String data) {

        addSubscribe(mModel.passWordLogin(data)
                .subscribeWith(new CommonSubscriber<UserInfo>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(UserInfo loginBean) {
                        mView.stopLoading();
                        mView.getLoginSuc(loginBean);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.getLoginFail(errorMsg);
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
//                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
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

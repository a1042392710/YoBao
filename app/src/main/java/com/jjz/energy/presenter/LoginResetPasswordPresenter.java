package com.jjz.energy.presenter;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.model.LoginResetPasswordModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.ILoginResetPasswordView;

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
                .subscribeWith(new CommonSubscriber<LoginBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(LoginBean registerBean) {
                        mView.stopLoading();
                        mView.isSuccess(registerBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));
    }


    @Override
    protected LoginResetPasswordModel createModel() {
        return new LoginResetPasswordModel();
    }
}

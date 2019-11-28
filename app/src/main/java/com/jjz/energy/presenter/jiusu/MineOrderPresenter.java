package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;
import com.jjz.energy.model.jiusu.MineOrderModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IMineOrderView;

/**
 * create by: 获取用户信息
 * Date: 2018/9/17 下午4:22
 */
public class MineOrderPresenter extends BasePresenter<MineOrderModel, IMineOrderView> {


    public MineOrderPresenter(IMineOrderView view) {
        initPresenter(view);
    }


    /**
     * 获取订单列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getOrderList(String map, boolean isShowLoading) {

        addSubscribe(mModel.getOrderList(map)
                .subscribeWith(new CommonSubscriber<JiuSuOrderBean>() {

                    @Override
                    protected void startLoading() {
                        if (isShowLoading){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(JiuSuOrderBean response) {
                        mView.stopLoading();
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected MineOrderModel createModel() {
        return new MineOrderModel();
    }

}
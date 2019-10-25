package com.jjz.energy.presenter.order;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.order.ExpressAddressInfoBean;
import com.jjz.energy.entry.order.ExpressCompanyBean;
import com.jjz.energy.entry.order.ExpressTrackingBean;
import com.jjz.energy.model.order.ExpressModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.order.IExpressView;

import java.util.List;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 物流的所有信息
 */
public class ExpressPresenter extends BasePresenter<ExpressModel, IExpressView> {


    public ExpressPresenter(IExpressView view) {
        initPresenter(view);
    }



    /**
     * 查询所有的物流公司
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getExpressCompany(String map) {

        addSubscribe(mModel.getExpressCompany(map)
                .subscribeWith(new CommonSubscriber<List<ExpressCompanyBean>>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(List<ExpressCompanyBean> response) {
                        mView.stopLoading();
                        mView.isGetExpressCompanySuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }



    /**
     * 发货
     * @param map
     */
    @SuppressLint("CheckResult")
    public void putExpressInfo(String map) {

        addSubscribe(mModel.putExpressInfo(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isPutExpressInfoSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }




    /**
     * 获取物流地址信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getExpressAddressInfo(String map) {

        addSubscribe(mModel.getExpressAddressInfo(map)
                .subscribeWith(new CommonSubscriber<ExpressAddressInfoBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(ExpressAddressInfoBean response) {
                        mView.stopLoading();
                        mView.isGetExpressAddressInfoSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取物流跟踪
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getExpressTracking(String map) {

        addSubscribe(mModel.getExpressTracking(map)
                .subscribeWith(new CommonSubscriber<ExpressTrackingBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(ExpressTrackingBean response) {
                        mView.stopLoading();
                        mView.isGetExpressTrackingSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected ExpressModel createModel() {
        return new ExpressModel();
    }
}

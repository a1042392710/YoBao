package com.jjz.energy.presenter.home;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.GoodsDetailsBean;
import com.jjz.energy.model.home.CommodityDetailsModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.ICommodityView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 商品详情
 */
public class CommodityDetailsPresenter extends BasePresenter<CommodityDetailsModel, ICommodityView> {


    public CommodityDetailsPresenter(ICommodityView view) {
        initPresenter(view);
    }


    /**
     * 获取商品详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getGoodsDetails(String map) {

        addSubscribe(mModel.getGoodsDetails(map)
                .subscribeWith(new CommonSubscriber<GoodsDetailsBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(GoodsDetailsBean response) {
                        mView.stopLoading();
                        mView.isGetGoodsDetailsSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected CommodityDetailsModel createModel() {
        return new CommodityDetailsModel();
    }
}

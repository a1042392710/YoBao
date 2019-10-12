package com.jjz.energy.presenter.home;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.commodity.GoodsListBean;
import com.jjz.energy.model.home.HomeCommodityModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.IHomeCommodityView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 首页  商品
 */
public class HomeCommodityPresenter extends BasePresenter<HomeCommodityModel, IHomeCommodityView> {


    public HomeCommodityPresenter(IHomeCommodityView view) {
        initPresenter(view);
    }

    /**
     * 商品列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getGoodsList(String map , boolean isLoadMore) {

        addSubscribe(mModel.getGoodsList(map)
                .subscribeWith(new CommonSubscriber<GoodsListBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(GoodsListBean response) {
                        mView.stopLoading();
                        mView.isGetGoodsSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isGetGoodsFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected HomeCommodityModel createModel() {
        return new HomeCommodityModel();
    }
}

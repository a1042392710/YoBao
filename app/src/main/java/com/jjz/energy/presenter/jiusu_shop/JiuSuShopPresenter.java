package com.jjz.energy.presenter.jiusu_shop;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;
import com.jjz.energy.model.jiusu_shop.JiuSuShopModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;

/**
 * create by: 久速商家
 * Date: 2018/9/17 下午4:22
 */
public class JiuSuShopPresenter extends BasePresenter<JiuSuShopModel, IJiuSuShopView> {


    public JiuSuShopPresenter(IJiuSuShopView view) {
        initPresenter(view);
    }


    /**
     * 获取推荐商家 和 商家分类
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getShopList(String map,boolean isLoadMore) {

        addSubscribe(mModel.getShopList(map)
                .subscribeWith(new CommonSubscriber<JiuSuShopBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(JiuSuShopBean response) {
                        mView.stopLoading();
                        mView.isGetClassAndShopListSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取商家个人主页信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getShopHomePageInfo(String map,boolean isLoadMore) {

        addSubscribe(mModel.getShopHomePage(map)
                .subscribeWith(new CommonSubscriber<ShopHomePageBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(ShopHomePageBean response) {
                        mView.stopLoading();
                        mView.isGetShopHomePageSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected JiuSuShopModel createModel() {
        return new JiuSuShopModel();
    }

}
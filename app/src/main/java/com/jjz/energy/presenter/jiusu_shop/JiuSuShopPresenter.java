package com.jjz.energy.presenter.jiusu_shop;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.commodity.HomePageCommentBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopClassBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingDetailsBean;
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
     * 获取推荐商家
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
     * 搜索商家
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getSearchShopList(String map,boolean isLoadMore) {

        addSubscribe(mModel.getSearchShopList(map)
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
     * 商家分类
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getShopClass(String map) {

        addSubscribe(mModel.getShopClass(map)
                .subscribeWith(new CommonSubscriber<JiuSuShopClassBean>() {

                    @Override
                    protected void startLoading() {}

                    @Override
                    protected void onSuccess(JiuSuShopClassBean response) {
                        mView.stopLoading();
                        mView.isGetShopClassSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取店内消费记录
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getJiuSuShoppingList(String map,boolean isLoadMore) {

        addSubscribe(mModel.getJiuSuShoppingList(map)
                .subscribeWith(new CommonSubscriber<JiuSuShoppingBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(JiuSuShoppingBean response) {
                        mView.stopLoading();
                        mView.isGetJiusuShoppingListSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    /**
     * 获取本店收款记录
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getJiuSuCollectionList(String map,boolean isLoadMore) {

        addSubscribe(mModel.getJiuSuCollectionList(map)
                .subscribeWith(new CommonSubscriber<JiuSuShoppingBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(JiuSuShoppingBean response) {
                        mView.stopLoading();
                        mView.isGetJiusuShoppingListSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 商家收款详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getJiuSuCollectionDetails(String map) {

        addSubscribe(mModel.getJiuCollectionDetails(map)
                .subscribeWith(new CommonSubscriber<JiuSuShoppingDetailsBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(JiuSuShoppingDetailsBean response) {
                        mView.stopLoading();
                        mView.isGetJiusuShoppingDetailsSuc(response);
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
    public void getShopHomePageInfo(String map) {

        addSubscribe(mModel.getShopHomePage(map)
                .subscribeWith(new CommonSubscriber<ShopHomePageBean>() {

                    @Override
                    protected void startLoading() {
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

    /**
     * 获取商家的评价
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getShopCommentList(String map ,boolean isLoadMore) {

        addSubscribe(mModel.getShopCommentList(map)
                .subscribeWith(new CommonSubscriber<HomePageCommentBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(HomePageCommentBean response) {
                        mView.stopLoading();
                        mView.isGetShopHomePageCommentSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 商家消费详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getJiuSuShoppingDetails(String map) {

        addSubscribe(mModel.getJiuSuShoppingDetails(map)
                .subscribeWith(new CommonSubscriber<JiuSuShoppingDetailsBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(JiuSuShoppingDetailsBean response) {
                        mView.stopLoading();
                        mView.isGetJiusuShoppingDetailsSuc(response);
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
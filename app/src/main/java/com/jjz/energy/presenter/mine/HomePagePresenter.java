package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.commodity.GoodsListBean;
import com.jjz.energy.entry.commodity.HomePageCommentBean;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;
import com.jjz.energy.entry.mine.UserPageInfo;
import com.jjz.energy.model.mine.HomePageModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IHomePageView;

/**
 * create by: 个人主页
 * Date: 2018/9/17 下午4:22
 */
public class HomePagePresenter extends BasePresenter<HomePageModel, IHomePageView> {


    public HomePagePresenter(IHomePageView view) {
        initPresenter(view);
    }

    /**
     * 获取用户信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getUserPageInfo(String map) {

        addSubscribe(mModel.getUserPageInfo(map)
                .subscribeWith(new CommonSubscriber<UserPageInfo>() {

                    @Override
                    protected void startLoading() {}

                    @Override
                    protected void onSuccess(UserPageInfo response) {
                        mView.stopLoading();
                        mView.isGetInfoSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }

    /**
     * 关注用户或取消关注
     * @param map
     */
    @SuppressLint("CheckResult")
    public void setFocusUser(String map) {

        addSubscribe(mModel.setFocusUser(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isFocusUserSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }


    /**
     * 查询该用户的所有商品
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getUserAllGoods(String map,boolean isLoadMore) {

        addSubscribe(mModel.getUserAllGoods(map)
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
                        mView.isGetUserAllGoods(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }

    /**
     * 查询该商家的所有商品
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getShopAllGoods(String map,boolean isLoadMore) {

        addSubscribe(mModel.getShopAllGoods(map)
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
                        mView.isGetShopAllGoods(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }
    /**
     * 查询该用户的所有评价
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getUserComments(String map,boolean isLoadMore) {

        addSubscribe(mModel.getUserComments(map)
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
                        mView.isGetUserComments(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }

    @Override
    protected HomePageModel createModel() {
        return new HomePageModel();
    }

}
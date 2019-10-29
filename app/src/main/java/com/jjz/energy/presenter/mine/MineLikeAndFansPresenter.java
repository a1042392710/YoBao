package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.mine.MineLikeAndFansBean;
import com.jjz.energy.model.mine.MineLikeAndFansModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IMineLikeAndFansView;

/**
 * create by: 我的关注 和我的粉丝
 * Date: 2018/9/17 下午4:22
 */
public class MineLikeAndFansPresenter extends BasePresenter<MineLikeAndFansModel, IMineLikeAndFansView> {


    public MineLikeAndFansPresenter(IMineLikeAndFansView view) {
        initPresenter(view);
    }

    /**
     * 获取我的关注
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getFocusList(String map , boolean isLoadMore) {

        addSubscribe(mModel.getFocusList(map)
                .subscribeWith(new CommonSubscriber<MineLikeAndFansBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(MineLikeAndFansBean response) {
                        mView.stopLoading();
                        mView.isGetMineLikeSuc(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }

    /**
     * 获取我的粉丝列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getFansList(String map,boolean isLoadMore) {

        addSubscribe(mModel.getFansList(map )
                .subscribeWith(new CommonSubscriber<MineLikeAndFansBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(MineLikeAndFansBean response) {
                        mView.stopLoading();
                        mView.isGetMineFansSuc(response);
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

    @Override
    protected MineLikeAndFansModel createModel() {
        return new MineLikeAndFansModel();
    }

}
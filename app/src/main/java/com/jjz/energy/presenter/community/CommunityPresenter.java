package com.jjz.energy.presenter.community;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.community.CommunityBean;
import com.jjz.energy.model.home.CommunityModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.ICommunityView;

import java.io.File;
import java.util.List;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 社区
 */
public class CommunityPresenter extends BasePresenter<CommunityModel, ICommunityView> {


    public CommunityPresenter(ICommunityView view) {
        initPresenter(view);
    }


    /**
     * 发布帖子
     * @param map
     */
    @SuppressLint("CheckResult")
    public void putPost(String map, List<File> files) {

        addSubscribe(mModel.putPost(map,files)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.isPutPostSuc(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取帖子列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getPostList(String map ,boolean isLoadMore) {

        addSubscribe(mModel.getPostList(map)
                .subscribeWith(new CommonSubscriber<CommunityBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(CommunityBean response) {
                        mView.isGetPostListSuc(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    /**
     * 获取帖子详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getPostDeatails(String map) {

        addSubscribe(mModel.getPostDeatails(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isGetPostDetailsSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 点赞
     */
    @SuppressLint("CheckResult")
    public void putLike(String map) {

        addSubscribe(mModel.putLike(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isPutPostLikeSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 评论
     */
    @SuppressLint("CheckResult")
    public void putComment(String map) {

        addSubscribe(mModel.putComment(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isPutPostCommentSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected CommunityModel createModel() {
        return new CommunityModel();
    }
}

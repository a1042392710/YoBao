package com.jjz.energy.presenter.order;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.model.order.EvaluateModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.order.IEvaluateView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 评价
 */
public class EvaluatePresenter extends BasePresenter<EvaluateModel, IEvaluateView> {


    public EvaluatePresenter(IEvaluateView view) {
        initPresenter(view);
    }



    /**
     *  提交评价信息
     */
    @SuppressLint("CheckResult")
    public void putEvaluateInfo(String map, String file) {

        addSubscribe(mModel.putEvaluateInfo(map,file)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.isPutEvaluateInfoSuc(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg, boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected EvaluateModel createModel() {
        return new EvaluateModel();
    }
}

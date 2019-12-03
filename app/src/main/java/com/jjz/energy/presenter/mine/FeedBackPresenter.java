package com.jjz.energy.presenter.mine;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.model.mine.FeedBackModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IFeedBackView;

import java.io.File;
import java.util.List;

/**
 * @author chenhao 2018/9/20
 * @function 意见反馈
 */
public class FeedBackPresenter extends BasePresenter<FeedBackModel, IFeedBackView> {


    public FeedBackPresenter(IFeedBackView view) {
        initPresenter(view);
    }


    /**
     * 意见反馈
     */
    public void feedbackSubmit(String pack_no, List<File>files) {

        addSubscribe( mModel.feedbackSubmit(pack_no,files)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
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
    protected FeedBackModel createModel() {
        return new FeedBackModel();
    }

}

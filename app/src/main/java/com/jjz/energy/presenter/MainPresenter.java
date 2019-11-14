package com.jjz.energy.presenter;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.mine.LikeGoodsBean;
import com.jjz.energy.model.MainModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.IMainView;

/**
 * @author chenhao 2018/9/20
 * @function 消息
 */
public class MainPresenter extends BasePresenter<MainModel, IMainView> {


    public MainPresenter(IMainView view) {
        initPresenter(view);
    }


    /**
     * 上传 推送Id
     *
     * @param pack_no
     */
    public void submitRegistrationId(String pack_no) {

        addSubscribe(mModel.submitRegistrationId(pack_no)
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
                    protected void onFail(String errorMsg,boolean isNetError) {
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 搜索
     *
     * @param pack_no
     */
    public void searchGoodsResult(String pack_no,boolean isLoadMore) {

        addSubscribe(mModel.getSearchGoodsResult(pack_no)
                .subscribeWith(new CommonSubscriber<LikeGoodsBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore) {
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(LikeGoodsBean response) {
                        mView.stopLoading();
                        mView.isGetSearchGoodsResultSuccess(response);
                    }
                    @Override
                    protected void onFail(String errorMsg,boolean isNetError) {
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected MainModel createModel() {
        return new MainModel();
    }

}

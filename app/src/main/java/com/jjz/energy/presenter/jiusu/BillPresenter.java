package com.jjz.energy.presenter.jiusu;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.BillEntry;
import com.jjz.energy.model.BillModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IBillView;

/**
 * @author chenhao 2018/9/20
 * @function 查看发票
 */
public class BillPresenter extends BasePresenter<BillModel, IBillView> {


    public BillPresenter(IBillView view) {
        initPresenter(view);
    }


    /**
     * 提交发票
     */
    public void submitBillData(String pack_no) {

        addSubscribe( mModel.getBillSubmit(pack_no)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isBillSubmitSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 查看发票
     */
    public void getBill(String pack_no) {

        addSubscribe( mModel.getBill(pack_no)
                .subscribeWith(new CommonSubscriber<BillEntry>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(BillEntry response) {
                        mView.stopLoading();
                        mView.getBillSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }


    @Override
    protected BillModel createModel() {
        return new BillModel();
    }

}

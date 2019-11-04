package com.jjz.energy.presenter.order;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.order.RefundDetailsBean;
import com.jjz.energy.model.order.RefundModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.order.IRefundView;

/**
 * @author chenhao 2018/9/20
 * @function 退款等流程
 */
public class RefundPresenter extends BasePresenter<RefundModel, IRefundView> {


    public RefundPresenter(IRefundView view) {
        initPresenter(view);
    }


    /**
     * 获取申请售后信息
     */
    public void getRefundDetails(String pack_no) {

        addSubscribe( mModel.getRefundDetails(pack_no)
                .subscribeWith(new CommonSubscriber<RefundDetailsBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(RefundDetailsBean response) {
                        mView.isGetRefundDetailsSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }



    @Override
    protected RefundModel createModel() {
        return new RefundModel();
    }

}

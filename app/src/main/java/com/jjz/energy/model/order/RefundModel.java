package com.jjz.energy.model.order;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.order.RefundDetailsBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @author chenhao 2018/11/27
 * @function 退款退货
 */
public class RefundModel extends BaseModel {

    /**
     * 获取退款详情数据
     * @param requestData
     */

    public Flowable<RefundDetailsBean> getRefundDetails(String requestData) {

        return RetrofitFactory.getRetrofit().create(Api.class).getRefundDetails(requestData).compose(RxSchedulerHepler.handleMyResult());

    }


}

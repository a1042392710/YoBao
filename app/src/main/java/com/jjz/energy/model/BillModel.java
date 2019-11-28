package com.jjz.energy.model;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.BillEntry;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @author chenhao 2018/11/27
 * @function 发票详情
 */
public class BillModel extends BaseModel {

    /**
     * 发票详情
     * @param requestData
     */
    public Flowable<String> getBillSubmit(String requestData) {

        return  RetrofitFactory.getRetrofit().create(Api.class).getBillSubmit(requestData).compose(RxSchedulerHepler.handleMyResult());

    }
    /**
     * 查看发票
     * @param requestData
     */
    public Flowable<BillEntry> getBill(String requestData) {

        return  RetrofitFactory.getRetrofit().create(Api.class).getBill(requestData).compose(RxSchedulerHepler.handleMyResult());

    }


}

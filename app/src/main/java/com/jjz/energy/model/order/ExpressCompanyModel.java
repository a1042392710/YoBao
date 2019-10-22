package com.jjz.energy.model.order;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.order.ExpressCompanyBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  快递公司
 */
public class ExpressCompanyModel extends BaseModel {


    //分类列表
    public Flowable<List<ExpressCompanyBean>> getExpressCompany(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getExpressCompany(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}

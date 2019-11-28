package com.jjz.energy.model.jiusu;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.AgencyBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import java.util.List;

import io.reactivex.Flowable;

/**
 * create 获取我的下级代理
 * Date: 2018/11/22 下午5:21
 */
public class MineAgencyModel extends BaseModel {

    public Flowable<List<AgencyBean>> getAgency(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getAgency(requestData).compose(RxSchedulerHepler.handleMyResult());
    }


}
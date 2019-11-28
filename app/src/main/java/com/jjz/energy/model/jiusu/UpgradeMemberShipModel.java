package com.jjz.energy.model.jiusu;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.VipListInfo;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;
import com.jjz.energy.wxapi.OrderPayTypeBean;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * create 主页地图
 * Date: 2018/11/22 下午5:21
 */
public class UpgradeMemberShipModel extends BaseModel {


    //升级会员
    public Flowable<OrderPayTypeBean> upVip(String requestData, String file) {

        MultipartBody.Builder mBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(Api.PACK_NO, requestData);
        if (!StringUtil.isEmpty(file)) {
            File mFile = new File(file);
            mBuilder.addFormDataPart("signature", mFile.getName(), RequestBody.create(MediaType.parse("image/png"), mFile));
        }

        return RetrofitFactory.getRetrofit().create(Api.class).upVip(mBuilder.build()).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取会员信息
    public Flowable<List<VipListInfo>> getVipInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getVipInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
}
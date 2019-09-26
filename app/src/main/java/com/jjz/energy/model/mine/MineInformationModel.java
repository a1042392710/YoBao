package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import java.io.File;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * create 个人信息
 * Date: 2018/11/22 下午5:21
 */
public class MineInformationModel extends BaseModel {

    //获取用户信息
    public Flowable<LoginBean> getUserInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getUser(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    /**
     * 更改用户信息
     */
    public Flowable<LoginBean> putMineInfo(String requestData, String file) {

        MultipartBody.Builder mBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(Api.PACK_NO, requestData);
        if (!StringUtil.isEmpty(file)) {
            File mFile = new File(file);
            mBuilder.addFormDataPart("head_pic", mFile.getName(), RequestBody.create(MediaType.parse("image/png"), mFile));
        }

        return RetrofitFactory.getRetrofit().create(Api.class).putMineInfo(mBuilder.build()).compose(RxSchedulerHepler.handleMyResult());
    }
    //提交推荐码
    public Flowable<String> putTuiJianCode(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putTuiJianCode(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
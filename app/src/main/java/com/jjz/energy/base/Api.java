package com.jjz.energy.base;

import com.jjz.energy.entry.BindBean;
import com.jjz.energy.entry.BindOwnerInfoBean;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.util.networkUtil.ResponseData;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * create by: chh
 * Date: 2018/10/25 上午8:45
 */
public interface Api {
     String BASE_URL = "http://192.168.0.199/app/";
//        String BASE_URL = "http://api.jjznewenergy.com/app/";
     String PACK_NO = "params";

    //获取验证码
    @FormUrlEncoded
    @POST("user/sendValidateCode")
    Flowable<ResponseData<String>> getVCode(@Field(PACK_NO) String pack_no);

    //登录
    @FormUrlEncoded
    @POST("user/smsLogin")
    Flowable<ResponseData<LoginBean>> login(@Field(PACK_NO) String pack_no);

    //密码登录
    @FormUrlEncoded
    @POST("user/pwdLogin ")
    Flowable<ResponseData<LoginBean>> pwdLogin(@Field(PACK_NO) String pack_no);

    //提交车主信息
    @FormUrlEncoded
    @POST("user/setUserinfo")
    Flowable<ResponseData<String>> putBindOwnerInfo(@Field(PACK_NO) String pack_no);

    //获取车主信息
    @FormUrlEncoded
    @POST("user/getUserinfo")
    Flowable<ResponseData<BindOwnerInfoBean>> getBindOwnernfo(@Field(PACK_NO) String pack_no);

    //提交微信和支付宝信息
    @FormUrlEncoded
    @POST("user/setUserBindInfo")
    Flowable<ResponseData<String>> putBindInfo(@Field(PACK_NO) String pack_no);

    //获取微信和支付宝绑定信息
    @FormUrlEncoded
    @POST("user/getUserBindInfo")
    Flowable<ResponseData<BindBean>> getBindInfo(@Field(PACK_NO) String pack_no);

    /**
     * 下载文件
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

}

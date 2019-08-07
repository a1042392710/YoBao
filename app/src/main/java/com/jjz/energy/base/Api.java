package com.jjz.energy.base;

import com.jjz.energy.entry.BindBean;
import com.jjz.energy.entry.BindOwnerInfoBean;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.entry.ModifyLoginPassWordBean;
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

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Flowable<ResponseData<LoginBean>> getLogin(
            @Field(PACK_NO) String data
    );

    //获取验证码
    @FormUrlEncoded
    @POST("user/sendValidateCode")
    Flowable<ResponseData<LoginBean>> getAuthCode(
            @Field(PACK_NO) String data
    );

    //验证码登录
    @FormUrlEncoded
    @POST("user/smsLogin")
    Flowable<ResponseData<LoginBean>> loginVCode(
            @Field(PACK_NO) String data
    );

    //修改登录密码
    @FormUrlEncoded
    @POST("user/editPassword")
    Flowable<ResponseData<ModifyLoginPassWordBean>> getModifyLoginPassWord(@Field(PACK_NO) String data);

    //忘记密码 = 提交验证码
    @FormUrlEncoded
    @POST("user/forgetPassword")
    Flowable<ResponseData<LoginBean>> forgotPasswordPutVCode(
            @Field(PACK_NO) String data
    );

    //忘记密码 = 重置密码
    @FormUrlEncoded
    @POST("user/setNewpassword")
    Flowable<ResponseData<LoginBean>> resetPassword(
            @Field(PACK_NO) String data
    );


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

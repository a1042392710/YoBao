package com.jjz.energy.base;

import com.jjz.energy.entry.AddressBean;
import com.jjz.energy.entry.BindBean;
import com.jjz.energy.entry.BindOwnerInfoBean;
import com.jjz.energy.entry.CommentBean;
import com.jjz.energy.entry.HomeDetailBean;
import com.jjz.energy.entry.LikeGoodsBean;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.entry.commodity.GoodsClassificationBean;
import com.jjz.energy.entry.commodity.GoodsDetailsBean;
import com.jjz.energy.entry.commodity.GoodsListBean;
import com.jjz.energy.entry.jiusu.AgencyBean;
import com.jjz.energy.entry.jiusu.HotBean;
import com.jjz.energy.entry.jiusu.HotDetailBean;
import com.jjz.energy.entry.jiusu.LoginBean;
import com.jjz.energy.entry.jiusu.MapMarkerBean;
import com.jjz.energy.entry.jiusu.MineAccountBean;
import com.jjz.energy.entry.jiusu.MineInfoBean;
import com.jjz.energy.entry.jiusu.MineWalletBean;
import com.jjz.energy.entry.jiusu.MineWalletListBean;
import com.jjz.energy.entry.jiusu.OrderBean;
import com.jjz.energy.entry.jiusu.OrderDetailBean;
import com.jjz.energy.entry.jiusu.ShareInfoBean;
import com.jjz.energy.entry.jiusu.ShopMarkerBean;
import com.jjz.energy.entry.jiusu.VipListInfo;
import com.jjz.energy.entry.jiusu.WithdrawListBean;
import com.jjz.energy.util.networkUtil.ResponseData;
import com.jjz.energy.wxapi.OrderPayTypeBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * create by: chh
 * Date: 2018/10/25 上午8:45
 */
public interface Api {
     String BASE_URL = "http://172.16.32.5/shop/";
//        String BASE_URL = "http://apit.jjznewenergy.com/app/";
     String PACK_NO = "params";


     //================================================== 用户

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Flowable<ResponseData<UserInfo>> passWordLogin(
            @Field(PACK_NO) String data
    );

    //获取验证码
    @FormUrlEncoded
    @POST("user/sendValidateCode")
    Flowable<ResponseData<String>> getAuthCode(
            @Field(PACK_NO) String data
    );

    //验证码登录
    @FormUrlEncoded
    @POST("user/smsLogin")
    Flowable<ResponseData<UserInfo>> loginVCode(
            @Field(PACK_NO) String data
    );

    //修改登录密码
    @FormUrlEncoded
    @POST("user/editPassword")
    Flowable<ResponseData<String>> getModifyLoginPassWord(@Field(PACK_NO) String data);

    //忘记密码 = 提交验证码
    @FormUrlEncoded
    @POST("user/forgetPassword")
    Flowable<ResponseData<UserInfo>> forgotPasswordPutVCode(
            @Field(PACK_NO) String data
    );

    //忘记密码 = 重置密码
    @FormUrlEncoded
    @POST("user/setNewpassword")
    Flowable<ResponseData<UserInfo>> resetPassword(
            @Field(PACK_NO) String data
    );

    //获取我的页面数据
    @FormUrlEncoded
    @POST("user/getUserinfo")
    Flowable<ResponseData<MineInfoBean>> getMineInfo(@Field(PACK_NO) String pack_no);

    /**
     * 修改个人信息
     */
    @POST("user/setUserinfo")
    Flowable<ResponseData<UserInfo>> putUserInfo(@Body MultipartBody mMultipartBody);

    //获取个人信息
    @FormUrlEncoded
    @POST("user/getUserinfo")
    Flowable<ResponseData<UserInfo>> getUserInfo(@Field(PACK_NO) String pack_no);

    //提交车主信息
    @FormUrlEncoded
    @POST("user/setUserinfo")
    Flowable<ResponseData<String>> putBindOwnerInfo(@Field(PACK_NO) String pack_no);

    //获取车主信息
    @FormUrlEncoded
    @POST("user/getUserinfo")
    Flowable<ResponseData<BindOwnerInfoBean>> getBindOwnernfo(@Field(PACK_NO) String pack_no);

    //获取我的收款账户信息
    @FormUrlEncoded
    @POST("user/getUserPayee")
    Flowable<ResponseData<MineAccountBean>> getMineAccounts(@Field(PACK_NO) String pack_no);

    //提交微信和支付宝信息
    @FormUrlEncoded
    @POST("user/setUserBindInfo")
    Flowable<ResponseData<String>> putBindInfo(@Field(PACK_NO) String pack_no);

    //获取微信和支付宝绑定信息
    @FormUrlEncoded
    @POST("user/getUserBindInfo")
    Flowable<ResponseData<BindBean>> getBindInfo(@Field(PACK_NO) String pack_no);


    //=================================================== 收货地址

    //获取久速商城基本信息
    @FormUrlEncoded
    @POST("user/getUserinfo")
    Flowable<ResponseData<LoginBean>> getJiuSuInfo(@Field(PACK_NO) String pack_no);

    /**
     * 我的 == 添加收货地址
     */
    @FormUrlEncoded
    @POST("user/addressSave")
    Flowable<ResponseData<AddressBean>> addAddress(@Field(PACK_NO) String pack_no);

    /**
     * 我的 == 删除收货地址
     */
    @FormUrlEncoded
    @POST("user/delAddress")
    Flowable<ResponseData<AddressBean>> deleteAddress(@Field(PACK_NO) String pack_no);

    /**
     * 我的 == 设为默认收货地址
     */
    @FormUrlEncoded
    @POST("user/myAddress")
    Flowable<ResponseData<AddressBean>> defaultAddress(@Field(PACK_NO) String pack_no);

    /**
     * 我的 == 收货地址列表
     */
    @FormUrlEncoded
    @POST("user/myAddress")
    Flowable<ResponseData<AddressBean>> getAddressList(@Field(PACK_NO) String pack_no);


    //======================================================== 发布 帖子 / 物流 / 商品


    //发布商品
    @Multipart
    @POST("goods/save")
    Flowable<ResponseData<String>> putCommodity(
            @Part MultipartBody.Part params,  @PartMap Map<String, RequestBody> imgFiles
    );

    //查询商品分类
    @FormUrlEncoded
    @POST("goods/getCateList")
    Flowable<ResponseData<List<GoodsClassificationBean>>> getClassification(@Field(PACK_NO) String pack_no);

    //查询我发布的商品
    @FormUrlEncoded
    @POST("user/myGoods")
    Flowable<ResponseData<LikeGoodsBean>> getMinePutGoods(@Field(PACK_NO) String pack_no);

    //下架商品
    @FormUrlEncoded
    @POST("goods/setGoodsSale")
    Flowable<ResponseData<String>> downGoods(@Field(PACK_NO) String pack_no);

    //首页 查询商品分类
    @FormUrlEncoded
    @POST("index/index")
    Flowable<ResponseData<HomeDetailBean>> getHomeClassification(@Field(PACK_NO) String pack_no);

    //查询商品列表
    @FormUrlEncoded
    @POST("index/getGoodsList")
    Flowable<ResponseData<GoodsListBean>> getGoodsList(@Field(PACK_NO) String pack_no);


    //首页 查询商品详情
    @FormUrlEncoded
    @POST("goods/goodsInfo")
    Flowable<ResponseData<GoodsDetailsBean>> getGoodsDetails(@Field(PACK_NO) String pack_no);

    //商品详情 查询留言评论列表
    @FormUrlEncoded
    @POST("goods/goodsComments")
    Flowable<ResponseData<CommentBean>> getGoodsComment(@Field(PACK_NO) String pack_no);

    //商品详情  发布留言评论
    @FormUrlEncoded
    @POST("goods/sendComment")
    Flowable<ResponseData<String>> putComment(@Field(PACK_NO) String pack_no);


    //商品详情  收藏商品
    @FormUrlEncoded
    @POST("goods/collectGoods")
    Flowable<ResponseData<String>> putCollect(@Field(PACK_NO) String pack_no);


    //我的  我的收藏
    @FormUrlEncoded
    @POST("user/myCollect")
    Flowable<ResponseData<LikeGoodsBean>> getLikeGoods(@Field(PACK_NO) String pack_no);


    //======================================================== 久速商城接口


    //查询下级代理
    @FormUrlEncoded
    @POST("user/agentsMembers")
    Flowable<ResponseData<List<AgencyBean>>> getAgency(@Field(PACK_NO) String pack_no);

    //地图 查询网点
    @FormUrlEncoded
    @POST("index/index")
    Flowable<ResponseData<List<MapMarkerBean>>> getServiceMarkerInfo(@Field(PACK_NO) String pack_no);

    //地图 查询具体网点信息
    @FormUrlEncoded
    @POST("index/getShopInfo")
    Flowable<ResponseData<ShopMarkerBean>> getShopInfo(@Field(PACK_NO) String pack_no);

    //热点 新闻列表
    @FormUrlEncoded
    @POST("news/index")
    Flowable<ResponseData<List<HotBean>>> getHotList(@Field(PACK_NO) String pack_no);

    //热点 新闻详情
    @FormUrlEncoded
    @POST("news/getNewsInfo")
    Flowable<ResponseData<HotDetailBean>> getHotDetail(@Field(PACK_NO) String pack_no);


    //获取买家订单列表
    @FormUrlEncoded
    @POST("order/buyerOrderList")
    Flowable<ResponseData<List<OrderBean>>> getOrderList(@Field(PACK_NO) String pack_no);

    //获取卖家订单列表
    @FormUrlEncoded
    @POST("order/sellerOrderList")
    Flowable<ResponseData<List<OrderBean>>> getSellerOrderList(@Field(PACK_NO) String pack_no);

    //卖家接受订单
    @FormUrlEncoded
    @POST("order/confirmOrder")
    Flowable<ResponseData<String>> confirmOrder(@Field(PACK_NO) String pack_no);

    //创建订单 获取支付的信息
    @FormUrlEncoded
    @POST("order/createOrder")
    Flowable<ResponseData<OrderPayTypeBean>> createOrder(@Field(PACK_NO) String pack_no);

    //买家订单详情
    @FormUrlEncoded
    @POST("order/buyerOrderInfo")
    Flowable<ResponseData<OrderDetailBean>> getOrderDetail(@Field(PACK_NO) String pack_no);

    //卖家订单详情
    @FormUrlEncoded
    @POST("order/sellerOrderInfo")
    Flowable<ResponseData<OrderDetailBean>> getSellerOrderDetail(@Field(PACK_NO) String pack_no);

    //卖家订单详情
    @FormUrlEncoded
    @POST("order/scanQrOrder")
    Flowable<ResponseData<OrderDetailBean>> scanQrOrder(@Field(PACK_NO) String pack_no);

    //完结订单
    @FormUrlEncoded
    @POST("order/finishOrder")
    Flowable<ResponseData<String>> finishOrder(@Field(PACK_NO) String pack_no);

    //升级VIP
    @POST("recharge/createRechargeOrder")
    Flowable<ResponseData<OrderPayTypeBean>> upVip(@Body MultipartBody mMultipartBody);

    //获取会员信息
    @FormUrlEncoded
    @POST("user/levelList")
    Flowable<ResponseData<List<VipListInfo>>> getVipInfo(@Field(PACK_NO) String pack_no);

    //取消订单
    @FormUrlEncoded
    @POST("order/cancelOrder")
    Flowable<ResponseData<String>> cancelOrder(@Field(PACK_NO) String pack_no);

    //提交推荐码
    @FormUrlEncoded
    @POST("user/linkParent")
    Flowable<ResponseData<String>> putTuiJianCode(@Field(PACK_NO) String pack_no);

    //推荐分享
    @FormUrlEncoded
    @POST("user/share ")
    Flowable<ResponseData<ShareInfoBean>> getShareInfo(@Field(PACK_NO) String pack_no);

    //密码登录
    @FormUrlEncoded
    @POST("user/pwdLogin ")
    Flowable<ResponseData<LoginBean>> pwdLogin(@Field(PACK_NO) String pack_no);

    //我的佣金
    @FormUrlEncoded
    @POST("commission/myCommissions")
    Flowable<ResponseData<MineWalletBean>> getBalance(@Field(PACK_NO) String pack_no);

    //佣金列表
    @FormUrlEncoded
    @POST("commission/commissionsList")
    Flowable<ResponseData<List<MineWalletListBean>>> getBalanceList(@Field(PACK_NO) String pack_no);

    //提现记录c
    @FormUrlEncoded
    @POST("commission/insufficientList")
    Flowable<ResponseData<List<WithdrawListBean>>> getWithdrawList(@Field(PACK_NO) String pack_no);

    //提现信息
    @FormUrlEncoded
    @POST("commission/commissionApplying")
    Flowable<ResponseData<String>> putWithdrawInfo(@Field(PACK_NO) String pack_no);






    /**
     * 下载文件
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

}

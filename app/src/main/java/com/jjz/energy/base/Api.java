package com.jjz.energy.base;

import com.jjz.energy.entry.NoticeListInfo;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.entry.commodity.CommentBean;
import com.jjz.energy.entry.commodity.GoodsBean;
import com.jjz.energy.entry.commodity.GoodsClassificationBean;
import com.jjz.energy.entry.commodity.GoodsDetailsBean;
import com.jjz.energy.entry.commodity.GoodsListBean;
import com.jjz.energy.entry.commodity.HomePageCommentBean;
import com.jjz.energy.entry.community.Community;
import com.jjz.energy.entry.community.CommunityBean;
import com.jjz.energy.entry.community.CommunityCommentBean;
import com.jjz.energy.entry.home.BindBean;
import com.jjz.energy.entry.home.HomeDetailBean;
import com.jjz.energy.entry.jiusu.AgencyBean;
import com.jjz.energy.entry.jiusu.BillEntry;
import com.jjz.energy.entry.jiusu.BindOwnerInfoBean;
import com.jjz.energy.entry.jiusu.CommissionDetailBean;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;
import com.jjz.energy.entry.jiusu.LoginBean;
import com.jjz.energy.entry.jiusu.MapMarkerBean;
import com.jjz.energy.entry.jiusu.MineAccountBean;
import com.jjz.energy.entry.jiusu.MineInfoBean;
import com.jjz.energy.entry.jiusu.MineWalletBean;
import com.jjz.energy.entry.jiusu.MineWalletListBean;
import com.jjz.energy.entry.jiusu.OrderDetailBean;
import com.jjz.energy.entry.jiusu.ShareInfoBean;
import com.jjz.energy.entry.jiusu.ShopMarkerBean;
import com.jjz.energy.entry.jiusu.VipListInfo;
import com.jjz.energy.entry.jiusu.WithdrawInfoBean;
import com.jjz.energy.entry.jiusu.WithdrawListBean;
import com.jjz.energy.entry.jiusu.YoCardReceiveListBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShop;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopClassBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingDetailsBean;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.entry.mine.LikeGoodsBean;
import com.jjz.energy.entry.mine.MineBuyerBean;
import com.jjz.energy.entry.mine.MineIntegralBean;
import com.jjz.energy.entry.mine.MineLikeAndFansBean;
import com.jjz.energy.entry.mine.OrderNoticeBean;
import com.jjz.energy.entry.mine.RefundHistroyBean;
import com.jjz.energy.entry.mine.UserPageInfo;
import com.jjz.energy.entry.order.ApplicationRefundBean;
import com.jjz.energy.entry.order.EvaluateDetailsBean;
import com.jjz.energy.entry.order.ExpressAddressInfoBean;
import com.jjz.energy.entry.order.ExpressCompanyBean;
import com.jjz.energy.entry.order.ExpressTrackingBean;
import com.jjz.energy.entry.order.RefundDetailsBean;
import com.jjz.energy.entry.order.ShopOrderDetailsBean;
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
     String BASE_URL = "http://172.16.32.3/";
//        String BASE_URL = "http://apit.jjznewenergy.com/app/";
     String PACK_NO = "params";


     //-------------------------------------------------- 用户

    //登录
    @FormUrlEncoded
    @POST("shop/user/login")
    Flowable<ResponseData<UserInfo>> passWordLogin(
            @Field(PACK_NO) String data
    );

    //获取验证码
    @FormUrlEncoded
    @POST("shop/user/sendValidateCode")
    Flowable<ResponseData<String>> getAuthCode(
            @Field(PACK_NO) String data
    );

    //验证码登录
    @FormUrlEncoded
    @POST("shop/user/smsLogin")
    Flowable<ResponseData<UserInfo>> loginVCode(
            @Field(PACK_NO) String data
    );

    //修改登录密码
    @FormUrlEncoded
    @POST("shop/user/editPassword")
    Flowable<ResponseData<String>> getModifyLoginPassWord(@Field(PACK_NO) String data);

    //忘记密码 = 提交验证码
    @FormUrlEncoded
    @POST("shop/user/forgetPassword")
    Flowable<ResponseData<UserInfo>> forgotPasswordPutVCode(@Field(PACK_NO) String data);

    //忘记密码 = 重置密码
    @FormUrlEncoded
    @POST("shop/user/setNewpassword")
    Flowable<ResponseData<UserInfo>> resetPassword(@Field(PACK_NO) String data);

    //获取我的页面数据
    @FormUrlEncoded
    @POST("shop/user/getUserinfo")
    Flowable<ResponseData<MineInfoBean>> getMineInfo(@Field(PACK_NO) String pack_no);

    /**
     * 修改个人信息
     */
    @POST("shop/user/setUserinfo")
    Flowable<ResponseData<UserInfo>> putUserInfo(@Body MultipartBody mMultipartBody);

    //获取个人信息
    @FormUrlEncoded
    @POST("shop/user/getUserinfo")
    Flowable<ResponseData<UserInfo>> getUserInfo(@Field(PACK_NO) String pack_no);

    //获取主页个人信息
    @FormUrlEncoded
    @POST("shop/user/getUserPageInfo")
    Flowable<ResponseData<UserPageInfo>> getUserPageInfo(@Field(PACK_NO) String pack_no);

    //关注用户和取消关注
    @FormUrlEncoded
    @POST("shop/user/setFocusUser")
    Flowable<ResponseData<String>> setFocusUser(@Field(PACK_NO) String pack_no);

    //获取用户的所有商品
    @FormUrlEncoded
    @POST("shop/user/userPageGoodsList")
    Flowable<ResponseData<GoodsListBean>> getUserAllGoods(@Field(PACK_NO) String pack_no);

    //获取商家的所有商品
    @FormUrlEncoded
    @POST("shop/shop/shopGoods")
    Flowable<ResponseData<ShopHomePageBean>> getShopAllGoods(@Field(PACK_NO) String pack_no);

    //获取用户的所有评价
    @FormUrlEncoded
    @POST("shop/user/userPageCommentList")
    Flowable<ResponseData<HomePageCommentBean>> getUserComments(@Field(PACK_NO) String pack_no);


    //获取关注列表
    @FormUrlEncoded
    @POST("shop/user/focusList")
    Flowable<ResponseData<MineLikeAndFansBean>> getFocusList(@Field(PACK_NO) String pack_no);


    //获取粉丝列表
    @FormUrlEncoded
    @POST("shop/user/fansList")
    Flowable<ResponseData<MineLikeAndFansBean>> getFansList(@Field(PACK_NO) String pack_no);

    //获取我的收款账户信息
    @FormUrlEncoded
    @POST("shop/user/getUserPayee")
    Flowable<ResponseData<MineAccountBean>> getMineAccounts(@Field(PACK_NO) String pack_no);

    //提交微信和支付宝信息
    @FormUrlEncoded
    @POST("shop/user/setUserBindInfo")
    Flowable<ResponseData<String>> putBindInfo(@Field(PACK_NO) String pack_no);

    //获取微信和支付宝绑定信息
    @FormUrlEncoded
    @POST("shop/user/getUserBindInfo")
    Flowable<ResponseData<BindBean>> getBindInfo(@Field(PACK_NO) String pack_no);


    //--------------------------------------------------= 收货地址


    /**
     * 我的 -- 添加收货地址
     */
    @FormUrlEncoded
    @POST("shop/user/addressSave")
    Flowable<ResponseData<AddressBean>> addAddress(@Field(PACK_NO) String pack_no);

    /**
     * 我的 -- 删除收货地址
     */
    @FormUrlEncoded
    @POST("shop/user/delAddress")
    Flowable<ResponseData<AddressBean>> deleteAddress(@Field(PACK_NO) String pack_no);

    /**
     * 我的 -- 设为默认收货地址
     */
    @FormUrlEncoded
    @POST("shop/user/myAddress")
    Flowable<ResponseData<AddressBean>> defaultAddress(@Field(PACK_NO) String pack_no);

    /**
     * 我的 -- 收货地址列表
     */
    @FormUrlEncoded
    @POST("shop/user/myAddress")
    Flowable<ResponseData<AddressBean>> getAddressList(@Field(PACK_NO) String pack_no);





    //-------------------------------------------------------- 发布 帖子 / 物流 / 商品


    //发布商品
    @Multipart
    @POST("shop/goods/save")
    Flowable<ResponseData<String>> putCommodity(
            @Part MultipartBody.Part params,  @PartMap Map<String, RequestBody> imgFiles
    );

    //查询商品分类
    @FormUrlEncoded
    @POST("shop/goods/getCateList")
    Flowable<ResponseData<List<GoodsClassificationBean>>> getClassification(@Field(PACK_NO) String pack_no);

    //查询我发布的商品
    @FormUrlEncoded
    @POST("shop/user/myGoods")
    Flowable<ResponseData<LikeGoodsBean>> getMinePutGoods(@Field(PACK_NO) String pack_no);

    //下架商品
    @FormUrlEncoded
    @POST("shop/goods/setGoodsSale")
    Flowable<ResponseData<String>> downGoods(@Field(PACK_NO) String pack_no);

    //首页 查询商品分类
    @FormUrlEncoded
    @POST("shop/index/index")
    Flowable<ResponseData<HomeDetailBean>> getHomeClassification(@Field(PACK_NO) String pack_no);

    //查询商品列表
    @FormUrlEncoded
    @POST("shop/index/getGoodsList")
    Flowable<ResponseData<GoodsListBean>> getGoodsList(@Field(PACK_NO) String pack_no);


    //首页 查询商品详情
    @FormUrlEncoded
    @POST("shop/goods/goodsInfo")
    Flowable<ResponseData<GoodsDetailsBean>> getGoodsDetails(@Field(PACK_NO) String pack_no);

    //商品详情 查询留言评论列表
    @FormUrlEncoded
    @POST("shop/goods/goodsComments")
    Flowable<ResponseData<CommentBean>> getGoodsComment(@Field(PACK_NO) String pack_no);

    //商品详情  发布留言评论
    @FormUrlEncoded
    @POST("shop/goods/sendComment")
    Flowable<ResponseData<String>> putComment(@Field(PACK_NO) String pack_no);


    //商品详情  收藏商品
    @FormUrlEncoded
    @POST("shop/goods/collectGoods")
    Flowable<ResponseData<String>> putCollect(@Field(PACK_NO) String pack_no);


    //我的  我的收藏
    @FormUrlEncoded
    @POST("shop/user/myCollect")
    Flowable<ResponseData<LikeGoodsBean>> getLikeGoods(@Field(PACK_NO) String pack_no);

    //我的  我买到的
    @FormUrlEncoded
    @POST("shop/order/buyerOrderList")
    Flowable<ResponseData<MineBuyerBean>> getMyBuyer(@Field(PACK_NO) String pack_no);

    //我的  我卖出的
    @FormUrlEncoded
    @POST("shop/order/salerOrderList")
    Flowable<ResponseData<MineBuyerBean>> getMySeller(@Field(PACK_NO) String pack_no);

    // 评价一下
    @POST("shop/order/sendComment")
    Flowable<ResponseData<String>> putEvaluateInfo(@Body MultipartBody mMultipartBody);

    //查看评价
    @FormUrlEncoded
    @POST("shop/order/orderComment")
    Flowable<ResponseData<EvaluateDetailsBean>> getEvaluateDetails(@Field(PACK_NO) String pack_no);


    // ----------------------------------------------------------  社区

    //发布帖子
    @Multipart
    @POST("shop/time_line/createTimeLine")
    Flowable<ResponseData<Community>> putPost(
            @Part MultipartBody.Part params,  @PartMap Map<String, RequestBody> imgFiles
    );

    //查询帖子列表
    @FormUrlEncoded
    @POST("shop/time_line/index")
    Flowable<ResponseData<CommunityBean>> getPostList(@Field(PACK_NO) String pack_no);

    //查询指定用户的帖子列表
    @FormUrlEncoded
    @POST("shop/time_line/myTimeLine")
    Flowable<ResponseData<CommunityBean>> getUserPostList(@Field(PACK_NO) String pack_no);

    //查询指定用户的帖子详情
    @FormUrlEncoded
    @POST("shop/time_line/timeLineInfo")
    Flowable<ResponseData<Community>> getPostDetails(@Field(PACK_NO) String pack_no);

    //查看帖子中的评论
    @FormUrlEncoded
    @POST("shop/time_line/timeLineComments")
    Flowable<ResponseData<CommunityCommentBean>> getPostComment(@Field(PACK_NO) String pack_no);

    //点赞帖子
    @FormUrlEncoded
    @POST("shop/time_line/topThis")
    Flowable<ResponseData<String>> putPostLike(@Field(PACK_NO) String pack_no);


    //帖子评价
    @FormUrlEncoded
    @POST("shop/time_line/sendComment")
    Flowable<ResponseData<String>> putPostComment(@Field(PACK_NO) String pack_no);




    // ----------------------------------------------------------  订单

    //确认订单页面获取商品信息
    @FormUrlEncoded
    @POST("shop/order/checkOrder")
    Flowable<ResponseData<GoodsBean>> getSureBuyInfo(@Field(PACK_NO) String pack_no);

    //确认订单页面获取商家信息
    @FormUrlEncoded
    @POST("shop/order/scanCheck")
    Flowable<ResponseData<JiuSuShop>> getShopsInfo(@Field(PACK_NO) String pack_no);

    //立即付款页面 获取支付信息
    @FormUrlEncoded
    @POST("shop/order/createOrder")
    Flowable<ResponseData<OrderPayTypeBean>> getBuyGoodsInfo(@Field(PACK_NO) String pack_no);

    //立即付款页面 获取商家支付信息
    @FormUrlEncoded
    @POST("shop/order/createScanOrder")
    Flowable<ResponseData<OrderPayTypeBean>> getBuyShopsInfo(@Field(PACK_NO) String pack_no);

    // 查询物流公司
    @FormUrlEncoded
    @POST("shop/order/shippingList")
    Flowable<ResponseData<List<ExpressCompanyBean>>> getExpressCompany(@Field(PACK_NO) String pack_no);

    // 发货
    @FormUrlEncoded
    @POST("shop/order/sendGoods")
    Flowable<ResponseData<String>> putExpressInfo(@Field(PACK_NO) String pack_no);


    // 获取物流地址信息
    @FormUrlEncoded
    @POST("shop/order/showSendGoods")
    Flowable<ResponseData<ExpressAddressInfoBean>> getExpressAddressInfo(@Field(PACK_NO) String pack_no);

    // 获取物流跟踪信息
    @FormUrlEncoded
    @POST("shop/order/shippingInfo")
    Flowable<ResponseData<ExpressTrackingBean>> getExpressTracking(@Field(PACK_NO) String pack_no);

    // 订单详情
    @FormUrlEncoded
    @POST("shop/order/orderinfo")
    Flowable<ResponseData<ShopOrderDetailsBean>> getShopOrderDetails(@Field(PACK_NO) String pack_no);

    // 确认收货
    @FormUrlEncoded
    @POST("shop/order/confirmOrder")
    Flowable<ResponseData<String>> confirmReceipt(@Field(PACK_NO) String pack_no);

    // 提醒收货
    @FormUrlEncoded
    @POST("shop/order/remindReceive")
    Flowable<ResponseData<String>> remindReceipt(@Field(PACK_NO) String pack_no);

    // 提醒发货
    @FormUrlEncoded
    @POST("shop/order/remindSend")
    Flowable<ResponseData<String>> remindShipment(@Field(PACK_NO) String pack_no);

    // 取消订单
    @FormUrlEncoded
    @POST("shop/order/cancelOrder")
    Flowable<ResponseData<String>> cancelShopOrder(@Field(PACK_NO) String pack_no);

    //退款申请页面
    @FormUrlEncoded
    @POST("shop/return_goods/addReturnGoods")
    Flowable<ResponseData<ApplicationRefundBean>> getApplicationRefundData(@Field(PACK_NO) String pack_no);

    //退款详情页面
    @FormUrlEncoded
    @POST("shop/return_goods/returnGoodsInfo")
    Flowable<ResponseData<RefundDetailsBean>> getRefundDetails(@Field(PACK_NO) String pack_no);

    // 退款申请页面  提交申请
    @Multipart
    @POST("shop/return_goods/doAddReturnGoods")
    Flowable<ResponseData<ApplicationRefundBean>> submitRefund(
            @Part MultipartBody.Part prams, @Part("file") String urlsdesc, @PartMap Map<String, RequestBody> imgs
    );

    //买家撤销退款
    @FormUrlEncoded
    @POST("shop/return_goods/doRefund")
    Flowable<ResponseData<String>> buyerCancelApplication(@Field(PACK_NO) String pack_no);

    //买家提交退货物流信息
    @Multipart
    @POST("shop/return_goods/doRefund")
    Flowable<ResponseData<String>> buyerPutExpressInfo(@Part MultipartBody.Part prams, @PartMap Map<String, RequestBody> imgs);

    //卖家同意退款
    @FormUrlEncoded
    @POST("shop/return_goods/doRefund")
    Flowable<ResponseData<String>> sellerAgreeReturnMoney(@Field(PACK_NO) String pack_no);

    //卖家同意退货，上传退货地址
    @FormUrlEncoded
    @POST("shop/return_goods/doRefund")
    Flowable<ResponseData<String>> sellerPutExpressInfo(@Field(PACK_NO) String pack_no);

    //卖家拒绝退款申请
    @Multipart
    @POST("shop/return_goods/doRefund")
    Flowable<ResponseData<String>> sellerRefuseApplication(@Part MultipartBody.Part prams, @PartMap Map<String, RequestBody> imgs);

    /**
     * 退货 -- 协商历史
     */
    @FormUrlEncoded
    @POST("shop/return_goods/returnGoodsLog")
    Flowable<ResponseData<RefundHistroyBean>> getRefundHistoryList(@Field(PACK_NO) String pack_no);

    /**
     * 获取指定类型的消息
     */
    @FormUrlEncoded
    @POST("shop/tiding/tidingList")
    Flowable<ResponseData<OrderNoticeBean>> getOrderNoticeList(@Field(PACK_NO) String pack_no);

    /**
     * 获取所有消息类型的最新数据
     */
    @FormUrlEncoded
    @POST("shop/tiding/index")
    Flowable<ResponseData<NoticeListInfo>> getNoticeListInfo(@Field(PACK_NO) String pack_no);


    // -------------------------------------------------------------------- 久速商家

    /**
     * 获取推荐商家列表 和 商家分类信息
     */
    @FormUrlEncoded
    @POST("shop/shop/index")
    Flowable<ResponseData<JiuSuShopBean>> getShopList(@Field(PACK_NO) String pack_no);

    /**
     * 搜索商家
     */
    @FormUrlEncoded
    @POST("shop/shop/index")
    Flowable<ResponseData<JiuSuShopBean>> getSearchShopList(@Field(PACK_NO) String pack_no);

    /**
     * 商家分类信息
     */
    @FormUrlEncoded
    @POST("shop/shop/cateList")
    Flowable<ResponseData<JiuSuShopClassBean>> getShopClass(@Field(PACK_NO) String pack_no);

    /**
     * 获取久速商家店内消费记录
     */
    @FormUrlEncoded
    @POST("shop/order/scanOrderList")
    Flowable<ResponseData<JiuSuShoppingBean>> getJiuSuShoppingList(@Field(PACK_NO) String pack_no);

    /**
     * 获取店内收款记录
     */
    @FormUrlEncoded
    @POST("shop/user/scanOrderList")
    Flowable<ResponseData<JiuSuShoppingBean>> getJiuSuCollectionList(@Field(PACK_NO) String pack_no);

    /**
     * 获取久速商家店内消费记录 详情
     */
    @FormUrlEncoded
    @POST("shop/order/scanOrderInfo")
    Flowable<ResponseData<JiuSuShoppingDetailsBean>> getJiuSuShoppingDetails(@Field(PACK_NO) String pack_no);

    /**
     * 获取收款记录 详情
     */
    @FormUrlEncoded
    @POST("shop/user/scanOrderInfo")
    Flowable<ResponseData<JiuSuShoppingDetailsBean>> getJiuCollectionDetails(@Field(PACK_NO) String pack_no);

    /**
     * 获取推荐商家列表 和 商家分类信息
     */
    @FormUrlEncoded
    @POST("shop/user/setUserinfo")
    Flowable<ResponseData<String>> submitRegistrationId(@Field(PACK_NO) String pack_no);

    /**
     * 获取商家个人主页的信息
     */
    @FormUrlEncoded
    @POST("shop/shop/shopIndex")
    Flowable<ResponseData<ShopHomePageBean>> getShopHomePage(@Field(PACK_NO) String pack_no);

    /**
     * 获取商家个人主页的评价信息
     */
    @FormUrlEncoded
    @POST("shop/shop/shopComment")
    Flowable<ResponseData<HomePageCommentBean>> getShopCommentList(@Field(PACK_NO) String pack_no);

    /**
     * 搜索商品
     */
    @FormUrlEncoded
    @POST("shop/goods/search")
    Flowable<ResponseData<LikeGoodsBean>> getSearchGoodsResult(@Field(PACK_NO) String pack_no);



    //-------------------------------------------------------- 以下是 久速接口

    //获取久速专区基本信息
    @FormUrlEncoded
    @POST("app/user/getUserinfo")
    Flowable<ResponseData<LoginBean>> getJiuSuInfo(@Field(PACK_NO) String pack_no);


    //查询下级代理
    @FormUrlEncoded
    @POST("app/user/agentsMembers")
    Flowable<ResponseData<List<AgencyBean>>> getAgency(@Field(PACK_NO) String pack_no);

    //地图 查询网点
    @FormUrlEncoded
    @POST("app/index/index")
    Flowable<ResponseData<List<MapMarkerBean>>> getServiceMarkerInfo(@Field(PACK_NO) String pack_no);

    //地图 查询具体网点信息
    @FormUrlEncoded
    @POST("app/index/getShopInfo")
    Flowable<ResponseData<ShopMarkerBean>> getShopInfo(@Field(PACK_NO) String pack_no);

    //获取买家订单列表
    @FormUrlEncoded
    @POST("app/order/buyerOrderList")
    Flowable<ResponseData<JiuSuOrderBean>> getOrderList(@Field(PACK_NO) String pack_no);

    //获取卖家订单列表
    @FormUrlEncoded
    @POST("app/order/sellerOrderList")
    Flowable<ResponseData<JiuSuOrderBean>> getSellerOrderList(@Field(PACK_NO) String pack_no);

    //卖家接受订单
    @FormUrlEncoded
    @POST("app/order/confirmOrder")
    Flowable<ResponseData<String>> confirmOrder(@Field(PACK_NO) String pack_no);

    //创建订单 获取支付的信息
    @FormUrlEncoded
    @POST("app/order/createOrder")
    Flowable<ResponseData<OrderPayTypeBean>> createOrder(@Field(PACK_NO) String pack_no);

    //买家订单详情
    @FormUrlEncoded
    @POST("app/order/buyerOrderInfo")
    Flowable<ResponseData<OrderDetailBean>> getOrderDetail(@Field(PACK_NO) String pack_no);

    //卖家订单详情
    @FormUrlEncoded
    @POST("app/order/sellerOrderInfo")
    Flowable<ResponseData<OrderDetailBean>> getSellerOrderDetail(@Field(PACK_NO) String pack_no);

    //卖家订单详情
    @FormUrlEncoded
    @POST("app/order/scanQrOrder")
    Flowable<ResponseData<OrderDetailBean>> scanQrOrder(@Field(PACK_NO) String pack_no);

    //完结订单
    @FormUrlEncoded
    @POST("app/order/finishOrder")
    Flowable<ResponseData<String>> finishOrder(@Field(PACK_NO) String pack_no);

    //升级VIP
    @POST("app/recharge/createRechargeOrder")
    Flowable<ResponseData<OrderPayTypeBean>> upVip(@Body MultipartBody mMultipartBody);

    //获取会员信息
    @FormUrlEncoded
    @POST("app/user/levelList")
    Flowable<ResponseData<List<VipListInfo>>> getVipInfo(@Field(PACK_NO) String pack_no);

    //取消订单
    @FormUrlEncoded
    @POST("app/order/cancelOrder")
    Flowable<ResponseData<String>> cancelOrder(@Field(PACK_NO) String pack_no);

    //提交推荐码
    @FormUrlEncoded
    @POST("app/user/linkParent")
    Flowable<ResponseData<String>> putTuiJianCode(@Field(PACK_NO) String pack_no);

    //推荐分享
    @FormUrlEncoded
    @POST("app/user/share ")
    Flowable<ResponseData<ShareInfoBean>> getShareInfo(@Field(PACK_NO) String pack_no);

    //密码登录
    @FormUrlEncoded
    @POST("app/user/pwdLogin ")
    Flowable<ResponseData<LoginBean>> pwdLogin(@Field(PACK_NO) String pack_no);

    //我的佣金
    @FormUrlEncoded
    @POST("app/commission/myCommissions")
    Flowable<ResponseData<MineWalletBean>> getBalance(@Field(PACK_NO) String pack_no);

    //佣金列表
    @FormUrlEncoded
    @POST("app/commission/commissionsList")
    Flowable<ResponseData<List<MineWalletListBean>>> getBalanceList(@Field(PACK_NO) String pack_no);

    //提现记录
    @FormUrlEncoded
    @POST("app/commission/insufficientList")
    Flowable<ResponseData<List<WithdrawListBean>>> getWithdrawList(@Field(PACK_NO) String pack_no);

    //佣金详情
    @FormUrlEncoded
    @POST("app/commission/commissionsSecondList")
    Flowable<ResponseData<CommissionDetailBean>> getCommissionDetail(@Field(PACK_NO) String pack_no);

    //查询上次提现银行卡的记录
    @FormUrlEncoded
    @POST("app/commission/lastApply")
    Flowable<ResponseData<WithdrawInfoBean>> getWithdrawInfo(@Field(PACK_NO) String pack_no);

    //提现信息
    @POST("app/commission/commissionApplying")
    @FormUrlEncoded
    Flowable<ResponseData<String>> putWithdrawInfo(@Field(PACK_NO) String pack_no);

    //我的订单 提交发票
    @FormUrlEncoded
    @POST("app/order/createInvoice")
    Flowable<ResponseData<String>> getBillSubmit(@Field(PACK_NO) String pack_no);

    //查看发票
    @FormUrlEncoded
    @POST("app/order/getOrderInvoice")
    Flowable<ResponseData<BillEntry>> getBill(@Field(PACK_NO) String pack_no);

    //获取赠品领取记录
    @FormUrlEncoded
    @POST("app/user/consumptionRecord")
    Flowable<ResponseData<List<YoCardReceiveListBean>>> getYoGiftList(@Field(PACK_NO) String pack_no);

    //我的积分
    @FormUrlEncoded
    @POST("app/user/myPointsList")
    Flowable<ResponseData<MineIntegralBean>> getIntegralList(@Field(PACK_NO) String pack_no);

    @Multipart
    @POST("app/user/setUserinfo")
    Flowable<ResponseData<String>> putBindOwnerInfo(
            @Part MultipartBody.Part params, @PartMap Map<String, RequestBody> imgFiles
    );
    //获取车主信息
    @FormUrlEncoded
    @POST("app/user/getUserinfo")
    Flowable<ResponseData<BindOwnerInfoBean>> getBindOwnernfo(@Field(PACK_NO) String pack_no);

    /**
     * 我的 == 意见反馈
     */
    @Multipart
    @POST("shop/user/feedbackSubmit")
    Flowable<ResponseData<String>> feedbackSubmit(
            @Part MultipartBody.Part params, @PartMap Map<String, RequestBody> imgFiles
    );

    /**
     * 下载文件
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

}

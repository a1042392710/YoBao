package com.jjz.energy.base;

/**
 * create by: CH
 * Date: 2018/9/13 下午2:13
 * 常量
 */
public class Constant {


    /**
     * Intent 传输对象时的key值
     */
    public static final String INTENT_KEY_OBJECT = "bean";
    /**
     * 图片选择器 文件过滤
     */
    public static final String MATISSE_FILE_PATH = "com.jjz.energy.fileprovider";
    /**
     * 定义相册请求码常量
     */
    public static final int REQUEST_CODE_CHOOSE = 23;
    /**
     * 扫一扫的REQUSET_CODE
     */
    public static final int REQUEST_CODE_SCAN = 30;

    public static final String TOAST_NO_MORE_DATA = "没有更多数据";

    public static final String LOGIN_ID = "login_id";
    public static final String APP_KEY = "asd$:ASD*SD$#@Psda13r49shw2%:d;)0D";

    public static final String NETWORK_YES = "1";
    public static final String NETWORK_ERR = "0";
    public static final String NETWORK_NOLOGIN = "-1";

//    ScanCode
    public static final String SCAN_TYPE_SHARE = "share";
    public static final String SCAN_TYPE_ORDER = "order";


    //微信相关数据
    public static final String WX_APP_ID = "wx1a49cbcab5a4c41c";
    public static final String WX_APP_SECRET = "ab718b505aca9871335d4a32c1a67dac";
    //金玖洲隐私协议
    public static final String PRIVACY_POLICY_URL = "http://api.jjznewenergy.com/index/user/userDeal";
    //bugly id
    public static final String BUGLY_ID = "549524cc16";

    /**
     * 推送的消息类型
     */
    public  static  final int NOTICE_ONE = 100;


    /**
     * 商品id
     */
    public static final String GOODS_ID = "goods_id";

    /**
     * 订单编号
     */
    public static final String ORDER_SN = "order_sn";
    /**
     * 用户类型  0 是买主 1 是卖主
     */
    public static final String USER_TYPE = "user_type";
    /**
     * 用户id
     */
    public static final String USER_ID= "user_id";


    /**
     * 支付方式  支付宝
     */

    public static final String PAYTYPE_ALIPAY = "alipayApp";

    /**
     * 支付成功 支付宝
     */
    public static final String PAYSUC_ALIPAY_CODE= "9000";

    /**
     * 支付取消 支付宝
     */
    public static final String PAYCANCLE_ALIPAY_CODE= "6001";

    /**
     * 支付方式  微信
     */
    public static final String PAYTYPE_WECHAT = "appWeixinPay";



}

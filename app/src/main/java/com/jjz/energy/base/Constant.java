package com.jjz.energy.base;

import com.jjz.energy.widgets.idcardcamera.utils.FileUtils;

import java.io.File;

/**
 * create by: CH
 * Date: 2018/9/13 下午2:13
 * 常量
 */
public class Constant {

    //---------------------------- 系统常量

    /**
     * 图片选择器 文件过滤
     */
    public static final String MATISSE_FILE_PATH = "com.jjz.energy.fileprovider";

    /**
     * Intent 传输对象时的key值
     */
    public static final String INTENT_KEY_OBJECT = "bean";

    public static final String LOGIN_ID = "login_id";
    public static final String APP_KEY = "asd$:ASD*SD$#@Psda13r49shw2%:d;)0D";

    public static final String NETWORK_YES = "1";
    public static final String NETWORK_ERR = "0";
    public static final String NETWORK_NOLOGIN = "-1";

    //    ScanCode
    public static final String SCAN_TYPE_SHARE = "share";
    public static final String SCAN_TYPE_ORDER = "order";
    public static final String SCAN_TYPE_SHOP_SHOPPING = "offline";

    /**
     * 存储用户定位 和经纬度
     */
    public static final String LOCATION_ADDRESS = "locationAddress";
    public static final String LOCATION_LAT= "lat";
    public static final String LOCATION_LNG= "lng";

    //微信相关数据
    public static final String WX_APP_ID = "wx1a49cbcab5a4c41c";
    public static final String WX_APP_SECRET = "ab718b505aca9871335d4a32c1a67dac";

    //bugly id
    public static final String BUGLY_ID = "549524cc16";

    //---------------------------------------   Url

    //金玖洲隐私协议
    public static final String PRIVACY_POLICY_URL = "http://api.jjznewenergy.com/index/user/userDeal";
    //商家入驻
    public static final String SHOP_GO = "http://apit.jjznewenergy.com/web";
    //积分规则
    public static final String INTEGRAL_RULE = "http://api.jjznewenergy.com/web/rule";
    //三久官网
    public static final String SANJIU_WEB = "www.sanjiutech.com";

    //--------------------------------------- Intent 请求码

    /**
     * 选择物流公司
     */
    public static final int SELECT_COMPANY_CODE = 40;
    /**
     * 定义相册请求码常量
     */
    public static final int REQUEST_CODE_CHOOSE = 23;
    /**
     * 扫一扫的REQUSET_CODE
     */
    public static final int REQUEST_CODE_SCAN = 30;
    /**
     * 推送的消息类型 订单
     */
    public  static  final int NOTICE_ORDER = 1;
    /**
     * 推送的消息类型 售后
     */
    public  static  final int NOTICE_REFUND = 2;
    /**
     * 推送的消息类型 留言
     */
    public  static  final int NOTICE_MESSAGE = 4;
    /**
     * 推送的消息类型 评论
     */
    public  static  final int NOTICE_COMMENT= 5;
    /**
     * 推送的消息类型  系统
     */
    public  static  final int NOTICE_SYSTEM = 6;

    /**
     * 推送的消息类型  社区
     */
    public  static  final int NOTICE_COMMUNITY = 7;

    //------------------------------- 用户

    /**
     * 用户类型  0 是买主 1 是卖主
     */
    public static final String USER_TYPE = "user_type";
    /**
     * 用户id
     */
    public static final String USER_ID= "user_id";
    /**
     * 商家id
     */
    public static final String SHOP_ID= "shop_id";
    /**
     * 用户 积分
     */
    public static final String PAY_POINTS= "pay_points";
    /**
     * 用户 积分
     */
    public static final String IS_SET_IDCARD= "is_set_idcard";

    /**
     * 搜索的历史记录 商品
     */
    public static final String SEARCH_HISTORY= "search_history";
    /**
     * 搜索的历史记录 商家
     */
    public static final String SEARCH_HISTORY_SHOP= "search_history_shop";


    // ----------------------------------------------- 支付

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


    //------------------------------------------ 订单和售后


    /**
     * 商品id
     */
    public static final String GOODS_ID = "goods_id";
    /**
     * 物流单号
     */
    public static final String SHIPPING_NO = "shipping_no";

    /**
     * 订单编号
     */
    public static final String ORDER_SN = "order_sn";
    /**
     * 商品编号
     */
    public static final String REC_ID = "rec_id";
    /**
     * 售后id
     */
    public static final String RETURN_ID = "return_id";
    /**
     * 售后   仅退款标识
     */
    public static final int RETURN_MONEY = 0;
    /**
     * 售后   退货退款标识
     */
    public static final int RETURN_SALES = 1;


    //身份证识别
    public static final String APP_NAME = "JiuSu";//app名称
    public static final String BASE_DIR = APP_NAME + File.separator;//JiuSu/
    public static final String DIR_ROOT = FileUtils.getRootPath() + File.separator + BASE_DIR;//文件夹根目录 /storage/emulated/0/WildmaIDCardCamera/




}

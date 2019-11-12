package com.jjz.energy.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.blankj.utilcode.util.ToastUtils;
import com.jjz.energy.base.Constant;
import com.jjz.energy.ui.MainActivity;
import com.jjz.energy.ui.home.commodity.CommodityDetailActivity;
import com.jjz.energy.ui.jiusu_shop.shop_order.EvaluateDetailsActivity;
import com.jjz.energy.ui.jiusu_shop.shop_order.ExpressDetailsActivity;
import com.jjz.energy.ui.jiusu_shop.shop_order.OrderDetailsActivity;
import com.jjz.energy.ui.jiusu_shop.shop_order.refund_order.BuyerRefundDetailsActivity;
import com.jjz.energy.ui.jiusu_shop.shop_order.refund_order.SellerRefundDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "陈大帅的推送";
    /**
     * 通知类型 1 订单 2 物流  3.留言评论 4系统通知
     */
    public static final String NOTICE_TYPE = "type";
    /**
     * 查询的Id
     */
    public static final String VALUE_ID = "id";
    /**
     * 用户类型 买家 还是 卖家
     */
    public static final String USER_TYPE = "user_type";

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Bundle bundle = intent.getExtras();
            LogUtil.d(TAG, "[MyReceiver]  " + intent.getAction() + ", extras: " + printBundle(bundle));
            //通知类型
            int type ;
            //查询Id
            String id = "";
            //用户类型
            String user_type ;
            //循环取出消息里面的数据
            JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            type = json.getInt(NOTICE_TYPE);
            id = json.getString(VALUE_ID);

            user_type = json.getString(USER_TYPE);

            LogUtil.e("通知类型", type+"id："+id);

            //接收到推送下来的自定义消息
            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                LogUtil.e(TAG, "推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

                //接收到推送下来的通知
            }else if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                LogUtil.e("MyReceiver推送Id", regId);


            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                LogUtil.e(TAG, "推送下来的通知");

                // 用户点击打开了通知
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                ToastUtils.showShort("用户打开了一个通知");
                goActivity(context,type,id,user_type);

            } else {
                LogUtil.e(TAG, "Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goActivity(Context context, int type, String id, String user_type) {
        Intent i;
        switch (type) {
            //推送类型 订单
            case Constant.NOTICE_ORDER:
                // 0 买家 1卖家
                i = new Intent(context, OrderDetailsActivity.class).putExtra(Constant.ORDER_SN,
                        id).putExtra(Constant.USER_TYPE, user_type.equals("buyer") ? 0 : 1);
                break;
            //推送类型 售后
            case Constant.NOTICE_REFUND:
                i = new Intent(context, user_type.equals("buyer") ?
                        BuyerRefundDetailsActivity.class : SellerRefundDetailsActivity.class).putExtra(Constant.RETURN_ID, id);
                break;
            //推送类型 物流
            case Constant.NOTICE_LOGISTICE:
                i = new Intent(context, ExpressDetailsActivity.class).putExtra(Constant.SHIPPING_NO,id);
                break;
            //推送类型 留言
            case Constant.NOTICE_MESSAGE:
                i = new Intent(context, CommodityDetailActivity.class).putExtra(Constant.GOODS_ID,id);
                break;

            //推送类型 查看评价
            case Constant.NOTICE_COMMENT:
                i = new Intent(context, EvaluateDetailsActivity.class).putExtra(Constant.ORDER_SN, id);
                break;

            //推送类型 系统消息
            case Constant.NOTICE_SYSTEM:
                i = new Intent(context, CommodityDetailActivity.class).putExtra(Constant.GOODS_ID, id);
                break;
            default:
                //不清楚通知类型的全跳首页
                i = new Intent(context, MainActivity.class);
                break;
        }
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtil.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

}

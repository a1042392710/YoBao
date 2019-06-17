package com.jjz.energy.wxapi;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author chenhao 2019/1/17
 * @function 微信支付
 */
public class WxPayUtil {

    /**
     * 支付
     *
     * @param data
     */
    public void pay(Context context, OrderPayTypeBean data, String payType) {
        String appid = data.getWx().getAppid();
        String partnerid = data.getWx().getPartnerid();
        String prepayid = data.getWx().getPrepayid();
        String noncestr = data.getWx().getNoncestr();
        String timestamp = data.getWx().getTimestamp();
        String sign = data.getWx().getSign();
        String packages = data.getWx().getPackages();

        /*微信支付*/
        IWXAPI mApi = WXAPIFactory.createWXAPI(context, null);
        // 判断用户有没有安装微信
        if (mApi.isWXAppInstalled()) {
            // 将AppId注册到微信
            mApi.registerApp(appid);
            PayReq mPayReq = new PayReq();
            mPayReq.appId = appid;
            mPayReq.partnerId = partnerid;
            mPayReq.prepayId = prepayid;
            mPayReq.nonceStr = noncestr;
            mPayReq.timeStamp = timestamp;
            mPayReq.packageValue = packages;
            mPayReq.sign = sign;
            mPayReq.extData = payType;
            mApi.sendReq(mPayReq);
        } else {
            ToastUtils.showShort("请先安装微信客户端");
        }

    }
}

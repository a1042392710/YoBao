package com.jjz.energy.wxapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jjz.energy.base.Api;
import com.jjz.energy.base.Constant;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private final String TAG = this.getClass().getSimpleName();
    private IWXAPI mApi;

    private String openids;
    private String nickname;
    private String headimgurl;

    private String weixin_share;//表示微信分享

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, true);
        mApi.handleIntent(this.getIntent(), this);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    // 微信回调方法
    @Override
    public void onReq(BaseReq req) {
//        ToastUitl.showShort(WXEntryActivity.this, "微信回调到了这里");
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:

                goToGetMsg();
//                ToastUtil.TextToast(" goToGetMsg();");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
//                ToastUtil.TextToast(" goToShowMsg((ShowMessageFromWX.Req) req);");
                break;
            default:
                break;
        }

    }

    private void goToGetMsg() {

    }

    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;

        StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);
    }

    // 发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                // 发送成功
                if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    // 发送成功
                    SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                    if (sendResp != null) {
                        String code = sendResp.code;
                        getAccess_token(code);
                        LogUtils.e("sendRespcode", "" + code);
                    }
                } else if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
//                    ToastUtils.showShort("分享成功");
                    EventBus.getDefault().post(new LoginEventBean(LoginEventBean.SHARE_SUC));
                    finish();
                    return;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                LogUtils.e("sendRespcode", "发送取消");
                // 发送取消
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                LogUtils.e("sendRespcode", "发送被拒绝");
                // 发送被拒绝
                finish();
                break;
        }
        WXEntryActivity.this.finish();



    }


    /**
     * 获取openid accessToken值用于后期操作
     *
     * @param code
     * 请求码
     */
    String openid;
    String access_token;

    private void getAccess_token(final String code) {

        new Thread(() -> {
            String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                    + Constant.WX_APP_ID
                    + "&secret="
                    + Constant.WX_APP_SECRET
                    + "&code="
                    + code
                    + "&grant_type=authorization_code";
            try {
                String jsonObject = new String(Utils.getHtmlByteArray(path));
                if (null != jsonObject) {
                    AccessTokenBean accessTokenBean = JSON.parseObject(jsonObject, AccessTokenBean.class);
                    access_token = accessTokenBean.getAccess_token();
                    String openid = accessTokenBean.getOpenid();
                    getUserMesg(access_token, openid);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    /**
     * 微信登录 获取用户信息
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token + "&openid=" + openid;
        try {
            String jsonObjects = new String(Utils.getHtmlByteArray(path));
            if (!StringUtils.isEmpty(jsonObjects)) {
                LogUtils.e("userinfor", jsonObjects + "");
                UserInfoBean userInfoBean = JSON.parseObject(jsonObjects, UserInfoBean.class);

                openids = userInfoBean.getOpenid();
                nickname = userInfoBean.getNickname();
                headimgurl = userInfoBean.getHeadimgurl();
                //提交微信授权信息
                submitWexinData(userInfoBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return;

    }

    /**
     * 提交微信授权登录信息
     */
    @SuppressLint("CheckResult")
    private void submitWexinData(UserInfoBean userInfoBean){

        HashMap<String,String>map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("openid", userInfoBean.getOpenid());
        map.put("nick_name", userInfoBean.getNickname());
        map.put("head_img", userInfoBean.getHeadimgurl());
        map.put("unionid", userInfoBean.getUnionid());
        map.put("sex", userInfoBean.getSex()+"");
        map.put("type", "wechat");
        //微信授权提交
        RetrofitFactory.getRetrofit().create(Api.class).putBindInfo(PacketUtil.getRequestPacket(map)).compose(RxSchedulerHepler.handleMyResult()).subscribeWith(new CommonSubscriber<String>() {
            @Override
            protected void onFail(String errorMsg,boolean isNetError) {
                ToastUtils.showShort(errorMsg);
            }

            @Override
            protected void onSuccess(String response) {
                LoginEventBean loginEventBean = new LoginEventBean(LoginEventBean.WECHAT_LOG_IN);
                loginEventBean.setValue(userInfoBean.getNickname());
                EventBus.getDefault().post(loginEventBean);
            }
            @Override
            protected void startLoading() {}
        });
    }


}

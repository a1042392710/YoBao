package com.jjz.energy.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.Constant;
import com.jjz.energy.base.LoginEventBean;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * @ author Ch
 * @ date  2019/1/3  17:12
 * @ fuction
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI mApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID);
        mApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApi.detach();
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        PayResp payResp= (PayResp) resp;
        String payType = payResp.extData;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK: {// 支付成功
                showToast("支付成功");
                if ("shop".equals(payType)){
                    EventBus.getDefault().post(new LoginEventBean(LoginEventBean.WEIXIN_PAYSUC));
                }else if ("vip".equals(payType)){
                    EventBus.getDefault().post(new LoginEventBean(LoginEventBean.WEIXIN_VIP_PAYSUC));
                }


                break;
            }
            case BaseResp.ErrCode.ERR_USER_CANCEL: {// 支付取消

                showToast("支付取消");
                break;
            }
            case BaseResp.ErrCode.ERR_AUTH_DENIED: {// 支付被拒绝
                showToast("支付被拒绝");
                break;
            }
            default: {// 支付失败
                showToast("支付失败");
                break;
            }
        }
        WXPayEntryActivity.this.finish();
    }

    private void showToast(String msg) {
        Toast.makeText(BaseApplication.getAppContext(),msg,Toast.LENGTH_SHORT).show();
    }
}

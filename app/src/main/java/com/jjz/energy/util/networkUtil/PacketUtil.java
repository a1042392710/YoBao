package com.jjz.energy.util.networkUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.system.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ author FX
 * @ date  2018/12/7  08:52
 * @ fuction  拼接请求的工具类
 */
public class PacketUtil {
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getRequestPacket(Object data) {
        String username = "";//用户名
        String token = "";//token
        String decode_token = "";//解密后的token
        String result = "";
        JSONObject rows = new JSONObject();
        try {
            long date = System.currentTimeMillis();/*当前系统时间*/
            if (!StringUtils.isEmpty(SpUtil.init(BaseApplication.getAppContext()).readString(Constant.LOGIN_ID))) {//用户ID
                decode_token = SpUtil.init(BaseApplication.getAppContext()).readString(Constant.LOGIN_ID);
            }


            // 请求时间
            rows.put("time", date + "");

            String uuid = UUID.randomUUID().toString();
            //随机字符串
            rows.put("nonce_str", uuid);
            //设备ID
            TelephonyManager manager = (TelephonyManager) BaseApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
            try {
                if (ActivityCompat.checkSelfPermission(BaseApplication.getAppContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    assert manager != null;
                    if (StringUtils.isEmpty(manager.getDeviceId())) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            rows.put("device_info", manager.getDeviceId(0));
                        }
                    } else {
                        rows.put("device_info", manager.getDeviceId());
                    }
                }
            } catch (Exception ignored) {
                rows.put("device_info", "");
            }


            // 用户ID
            if (UserLoginBiz.getInstance(BaseApplication.getAppContext()).detectUserLoginStatus()) {//获取用户登录信息
                UserInfo loginBean = UserLoginBiz.getInstance(BaseApplication.getAppContext()).readUserInfo();
                if (loginBean != null) {
                    if (!StringUtils.isEmpty(loginBean.getMobile())) {
                        username = loginBean.getMobile();
                    }
                }
            }
            rows.put("username", username);//用户名,手机号(后台暂定)
            if (!StringUtils.isEmpty(decode_token)) {//解密后的token 再次加密
                token = AesUtils.encrypt(decode_token + date, AesUtils.KEY, AesUtils.IV);
            }
            rows.put("token", token);//后台给的token值，没有就为空

            assert manager != null;
            String sign = Utils.getSign(date, uuid, manager.getDeviceId(), username, token);//Sign签名
            rows.put("sign", sign);//

            if (data instanceof String) {
                rows.put("data", new JSONObject((String) data));
            } else if (data instanceof AtomicReference) {
                rows.put("data", new JSONObject((Map) ((AtomicReference) data).get()));
            } else if (data instanceof Map) {
                rows.put("data", data == null ? new JSONObject() : new JSONObject((Map) data));
            } else {
                JSONObject jsonObject = (JSONObject) data;
                rows.put("data", data == null ? new JSONObject() : jsonObject);
            }
            result = StringUtil.toUTF_8(rows.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LogUtils.e("报文", result);
        return result;
//        }
    }

}

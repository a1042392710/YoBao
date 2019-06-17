package com.jjz.energy.base;

import java.io.Serializable;

/**
 * Created by chenhao on 2018/8/28.
 */

public class BaseRequest implements Serializable {
    public long time;
    public String nonce_str = "";
    public String device_info = "";
    public String username = "";
    public String token = "";
    public String sign = "";

    public BaseRequest(long time, String nonce_str, String device_info, String username, String token, String sign) {
        this.time = time;
        this.nonce_str = nonce_str;
        this.device_info = device_info;
        this.username = username;
        this.token = token;
        this.sign = sign;
    }
}

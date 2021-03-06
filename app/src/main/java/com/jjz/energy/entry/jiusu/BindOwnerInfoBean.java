package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 车主信息
 * @author: create by chenhao on 2019/4/12
 */
public class BindOwnerInfoBean implements Serializable {


    private String user_idcard;
    private String user_name;
    private String license_plate;
    private String idcard_front;
    private String idcard_back;

    public String getIdcard_front() {
        return idcard_front == null ? "" : idcard_front;
    }

    public void setIdcard_front(String idcard_front) {
        this.idcard_front = idcard_front;
    }

    public String getIdcard_back() {
        return idcard_back == null ? "" : idcard_back;
    }

    public void setIdcard_back(String idcard_back) {
        this.idcard_back = idcard_back;
    }

    public String getUser_idcard() {
        return user_idcard == null ? "" : user_idcard;
    }

    public void setUser_idcard(String user_idcard) {
        this.user_idcard = user_idcard;
    }

    public String getUser_name() {
        return user_name == null ? "" : user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getLicense_plate() {
        return license_plate == null ? "" : license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }
}

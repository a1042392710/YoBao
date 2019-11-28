package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 查看发票
 * @author: create by chenhao on 2019/7/3
 */
public class BillEntry implements Serializable {

    private String img_url;
    private String status;

    public String getImg_url() {
        return img_url == null ? "" : img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

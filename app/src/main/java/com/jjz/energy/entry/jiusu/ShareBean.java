package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @author FX
 * @date 2017/10/18  11:01
 * @fuction 分享
 */
public class ShareBean implements Serializable {
    //图片
    private int img;
    //title
    private String title;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

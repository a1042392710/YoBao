package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features: 广告位
 * @author: create by chenhao on 2019/10/10
 */
public class Banner implements Serializable {

    private String image;
    private int id;

    public String getImage() {
        return image == null ? "" : image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

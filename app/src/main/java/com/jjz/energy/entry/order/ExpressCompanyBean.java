package com.jjz.energy.entry.order;

import java.io.Serializable;

/**
 * @Features: 快递公司
 * @author: create by chenhao on 2019/10/10
 */
public class ExpressCompanyBean implements Serializable {
    /**
     * 类别id
     */
    private String id ;
    /**
     * 分类名称
     */
    private String name;


    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

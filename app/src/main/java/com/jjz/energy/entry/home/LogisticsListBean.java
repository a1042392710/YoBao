package com.jjz.energy.entry.home;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 物流
 * @author: create by chenhao on 2020/3/16
 */
public class LogisticsListBean implements Serializable {

    private List<LogisticsBean> list;

    public List<LogisticsBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<LogisticsBean> list) {
        this.list = list;
    }
}

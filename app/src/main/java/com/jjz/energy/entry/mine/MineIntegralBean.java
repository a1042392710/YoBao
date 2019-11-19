package com.jjz.energy.entry.mine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 我的 积分
 * @author: create by chenhao on 2019/11/16
 */
public class MineIntegralBean implements Serializable {

    private float points;

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    private List<ListBean> list ;

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean implements Serializable {

        private String pay_points;
        private String desc;
        private long change_time;

        public String getPay_points() {
            return pay_points == null ? "" : pay_points;
        }

        public void setPay_points(String pay_points) {
            this.pay_points = pay_points;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public long getChange_time() {
            return change_time;
        }

        public void setChange_time(long change_time) {
            this.change_time = change_time;
        }
    }
}

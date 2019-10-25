package com.jjz.energy.entry.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 物流跟踪
 * @author: create by chenhao on 2019/10/25
 */
public class ExpressTrackingBean implements Serializable {


    //图片
    private String goods_images;
    //状态
    private String state;
    //单号
    private String shipping_no;
    //公司
    private String shipping_name;
    /**
     * 物流信息
     */
    private List<Traces> traces;

    public String getShipping_no() {
        return shipping_no == null ? "" : shipping_no;
    }

    public void setShipping_no(String shipping_no) {
        this.shipping_no = shipping_no;
    }

    public String getShipping_name() {
        return shipping_name == null ? "" : shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getGoods_images() {
        return goods_images == null ? "" : goods_images;
    }

    public void setGoods_images(String goods_images) {
        this.goods_images = goods_images;
    }

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Traces> getTraces() {
        if (traces == null) {
            return new ArrayList<>();
        }
        return traces;
    }

    public void setTraces(List<Traces> traces) {
        this.traces = traces;
    }

    public  class Traces implements Serializable{

        String AcceptStation;
        String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation == null ? "" : AcceptStation;
        }

        public void setAcceptStation(String acceptStation) {
            AcceptStation = acceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime == null ? "" : AcceptTime;
        }

        public void setAcceptTime(String acceptTime) {
            AcceptTime = acceptTime;
        }
    }

}

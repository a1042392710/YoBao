package com.jjz.energy.wxapi;

import java.io.Serializable;

/**
 * @author chenhao 2019/1/17
 * @function 获取微信和支付宝的支付信息
 */
public class OrderPayTypeBean implements Serializable {
    /**
     * wx : {"appid":"wx2d2193774e25e72a","partnerid":"1490210132","prepayid":"wx1517565433094193645e73a73343258327","noncestr":"fIvAxjBHjmoODGSqjfkF","timestamp":1547546214,"sign":"2DE9F46A3E1D5D7EAA2C8DF0205ECF6C","order_id":"2019010409325495021547546214","packages":"Sign=WXPay"}
     */

    private WxBean wx;

    public String getZfb() {
        return zfb == null ? "" : zfb;
    }

    public void setZfb(String zfb) {
        this.zfb = zfb;
    }

    private String zfb;
    private String order_sn;

    public String getOrder_sn() {
        return order_sn == null ? "" : order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public WxBean getWx() {
        return wx;
    }

    public void setWx(WxBean wx) {
        this.wx = wx;
    }

    public  class WxBean implements Serializable{
        /**
         * appid : wx2d2193774e25e72a
         * partnerid : 1490210132
         * prepayid : wx1517565433094193645e73a73343258327
         * noncestr : fIvAxjBHjmoODGSqjfkF
         * timestamp : 1547546214
         * sign : 2DE9F46A3E1D5D7EAA2C8DF0205ECF6C
         * order_id : 2019010409325495021547546214
         * packages : Sign=WXPay
         */
        private String appid;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;
        private String sign;
        private String order_id;
        private String packages;


        public String getTimestamp() {
            return timestamp == null ? "" : timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }


        public String getAppid() {
            return appid == null ? "" : appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid == null ? "" : partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid == null ? "" : prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr == null ? "" : noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }


        public String getSign() {
            return sign == null ? "" : sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getOrder_id() {
            return order_id == null ? "" : order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getPackages() {
            return packages == null ? "" : packages;
        }

        public void setPackages(String packages) {
            this.packages = packages;
        }
    }
}

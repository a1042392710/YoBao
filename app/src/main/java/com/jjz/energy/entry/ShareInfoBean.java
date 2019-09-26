package com.jjz.energy.entry;

import java.io.Serializable;

public class ShareInfoBean implements Serializable {
        /**
         * rand_title : 华源商城 只卖正品好货
         * rand_desc : 这个世界多数人想买正品，少数人想买精品，再小的个体也有改善生活品质的权利
         * share_url : http://hyuansc.com/mobile.php?m=Mobile&c=index&a=discount
         * share_img : http://hyuansc.comimages/logo.png
         */

        private String rand_title;
        private String rand_desc;
        private String share_url;
        private String share_img;
        private String tj_code;

    public String getTj_code() {
        return tj_code == null ? "" : tj_code;
    }

    public void setTj_code(String tj_code) {
        this.tj_code = tj_code;
    }

    public String getRand_title() {
            return rand_title == null ? "" : rand_title;
        }

        public void setRand_title(String rand_title) {
            this.rand_title = rand_title;
        }

        public String getRand_desc() {
            return rand_desc == null ? "" : rand_desc;
        }

        public void setRand_desc(String rand_desc) {
            this.rand_desc = rand_desc;
        }

        public String getShare_url() {
            return share_url == null ? "" : share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getShare_img() {
            return share_img == null ? "" : share_img;
        }

        public void setShare_img(String share_img) {
            this.share_img = share_img;
        }
    }
package com.jjz.energy.entry.mine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features:
 * @author: create by chenhao on 2019/11/9
 */
public class OrderNoticeBean implements Serializable {

    private List<ListBean> list;

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

        private int id;
        private String title;
        private String img_url;
        private int goods_id;
        private int order_id;
        private String order_sn;
        private int rec_id;
        private String return_id;
        private int user_id;
        private String content;
        private int type;
        private int status;
        private long add_time;
        private String user_type;
        private String shipping_no;
        private String nickname;
        private String head_pic;

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_pic() {
            return head_pic == null ? "" : head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getShipping_no() {
            return shipping_no == null ? "" : shipping_no;
        }

        public void setShipping_no(String shipping_no) {
            this.shipping_no = shipping_no;
        }

        public String getUser_type() {
            return user_type == null ? "" : user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg_url() {
            return img_url == null ? "" : img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn == null ? "" : order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getRec_id() {
            return rec_id;
        }

        public void setRec_id(int rec_id) {
            this.rec_id = rec_id;
        }

        public String getReturn_id() {
            return return_id == null ? "" : return_id;
        }

        public void setReturn_id(String return_id) {
            this.return_id = return_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }
    }
}

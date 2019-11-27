package com.jjz.energy.entry.commodity;

import java.io.Serializable;


/**
 * 商品的一些信息
 */
public class GoodsBean implements Serializable {


    /**
     * 商品Id
     */
    private int goods_id;
    /**
     * 商品名称
     */
    private String goods_name;
    /**
     * 商品价格
     */
    private double shop_price;
    /**
     * 商品图片
     */
    private String goods_images;
    /**
     * 多少人喜欢
     */
    private int collect_sum;
    /**
     * 用户名称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String head_pic;
    //邮费
    private int shopping_price;
    //原价
    private String market_price;

    private int address_id;

    //收货地址
    private String full_address;
    /**
     * 折扣
     */
    private float rebate;

    public float getRebate() {
        return rebate;
    }

    public void setRebate(float rebate) {
        this.rebate = rebate;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getFull_address() {
        return full_address == null ? "" : full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public int getShopping_price() {
        return shopping_price;
    }

    public void setShopping_price(int shopping_price) {
        this.shopping_price = shopping_price;
    }

    public String getMarket_price() {
        return market_price == null ? "" : market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getShop_price() {
            return shop_price;
        }

        public void setShop_price(double shop_price) {
            this.shop_price = shop_price;
        }

        public String getGoods_images() {
            return goods_images == null ? "" : goods_images;
        }

        public void setGoods_images(String goods_images) {
            this.goods_images = goods_images;
        }

        public int getCollect_sum() {
            return collect_sum;
        }

        public void setCollect_sum(int collect_sum) {
            this.collect_sum = collect_sum;
        }

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
    }
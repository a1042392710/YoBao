package com.jjz.energy.entry.commodity;

import java.io.Serializable;

/**
     * 商家商品
     */
  public class ShopCommodityListBean implements Serializable {

      private int goods_id;
      private String goods_name;
      private String goods_images;
      private String shop_price;
      private String market_price;
      private float rebate;
        /**
         * 多少人喜欢
         */
        private int collect_sum;

        public int getCollect_sum() {
            return collect_sum;
        }

        public void setCollect_sum(int collect_sum) {
            this.collect_sum = collect_sum;
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

        public String getGoods_images() {
            return goods_images == null ? "" : goods_images;
        }

        public void setGoods_images(String goods_images) {
            this.goods_images = goods_images;
        }

        public String getShop_price() {
            return shop_price == null ? "" : shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getMarket_price() {
            return market_price == null ? "" : market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

    public float getRebate() {
        return rebate;
    }

    public void setRebate(float rebate) {
        this.rebate = rebate;
    }
}
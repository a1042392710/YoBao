package com.jjz.energy.entry.jiusu_shop;

import com.jjz.energy.entry.commodity.HomePageCommentBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 久速商家 个人主页
 * @author: create by chenhao on 2019/11/11
 */
public class ShopHomePageBean implements Serializable {

    private int total_num;
    private int have_img_num;
    private int good_num;
    private int bad_num;

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getHave_img_num() {
        return have_img_num;
    }

    public void setHave_img_num(int have_img_num) {
        this.have_img_num = have_img_num;
    }

    public int getGood_num() {
        return good_num;
    }

    public void setGood_num(int good_num) {
        this.good_num = good_num;
    }

    public int getBad_num() {
        return bad_num;
    }

    public void setBad_num(int bad_num) {
        this.bad_num = bad_num;
    }

    private List<HomePageCommentBean.ListBean> commentList;

    private List<CommodityListBean> commodityList;

    public List<CommodityListBean> getCommodityList() {
        if (commodityList == null) {
            return new ArrayList<>();
        }
        return commodityList;
    }

    public void setCommodityList(List<CommodityListBean> commodityList) {
        this.commodityList = commodityList;
    }

    public List<HomePageCommentBean.ListBean> getCommentList() {
        if (commentList == null) {
            return new ArrayList<>();
        }
        return commentList;
    }

    public void setCommentList(List<HomePageCommentBean.ListBean> commentList) {
        this.commentList = commentList;
    }

    /**
     * 商品
     */
  public   class CommodityListBean implements Serializable{

    }

}

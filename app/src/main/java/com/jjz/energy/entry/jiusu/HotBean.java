package com.jjz.energy.entry.jiusu;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Features: 新闻列表
 * @author: create by chenhao on 2019/4/10
 */
public class HotBean implements MultiItemEntity {
    /**
     * 布局类型 1 大布局 0 小布局
     */
    private int is_top;
    /**
     * 新闻主键
     */
    private int news_id;
    /**
     * 图片
     */
    private String header_image;
    /**
     * 新闻时间
     */
    private String create_time;
    /**
     * 新闻标题
     */
    private String news_title;
    /**
     * 新闻小标题
     */
    private String news_desc;

    public String getNews_desc() {
        return news_desc == null ? "" : news_desc;
    }

    public void setNews_desc(String news_desc) {
        this.news_desc = news_desc;
    }

    public String getNews_title() {
        return news_title == null ? "" : news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getHeader_image() {
        return header_image == null ? "" : header_image;
    }

    public void setHeader_image(String header_image) {
        this.header_image = header_image;
    }

    public String getCreate_time() {
        return create_time == null ? "" : create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    @Override
    public int getItemType() {
        return is_top;
    }

}

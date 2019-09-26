package com.jjz.energy.entry;

/**
 * @Features: 新闻详情
 * @author: create by chenhao on 2019/4/10
 */
public class HotDetailBean  {
    private  String   news_id;
    private  String   news_title;
    private  String   header_image;//	标题图片
    private  String  news_desc	;//副标题
    private  String  news_content;//内容
    private  String  outside_url;//	站外地址
    private  String  create_time;//	创建时间
    private  String   update_time;	//修改时间
    private  String  is_top;//	是否置顶 1是 0否
    private  String  is_show;//	是否显示

    public String getNews_id() {
        return news_id == null ? "" : news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
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

    public String getNews_desc() {
        return news_desc == null ? "" : news_desc;
    }

    public void setNews_desc(String news_desc) {
        this.news_desc = news_desc;
    }

    public String getNews_content() {
        return news_content == null ? "" : news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getOutside_url() {
        return outside_url == null ? "" : outside_url;
    }

    public void setOutside_url(String outside_url) {
        this.outside_url = outside_url;
    }

    public String getCreate_time() {
        return create_time == null ? "" : create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time == null ? "" : update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getIs_top() {
        return is_top == null ? "" : is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public String getIs_show() {
        return is_show == null ? "" : is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }
}

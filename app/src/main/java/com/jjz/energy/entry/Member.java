package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features:
 * @author: create by chenhao on 2019/4/8
 */
public class Member implements Serializable {

    /**
     * 推广的非会员数量
     */
    private int push_level_1 ;
    /**
     * 体验会员
     */
    private int push_level_2 ;
    /**
     * 经销商
     */
    private int push_level_3 ;
    /**
     * 加盟商
     */
    private int push_level_4 ;
    /**
     * 服务商
     */
    private int push_level_5 ;

    public int getPush_level_1() {
        return push_level_1;
    }

    public void setPush_level_1(int push_level_1) {
        this.push_level_1 = push_level_1;
    }

    public int getPush_level_2() {
        return push_level_2;
    }

    public void setPush_level_2(int push_level_2) {
        this.push_level_2 = push_level_2;
    }

    public int getPush_level_3() {
        return push_level_3;
    }

    public void setPush_level_3(int push_level_3) {
        this.push_level_3 = push_level_3;
    }

    public int getPush_level_4() {
        return push_level_4;
    }

    public void setPush_level_4(int push_level_4) {
        this.push_level_4 = push_level_4;
    }

    public int getPush_level_5() {
        return push_level_5;
    }

    public void setPush_level_5(int push_level_5) {
        this.push_level_5 = push_level_5;
    }
}

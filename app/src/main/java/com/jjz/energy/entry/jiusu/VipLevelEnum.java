package com.jjz.energy.entry.jiusu;

/**
 * @author chenhao 2018/11/8
 * @function 会员等级
 */
public enum VipLevelEnum {

    //0 无会员
    NOVIP("普通会员", 1), VIP_ONE("VIP", 2), VIP_TWO("VIP2", 3), VIP_THREE("VIP3",4), VIP_FOUR("代理服务商", 5);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    VipLevelEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (VipLevelEnum c : VipLevelEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // 普通方法
    public static int getIndex(String name) {
        for (VipLevelEnum c : VipLevelEnum.values()) {
            if (c.getName() == name) {
                return c.index;
            }
        }
        return -1;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
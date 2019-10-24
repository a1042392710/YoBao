package com.jjz.energy.entry.enums;

/**
 * @author chenhao 2018/11/8
 * @function 订单状态
 */
public enum ShopOrderStatusEnum {

    //订单状态（0待支付，1待发货,2待收货，3待评价，4交易关闭，5交易完成）
    TO_BE_PAID("待支付", 0),TO_SEND_GOODS("待发货", 1), PENDING_RECEIPT("待收货", 2),TO_EVALUATE("待评价", 3),TRADING_CLOSED("交易关闭",4),SUCCESSFUL_TRANSACTION("交易成功", 5);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    ShopOrderStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (ShopOrderStatusEnum c : ShopOrderStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // 普通方法
    public static int getIndex(String  name) {
        for (ShopOrderStatusEnum c : ShopOrderStatusEnum.values()) {
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
package com.jjz.energy.entry.enums;

/**
 * @author chenhao 2018/11/8
 * @function 订单状态
 */
public enum OrderStatusEnum {

    //0待支付 1待确认 2待收货 3交易关闭 4交易成功
    TO_BE_PAID("待支付", 0),TO_BE_DETERMINED("待确认", 1), PENDING_RECEIPT("待收货", 2),TRADING_CLOSED("交易关闭",3),SUCCESSFUL_TRANSACTION("交易成功", 4);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    OrderStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (OrderStatusEnum c : OrderStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // 普通方法
    public static int getIndex(String  name) {
        for (OrderStatusEnum c : OrderStatusEnum.values()) {
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
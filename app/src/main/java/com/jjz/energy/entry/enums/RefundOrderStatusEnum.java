package com.jjz.energy.entry.enums;

/**
 * @author chenhao 2018/11/8
 * @function 售后订单状态
 */
public enum RefundOrderStatusEnum {
//-2用户取消-1不同意0待审核1通过2已发货5退款完成
    CANCEL("买家取消退款申请", -2),SELLER_REFUSE("卖家拒绝退款申请", -1), WAIT_AUDIT("等待卖家审核", 0)
    ,WAIT_BUYER_SHIPMENTS("等待买家退货",1),WAIT_SELLER_RECEIVING ("买家已退货，等待卖家收货", 2),RETURN_SUC ("退款完成", 5);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    RefundOrderStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (RefundOrderStatusEnum c : RefundOrderStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // 普通方法
    public static int getIndex(String  name) {
        for (RefundOrderStatusEnum c : RefundOrderStatusEnum.values()) {
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
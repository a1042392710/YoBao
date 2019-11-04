package com.jjz.energy.entry.order;

import java.io.Serializable;

/**
 * @author chenhao 2019/1/8
 * @function 退货/退款类型
 */
public class RefundTypeBean implements Serializable {

    private int id;

    private String value;
    /**
     * 是否选中
     */
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 发票内容
 * @author: create by chenhao on 2019/7/3
 */
public class BillContentEntry implements Serializable {

    private int isBillUnit = 1;

    public int getIsBillUnit() {
        return isBillUnit;
    }

    public void setIsBillUnit(int isBillUnit) {
        this.isBillUnit = isBillUnit;
    }

    private String title;
    private String type;
    private String content;
    private String money;
    private String time;
    private String identifier;
    private String address;
    private String phone;
    private String bank;

    public String getOrder_id() {
        return order_id == null ? "" : order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    private String order_id;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoney() {
        return money == null ? "" : money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdentifier() {
        return identifier == null ? "" : identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank == null ? "" : bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank_number() {
        return bank_number == null ? "" : bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    private String bank_number;

}

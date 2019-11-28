package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 提现信息
 * @author: create by chenhao on 2019/10/26
 */
public class WithdrawInfoBean  implements Serializable {


    private String real_name;
    private String bank_name;
    private String bank_card;
    private String bank_phone;
    private String iccard;

    public String getReal_name() {
        return real_name == null ? "" : real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getBank_name() {
        return bank_name == null ? "" : bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_card() {
        return bank_card == null ? "" : bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getBank_phone() {
        return bank_phone == null ? "" : bank_phone;
    }

    public void setBank_phone(String bank_phone) {
        this.bank_phone = bank_phone;
    }

    public String getIccard() {
        return iccard == null ? "" : iccard;
    }

    public void setIccard(String iccard) {
        this.iccard = iccard;
    }
}

package com.jjz.energy.entry.jiusu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 我的代理
 * @author: create by chenhao on 2019/4/9
 */
public class AgencyBean implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean implements Serializable {

        private String mobile;
        private String nickname;
        private int reg_time;

        public int getReg_time() {
            return reg_time;
        }

        public void setReg_time(int reg_time) {
            this.reg_time = reg_time;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

    }


}

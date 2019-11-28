package com.jjz.energy.entry.jiusu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 提现详情记录
 * @author: create by chenhao on 2019/4/26
 */
public class CommissionDetailBean implements Serializable {


    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    private List<ListBean> list;


    public class ListBean implements Serializable {

        /**
         * 佣金状态
         *('insufficient 等级不足','apply 可提取','applying 提取中',
         * 'applyed 已提取','timeout 过期','cancel 关闭等级到头 100000级别不能提取')
         */
        private String status;
        /**
         * 佣金提供者的昵称
         */
        private String nickname;
        /**
         * 金额
         */
        private String commission_money;
        private int base_user_id;
        private int get_user_id;
        private long create_time;
        private String type;

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getBase_user_id() {
            return base_user_id;
        }

        public void setBase_user_id(int base_user_id) {
            this.base_user_id = base_user_id;
        }

        public int getGet_user_id() {
            return get_user_id;
        }

        public void setGet_user_id(int get_user_id) {
            this.get_user_id = get_user_id;
        }

        public String getCommission_money() {
            return commission_money == null ? "" : commission_money;
        }

        public void setCommission_money(String commission_money) {
            this.commission_money = commission_money;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}

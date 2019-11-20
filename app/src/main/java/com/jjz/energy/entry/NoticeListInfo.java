package com.jjz.energy.entry;

/**
 * @Features: 最新的消息通知
 * @author: create by chenhao on 2019/11/12
 */
public class NoticeListInfo {


    private Notice order;
    private Notice system;
    private Notice messages;
    private Notice shipping;
    private Notice timeline;

    public Notice getCommunity() {
        return timeline;
    }

    public void setCommunity(Notice timeline) {
        this.timeline = timeline;
    }

    public Notice getMessages() {
        return messages;
    }

    public void setMessages(Notice messages) {
        this.messages = messages;
    }


    public Notice getOrder() {
        return order;
    }

    public void setOrder(Notice order) {
        this.order = order;
    }

    public Notice getSystem() {
        return system;
    }

    public void setSystem(Notice system) {
        this.system = system;
    }


    public Notice getShipping() {
        return shipping;
    }

    public void setShipping(Notice shipping) {
        this.shipping = shipping;
    }

   public class Notice {
        String name;
        int unread_num;
        String message;
        long last_time;

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUnread_num() {
            return unread_num;
        }

        public void setUnread_num(int unread_num) {
            this.unread_num = unread_num;
        }

        public String getMessage() {
            return message == null ? "" : message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getLast_time() {
            return last_time;
        }

        public void setLast_time(long last_time) {
            this.last_time = last_time;
        }
    }
}

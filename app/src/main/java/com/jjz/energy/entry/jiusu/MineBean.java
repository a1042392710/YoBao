package com.jjz.energy.entry.jiusu;

public class MineBean {
        private String title;
        private int img;

        public MineBean(String title, int img) {
            this.title = title;
            this.img = img;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }
    }
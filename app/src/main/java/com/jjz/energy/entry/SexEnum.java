package com.jjz.energy.entry;

/**
 * @author chenhao 2018/11/8
 * @function
 */
public enum SexEnum {

    //1男 2 女
    MAN("男", 1), WOMAN("女", 2);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    SexEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (SexEnum c : SexEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // 普通方法
    public static int getIndex(String  name) {
        for (SexEnum c : SexEnum.values()) {
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
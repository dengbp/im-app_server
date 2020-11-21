package com.yr.net.app.common.constant;

/**
 * @author dengbp
 * @ClassName SexConstant
 * @Description TODO
 * @date 2020-11-22 02:43
 */
public enum  SexConstant {

    /** 男性 */
    mal(1,"男"),
    /** 女性 */
    Female(2,"女");

    private int sex;
    private String  desc;

    SexConstant(int sex, String desc) {
        this.sex = sex;
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public String getDesc() {
        return desc;
    }}

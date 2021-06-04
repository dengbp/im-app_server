package com.yr.net.app.pay.controller.enums;

/**
 * @author dengbp
 * @ClassName SexConstant
 * @Description 交易项目
 * @date 2020-11-22 02:43
 */
public enum ExchangeItem {


    info(0,"用户信息"),
    album(1,"用户相册"),
    moment(2,"用户动态"),
    track(3,"用户活动轨迹");

    private int type;
    private String  desc;

    ExchangeItem(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ExchangeItem getByType(Integer type){
        if(type == null){
            return null;
        }
        for(ExchangeItem e : ExchangeItem.values()){
            if(e.getType()==type){
                return e;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}

package com.yr.net.app.log.enums;

/**
 * @author dengbp
 * @ClassName OperationType
 * @Description 操作类型0：浏览用户信息；1：用户动态；2:主题点赞；3：主题取消点赞 4:评论内容点赞；5：评论内容取消点赞  6：关注；.....
 * @date 2019-12-14 11:06
 */
public enum OperationType {


    VIEW_USER_INFO(0,"浏览用户信息"),
    VIEW_MOMENT(1,"浏览用户动态"),
    THEME_LIKE(2,"主题点赞"),
    THEME_UNLIKE(3,"主题取消点赞"),
    COMMENT_LIKE(4,"评论内容点赞"),
    COMMENT_UNLIKE(5,"评论内容取消点赞"),
    FOLLOW(6,"关注");



    OperationType(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    private final Integer code;
    private final String des;

    public static OperationType getByCode(Integer code){
        if(code == null){
            return null;
        }
        for(OperationType e : OperationType.values()){
            if(e.getCode().equals(code)){
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}

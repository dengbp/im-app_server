package com.yr.net.app.common.entity;


/**
 * 常量
 *
 * @author dengbp
 */
public class AppConstant {

    // user缓存前缀
    public static final String USER_CACHE_PREFIX = "saas.authorize.cache.user.";
    // user角色缓存前缀
    public static final String USER_ROLE_CACHE_PREFIX = "saas.authorize.cache.user.role.";
    // user权限缓存前缀
    public static final String USER_PERMISSION_CACHE_PREFIX = "saas.authorize.cache.user.permission.";
    // user个性化配置前缀
    public static final String USER_CONFIG_CACHE_PREFIX = "saas.authorize.cache.user.config.";

    // token缓存前缀
    public static final String TOKEN_CACHE_PREFIX = "saas.authorize.cache.token.";

    // 排序规则：降序
    public static final String ORDER_DESC = "desc";
    // 排序规则：升序
    public static final String ORDER_ASC = "asc";

    // 验证码 Session Key
    public static final String CODE_PREFIX = "saas.authorize_captcha_";

    // 允许下载的文件类型，根据需求自己添加（小写）
    public static final String[] VALID_FILE_TYPE = {"xlsx", "zip"};

    /**
     *
     * getDataTable 中 HashMap 默认的初始化容量
     */
    public static final int DATA_MAP_INITIAL_CAPACITY = 4;
    /**
     * 异步线程池名称
     */
    public static final String ASYNC_POOL = "saas.authorizeAsyncThreadPool";

    // 菜单
    public static final String TYPE_BUTTON = "1";
    // 按钮
    public static final Integer TYPE_BUTTON2 = 2;
    // 目录
    public static final String TYPE_MENU = "0";

    // 存储在线用户的 zset前缀
    public static final String ACTIVE_USERS_ZSET_PREFIX = "saas.authorize.user.active";

}

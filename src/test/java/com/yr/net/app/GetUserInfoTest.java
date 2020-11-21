package com.yr.net.app;

import cn.wildfirechat.pojos.InputOutputUserInfo;
import cn.wildfirechat.sdk.UserAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import cn.wildfirechat.sdk.utilities.AdminHttpUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * @author dengbp
 * @ClassName GetUserInfoTest
 * @Description TODO
 * @date 2020-11-22 01:16
 */
public class GetUserInfoTest {

    /**
     * Description 根据用户手机取用户信息
     * @param args
     * @return void
     * @Author dengbp
     * @Date 00:26 2020-11-22
     **/
    public static void main(String[] args) {
        AdminHttpUtils.init("http://121.37.181.23:18080", "123456");
        try {
            IMResult<InputOutputUserInfo> userByMobile = UserAdmin.getUserByMobile("13737630742");
            System.out.println("InputOutputUserInfo="+ JSONObject.toJSONString(userByMobile));
            System.out.println("userByMobile.msg="+userByMobile.msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

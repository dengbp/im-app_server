package com.yr.net.app;

import com.yr.net.app.tools.DistanceUtil;

import java.text.DecimalFormat;

/**
 * @author dengbp
 * @ClassName GetDistanceTest
 * @Description 获取距离测试
 * @date 12/26/20 7:36 PM
 */
public class GetDistanceTest {

    public static void main(String[] args) {
        System.out.println(DistanceUtil.getDistance(new Double("113.828358"),new Double("22.752143"),new Double("116.391244079988"),new Double("39.90733345")));


    }
}

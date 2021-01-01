package com.yr.net.app;

import com.yr.net.app.tools.AddressByCoordUtil;

/**
 * @author dengbp
 * @ClassName coordinateTest
 * @Description TODO
 * @date 12/26/20 4:33 PM
 */
public class GetAddTest {

    public static void main(String[] args) {
        System.out.println(AddressByCoordUtil.getAdd("22.752143","113.828358").getFormattedAddress());
    }
}

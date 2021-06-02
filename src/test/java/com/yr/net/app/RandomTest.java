package com.yr.net.app;

import java.util.Random;

/**
 * @author dengbp
 * @ClassName RandomTest
 * @Description TODO
 * @date 6/1/21 1:09 PM
 */
public class RandomTest {
    public static void main(String[] args) {
        for (int i=0;i<100;i++) {
            int max = 482;
            int min = 12;
            Random random = new Random();
            int s = random.nextInt(max) % (max - min + 1) + min;
            System.out.println(s);
        }
    }
}

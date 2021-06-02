package com.yr.net.app.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * @author dengbp
 * @since 2018-07-04
 **/
public class RandomUtil {

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String randomNum(Integer length) {
        if (length < 0 || length > 512) {
            length = 32;
        }
        StringBuilder result = new StringBuilder();
        final String sources = "0123456789";
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            result.append(sources.charAt(rand.nextInt(9)));
        }
        return result.toString();
    }


    /**
     * 生成32位随机数字
     *
     * @param length
     * @return
     */
    public static String randomStr(Integer length) {
        if (length <= 0 || length > 32) {
            length = 32;
        }
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    /**
     * 获取当前时间戳，单位秒(10位)
     **/
    public static long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取带格式的日期字符串
     *
     * @param length
     * @return
     */
    public static String getDateStr(Integer length) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String result = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        if (length < 1 || length > 17) {
            return result;
        } else {
            return result.substring(0, length);
        }
    }


    private static int max = 482;
    private static int min = 12;
    /**
     * Description 指定范围内取随机数
     * @param
     * @return int
     * @Author dengbp
     * @Date 1:15 PM 6/1/21
     **/
    public static int getRandom(){
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

}

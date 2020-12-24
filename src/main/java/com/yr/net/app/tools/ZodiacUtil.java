package com.yr.net.app.tools;

/**
 * @author dengbp
 * @ClassName ZodiacUtil
 * @Description 星座、生肖计算
 * @date 2020-12-24 17:56
 */
public class ZodiacUtil {


    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };


    /**
     * Description todo
     * @param month	月份
     * @param day	天
     * @return java.lang.String
     * @Author dengbp
     * @Date 17:58 2020-12-24
     **/
    public static String getStar(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    /**
     * 通过生日计算属相
     *
     * @param year 年
     * @return
     */
    public static String getZodiac(int year) {
        if (year < 1900) {
            return "未知";
        }
        int start = 1900;
        String[] years = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪" };
        return years[(year - start) % years.length];
    }


}

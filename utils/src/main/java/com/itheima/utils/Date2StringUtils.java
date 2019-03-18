package com.itheima.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 时间类型转换工具类

public class Date2StringUtils {
    /**
     * 日期转字符串
     * @param date
     * @param str
     * @return
     */
    public static String date2String(Date date,String str){
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        String s = sdf.format(date);
        return s;
    }

    /**
     * 字符串转日期
     * @param date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date string2Date(Date date,String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        Date parse = sdf.parse(str);
        return parse;
    }
}

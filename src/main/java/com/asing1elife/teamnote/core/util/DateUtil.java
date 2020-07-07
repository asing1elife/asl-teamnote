package com.asing1elife.teamnote.core.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author asing1elife
 */
public class DateUtil {

    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "YYYY-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY = "YYYY-MM-dd";
    public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";

    /**
     * 日期转字符串，YYYY-MM-DD 格式
     */
    public static String dateToString(Date date) {
        return dateToString(date, YEAR_MONTH_DAY);
    }

    /**
     * 字符串转时间
     */
    public static Date stringToDate(String dateStr) {
        return stringToDate(dateStr, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }

    /**
     * 字符串转时间
     */
    public static Date stringToDate(String dateStr, String pattern) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern(pattern)).toDate();
    }

    /**
     * 日期转指定格式字符串
     */
    public static String dateToString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获取当前时间
     */
    public static Date getSysDate() {
        return DateTime.now().toDate();
    }

    /**
     * 获取当前时间戳
     */
    public static Timestamp getSysDateTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取当前时间
     */
    public static String getSysDateString() {
        return DateTime.now().toString(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }

    /**
     * 获取当前日期
     */
    public static int getCurrentDay() {
        DateTime now = DateTime.now();

        return now.getDayOfMonth();
    }

    /**
     * 获取当前月份
     */
    public static int getCurrentMonth() {
        DateTime now = DateTime.now();

        return now.getMonthOfYear();
    }

    public static int getCurrentYear() {
        DateTime now = DateTime.now();

        return now.getYear();
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getSysDateString());
        System.out.println(DateUtil.getCurrentMonth());
        System.out.println(DateUtil.getCurrentYear());
        System.out.println(DateUtil.getCurrentDay());
    }

}

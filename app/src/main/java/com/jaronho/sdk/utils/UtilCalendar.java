package com.jaronho.sdk.utils;

import java.util.Calendar;

/**
 * Author:  jaron.ho
 * Date:    2017-05-22
 * Brief:   日历
 */

public class UtilCalendar {
    private static Calendar mCalendar = Calendar.getInstance();

    /**
     * 功  能: 设置指定时间
     * 参  数: timeStamp - 时间戳(毫秒)
     * 返回值: 无
     */
    public static void setTime(long timeStamp) {
        mCalendar.clear();
        mCalendar.setTimeInMillis(timeStamp);
    }

    /**
     * 功  能: 回到当前时间
     * 参  数: 无
     * 返回值: 无
     */
    public static void setNow() {
        setTime(System.currentTimeMillis());
    }

    /**
     * 功  能: 获取年
     * 参  数: 无
     * 返回值: int
     */
    public static int getYear() {
        return mCalendar.get(Calendar.YEAR);
    }

    /**
     * 功  能: 获取月
     * 参  数: 无
     * 返回值: int,一月:1,二月:2,三月:3,四月:4,五月:5,六月:6,七月:7,八月:8,九月:9,十月:10,十一月:11,十二月:12
     */
    public static int getMonth() {
        return mCalendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功  能: 获取日
     * 参  数: 无
     * 返回值: int
     */
    public static int getDay() {
        return mCalendar.get(Calendar.DATE);
    }

    /**
     * 功  能: 获取小时
     * 参  数: 无
     * 返回值: int
     */
    public static int getHour() {
        return mCalendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功  能: 获取分钟
     * 参  数: 无
     * 返回值: int
     */
    public static int getMinute() {
        return mCalendar.get(Calendar.MINUTE);
    }

    /**
     * 功  能: 获取秒
     * 参  数: 无
     * 返回值: int
     */
    public static int getSecond() {
        return mCalendar.get(Calendar.SECOND);
    }

    /**
     * 功  能: 获取微秒
     * 参  数: 无
     * 返回值: int
     */
    public static int getMillisecond() {
        return mCalendar.get(Calendar.MILLISECOND);
    }

    /**
     * 功  能: 获取当前在本年第几周
     * 参  数: 无
     * 返回值: int
     */
    public static int getWeekOfYear() {
        return mCalendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 功  能: 获取当前在本月第几周
     * 参  数: 无
     * 返回值: int
     */
    public static int getWeekOfMonth() {
        return mCalendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 功  能: 获取当前在本年第几天
     * 参  数: 无
     * 返回值: int
     */
    public static int getDayOfYear() {
        return mCalendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 功  能: 获取当前在本周第几天
     * 参  数: 无
     * 返回值: int,周天:0,周一:1,周二:2,周三:3,周四:4,周五:5,周六:6
     */
    public static int getDayOfWeek() {
        return mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
    }
}

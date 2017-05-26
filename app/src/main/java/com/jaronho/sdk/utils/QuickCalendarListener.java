package com.jaronho.sdk.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author:  jaron.ho
 * Date:    2017-05-22
 * Brief:   日历监听器
 */

public abstract class QuickCalendarListener {
    private QuickCalendar mQuickCalendar = null;
    private Timer mTimer = null;        // 定时器
    private int mLastMinute = 0;        // 跨分钟计算
    private int mLastHour = 0;          // 跨小时计算
    private int mLastDay = 0;           // 跨天计算
    private int mLastWeekDay = 0;       // 跨周计算
    private int mLastMonth = 0;         // 跨月计算
    private int mLastYear = 0;          // 跨年计算

    /**
     * 功  能: 构造函数
     * 参  数: timeStamp - 时间戳(毫秒)
     * 返回值: 无
     */
    public QuickCalendarListener(long timeStamp) {
        mQuickCalendar = new QuickCalendar(timeStamp);
    }

    /**
     * 功  能: 构造函数
     * 参  数: 无
     * 返回值: 无
     */
    public QuickCalendarListener() {
        mQuickCalendar = new QuickCalendar();
    }

    @Override
    protected void finalize() throws Throwable {
        stop();
        super.finalize();
    }

    /**
     * 功  能: 开始监听(每秒监听)
     * 参  数: 无
     * 返回值: 无
     */
    public void start() {
        stop();
        mLastMinute = mQuickCalendar.getMinute();
        mLastHour = mQuickCalendar.getHour();
        mLastDay = mQuickCalendar.getDay();
        mLastWeekDay = mQuickCalendar.getDayOfWeek();
        mLastMonth = mQuickCalendar.getMonth();
        mLastYear = mQuickCalendar.getYear();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mQuickCalendar.setNow();
                int currentMinute = mQuickCalendar.getMinute();
                int currentHour = mQuickCalendar.getHour();
                int currentDay = mQuickCalendar.getDay();
                int currentWeekDay = mQuickCalendar.getDayOfWeek();
                int currentMonth = mQuickCalendar.getMonth();
                int currentYear = mQuickCalendar.getYear();
                if (currentMinute != mLastMinute) {
                    mLastMinute = currentMinute;    // 跨分钟
                    onNewMinute(mQuickCalendar);
                }
                if (currentHour != mLastHour) {    // 跨小时
                    mLastHour = currentHour;
                    onNewHour(mQuickCalendar);
                }
                if (currentDay != mLastDay) {      // 跨天
                    mLastDay = currentDay;
                    onNewDay(mQuickCalendar);
                }
                if (currentWeekDay != mLastWeekDay) {
                    mLastWeekDay = currentWeekDay;
                    if (1 == currentWeekDay) {     // 跨周
                        onNewWeek(mQuickCalendar);
                    }
                }
                if (currentMonth != mLastMonth) {  // 跨月
                    mLastMonth = currentMonth;
                    onNewMonth(mQuickCalendar);
                }
                if (currentYear != mLastYear) {    // 跨年
                    mLastYear = currentYear;
                    onNewYear(mQuickCalendar);
                }
                onInterval(mQuickCalendar);
            }
        }, 0, 1000);
    }

    /**
     * 功  能: 停止监听
     * 参  数: 无
     * 返回值: 无
     */
    public void stop() {
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 功  能: 触发新的一分钟
     * 参  数: quickCalendar - 日历
     * 返回值: 无
     */
    public abstract void onNewMinute(QuickCalendar quickCalendar);

    /**
     * 功  能: 触发新的一小时
     * 参  数: quickCalendar - 日历
     * 返回值: 无
     */
    public abstract void onNewHour(QuickCalendar quickCalendar);

    /**
     * 功  能: 触发新的一天
     * 参  数: quickCalendar - 日历
     * 返回值: 无
     */
    public abstract void onNewDay(QuickCalendar quickCalendar);

    /**
     * 功  能: 触发新的一周
     * 参  数: quickCalendar - 日历
     * 返回值: 无
     */
    public abstract void onNewWeek(QuickCalendar quickCalendar);

    /**
     * 功  能: 触发新的一月
     * 参  数: quickCalendar - 日历
     * 返回值: 无
     */
    public abstract void onNewMonth(QuickCalendar quickCalendar);

    /**
     * 功  能: 触发新的一年
     * 参  数: quickCalendar - 日历
     * 返回值: 无
     */
    public abstract void onNewYear(QuickCalendar quickCalendar);

    /**
     * 功  能: 触发时间间隔
     * 参  数: quickCalendar - 日历
     * 返回值: 无
     */
    public abstract void onInterval(QuickCalendar quickCalendar);
}

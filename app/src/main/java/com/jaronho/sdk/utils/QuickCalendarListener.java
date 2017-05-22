package com.jaronho.sdk.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author:  jaron.ho
 * Date:    2017-05-22
 * Brief:   日历监听器
 */

public abstract class QuickCalendarListener {
    private QuickCalendar mQuickCalendar = new QuickCalendar();
    private Timer mTimer = null;        // 定时器
    private int mLastHour = 0;          // 跨小时计算
    private int mLastDay = 0;           // 跨天计算
    private int mLastWeekDay = 0;       // 跨周计算
    private int mLastMonth = 0;         // 跨月计算
    private int mLastYear = 0;          // 跨年计算

    @Override
    protected void finalize() throws Throwable {
        stop();
        super.finalize();
    }

    /**
     * 功  能: 开始监听
     * 参  数: interval - 时间间隔(毫秒)
     * 返回值: 无
     */
    public void start(int interval) {
        stop();
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
                int currentHour = mQuickCalendar.getHour();
                int currentDay = mQuickCalendar.getDay();
                int currentWeekDay = mQuickCalendar.getDayOfWeek();
                int currentMonth = mQuickCalendar.getMonth();
                int currentYear = mQuickCalendar.getYear();
                if (currentHour != mLastHour) {    // 跨小时
                    mLastHour = currentHour;
                    onNewHour();
                }
                if (currentDay != mLastDay) {      // 跨天
                    mLastDay = currentDay;
                    onNewDay();
                }
                if (currentWeekDay != mLastWeekDay) {
                    mLastWeekDay = currentWeekDay;
                    if (1 == currentWeekDay) {     // 跨周
                        onNewWeek();
                    }
                }
                if (currentMonth != mLastMonth) {  // 跨月
                    mLastMonth = currentMonth;
                    onNewMonth();
                }
                if (currentYear != mLastYear) {    // 跨年
                    mLastYear = currentYear;
                    onNewYear();
                }
                onInterval();
            }
        }, 0, interval);
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
     * 功  能: 触发新的一小时
     * 参  数: 无
     * 返回值: 无
     */
    public abstract void onNewHour();

    /**
     * 功  能: 触发新的一天
     * 参  数: 无
     * 返回值: 无
     */
    public abstract void onNewDay();

    /**
     * 功  能: 触发新的一周
     * 参  数: 无
     * 返回值: 无
     */
    public abstract void onNewWeek();

    /**
     * 功  能: 触发新的一月
     * 参  数: 无
     * 返回值: 无
     */
    public abstract void onNewMonth();

    /**
     * 功  能: 触发新的一年
     * 参  数: 无
     * 返回值: 无
     */
    public abstract void onNewYear();

    /**
     * 功  能: 触发时间间隔
     * 参  数: 无
     * 返回值: 无
     */
    public abstract void onInterval();
}

package com.jaronho.sdk.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

/**
 * Author:  Administrator
 * Date:    2017/5/8
 * Brief:   ActivityTracker
 */

public class ActivityTracker implements ActivityLifecycleCallbacks {
    private static ActivityTracker mInstance = null;
    private static int mActivityCount = 0;
    private static boolean mIsForground = false;
    private static Activity mTopActivity = null;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++mActivityCount;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mIsForground = true;
        mTopActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        --mActivityCount;
        if (0 == mActivityCount) {
            mIsForground = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    /**
     * 功  能: 初始化
     * 参  数: app - 应用对象
     * 返回值: 无
     */
    public static void init(Application app) {
        if (null == mInstance) {
            synchronized (ActivityTracker.class) {
                mInstance = new ActivityTracker();
                app.registerActivityLifecycleCallbacks(mInstance);
            }
        }
    }

    /**
     * 功  能: 获取活动个数
     * 参  数: 无
     * 返回值: int
     */
    public static int getActivityCount() {
        return mActivityCount;
    }

    /**
     * 功  能: 是否在前台
     * 参  数: 无
     * 返回值: boolean
     */
    public static boolean isForground() {
        return mIsForground;
    }

    /**
     * 功  能: 获取顶部活动
     * 参  数: 无
     * 返回值: Activity
     */
    public static Activity getTopActivity() {
        return mTopActivity;
    }
}

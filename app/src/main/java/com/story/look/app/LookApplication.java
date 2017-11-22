package com.story.look.app;

import android.app.Application;

/**
 * 类名称：LookApplication
 * 类描述：自定义Application，初始化全局数据
 * 创建人：story
 * 创建时间：2017/11/22 17:02
 */

public class LookApplication extends Application {

    private ActivityStack activityStack = null;// Activity管理类

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Activity管理类
        activityStack = ActivityStack.getInstance();
    }

    public ActivityStack getActivityStack() {
        return activityStack;
    }
}

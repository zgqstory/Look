package com.story.look.app;

import android.app.Activity;

import java.util.Stack;

/**
 * 类名称：ActivityStack
 * 类描述：Activity管理类
 * 创建人：story
 * 创建时间：2017/11/22 17:04
 */

public class ActivityStack {

    private static ActivityStack instance = new ActivityStack();
    private static Stack<Activity> activityStack;

    private ActivityStack() {}

    public static ActivityStack getInstance() {
        if (instance == null) {
            activityStack = new Stack<>();
            instance = new ActivityStack();
        }
        return instance;
    }

    /**
     * 将当前 Activity 推入栈中
     */
    public void pushActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 移出 Activity
     */
    public void popActivity(Activity activity) {
        if(activity != null) {
            if(activityStack.contains(activity)) {
                activityStack.remove(activity);
            }
        }
    }

    /**
     * 结束并移出Activity
     */
    public void finishActivity(Activity activity) {
        if(activity != null) {
            activity.finish();
            if(activityStack.contains(activity)) {
                activityStack.remove(activity);
            }
            activity = null;
        }
    }

    /**
     * 获取当前Activity(最上层)
     * @return activity
     */
    public Activity getCurrentActivity() {
        Activity activity = null;
        if (!activityStack.empty()) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

    /**
     * 弹出除cls外的所有activity
     */
    public void popAllActivityExceptOne(Class<? extends Activity> cls) {
        while (true) {
            Activity activity = getCurrentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 结束除cls之外的所有activity
     */
    public void finishAllActivityExceptOne(Class<? extends Activity> cls) {
        while (!activityStack.empty()) {
            Activity activity = getCurrentActivity();
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        while (!activityStack.empty()) {
            Activity activity = getCurrentActivity();
            finishActivity(activity);
        }
    }

}

package com.story.look.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.StringRes;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * 类名称：BasePresenter
 * 类描述：基本Presenter定义
 * 创建人：story
 * 创建时间：2017/11/22 16:18
 */

public class BasePresenter<T> {

    private static final InvocationHandler NULL_VIEW = new MvpViewInvocationHandler();
    protected WeakReference<T> mViewReference;
    private WeakReference<Context> mContextRef;
    private static Context sAppContext;//防止View销毁后无法获取String
    private Class mMvpViewClass = null;//用于生成代理
    private T mNullViewProxy;//生成的代理View

    static {
        //初始化sAppContext
        try {
            // 先通过 ActivityThread 来获取 Application Context
            Application application = (Application) Class.forName("android.app.ActivityThread").getMethod
                    ("currentApplication").invoke(null, (Object[]) null);
            if (application != null) {
                sAppContext = application;
            }
            if (sAppContext == null) {
                // 第二种方式初始化
                application = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    sAppContext = application;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BasePresenter() {
        //初始化数据源接口
    }

    /**
     * 绑定View
     * @param view view
     */
    public void attachView(Context context, T view) {
        mContextRef = new WeakReference<>(context);
        mViewReference = new WeakReference<>(view);
        if (sAppContext == null && context != null) {
            sAppContext = context.getApplicationContext();
        }
    }

    /**
     * 释放持有的View，某些需要释放资源的方法可以继承此方法但是需要super
     */
    public void detachView() {
        if ( mContextRef != null ) {
            mContextRef.clear();
            mContextRef = null;
        }
        if (mViewReference != null) {
            mViewReference.clear();
            mViewReference = null;
        }
    }

    /**
     * UI展示相关的操作需要判断一下 Activity 是否已经 finish.
     * @return alive
     */
    protected boolean isActivityAlive() {
        return !isActivityFinishing() && mViewReference.get() != null;
    }

    /**
     * activity 是否是finishing状态
     * @return finish
     */
    private boolean isActivityFinishing() {
        if (mContextRef == null) {
            return true;
        }
        Context context = mContextRef.get();
        if (context instanceof Activity) {
            Activity hostActivity = (Activity) context;
            return hostActivity.isFinishing();
        }
        return true;
    }

    /**
     * 返回 Context. 如果 Activity被销毁, 那么返回应用的Context.
     *
     * 注意:
     *     通过过Context进行UI方面的操作时应该调用 {@link #isActivityAlive()}
     * 判断Activity是否还已经被销毁, 在Activity未销毁的状态下才能操作. 否则会引发crash.
     * 而获取资源等操作则可以使用应用的Context.
     *
     * @return context
     */
    private Context getContext() {
        Context context = mContextRef != null ? mContextRef.get() : null;
        if (context == null || isActivityFinishing()) {
            context = sAppContext;
        }
        return context;
    }

    /**
     * 用于在Presenter获取String资源
     * @param rid id
     * @return string
     */
    protected String getString(@StringRes int rid) {
        return getContext().getString(rid);
    }

    /**
     * 获取持有的View（注意：view可能为空，需要使用前做判断）
     * @return view
     */
    public T getView() {
        T view = mViewReference != null ? mViewReference.get() : null;
        if (view == null) {
            //create null mvp view
            if (mNullViewProxy == null) {
                mNullViewProxy = createView(getMvpViewClass());
            }
            view = mNullViewProxy;
        }
        return view;
    }

    /**
     * 创建 mvp view
     */
    private static <T> T createView(Class<T> viewClz) {
        return (T) Proxy.newProxyInstance(viewClz.getClassLoader(), new Class[] { viewClz }, NULL_VIEW);
    }

    /**
     * 创建 mvp class
     */
    private Class<T> getMvpViewClass() {
        if (mMvpViewClass == null) {
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            mMvpViewClass = (Class<T>) params[0];
        }
        return mMvpViewClass;
    }

    /**
     * 动态代理 InvocationHandler
     */
    private static class MvpViewInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }

}

package com.story.look.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 类名称：BaseFragment
 * 类描述：基本Fragment定义
 * 创建人：story
 * 创建时间：2017/11/23 11:00
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    private Activity mActivity = null;
    private View rootView;
    protected static String TAG;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mActivity = getActivity();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getLayout(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onCreateFragment(savedInstanceState);
    }

    @Override
    public void showLoading(String message, int time) {
        if (mActivity != null) {
            if (mActivity instanceof BaseActivity) {
                ((BaseActivity)mActivity).showLoading(message, time);
            }
        }
    }

    @Override
    public void hideLoading() {
        if (mActivity != null) {
            if (mActivity instanceof BaseActivity) {
                ((BaseActivity)mActivity).hideLoading();
            }
        }
    }

    @Override
    public void alertMessage(String message) {
        if (mActivity != null) {
            if (mActivity instanceof BaseActivity) {
                ((BaseActivity)mActivity).alertMessage(message);
            }
        }
    }

    @Override
    public void toastMessage(String message) {
        if (mActivity != null) {
            if (mActivity instanceof BaseActivity) {
                ((BaseActivity)mActivity).toastMessage(message);
            }
        }
    }

    protected View getRootView() {
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(rootView);
    }

    /**
     * 获取布局
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     * @return view
     */
    public abstract View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 存放创建时的初始化
     * @param savedInstanceState savedInstanceState
     */
    public abstract void onCreateFragment(@Nullable Bundle savedInstanceState);

}

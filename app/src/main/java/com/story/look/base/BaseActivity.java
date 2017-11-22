package com.story.look.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.story.look.R;
import com.story.look.app.LookApplication;
import com.story.view.alert.alert_like_ios.AlertView;
import com.story.view.alert.alert_like_ios.Style;
import com.story.view.progress.ProgressDialog;

import butterknife.ButterKnife;

/**
 * 类名称：BaseActivity
 * 类描述：基本Activity定义
 * 创建人：story
 * 创建时间：2017/11/22 16:26
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements BaseView {

    private ProgressDialog progressDialog;// 网络加载等待框
    protected LookApplication lookApp;
    protected T mPresenter;
    protected static String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 设置 Activity 屏幕方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // 隐藏 ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置 TAG
        TAG = this.getClass().getSimpleName();
        lookApp = (LookApplication) getApplication();

        super.onCreate(savedInstanceState);
        setContentView(getLayoutInflater().inflate(getLayoutRes(), null, true));

        mPresenter = createPresenter();
        mPresenter.attachView(this, (V)this);
        progressDialog = new ProgressDialog(this);
        // 绑定依赖注入框架
        ButterKnife.bind(this);
        onCreateActivity(savedInstanceState);
        // 将当前 Activity 推入栈中
        lookApp.getActivityStack().pushActivity(this);
    }

    @Override
    public void showLoading(String message, int time) {
        if (message != null && !message.equals("") && time > 0) {
            progressDialog.show(message, time);
        } else if (time > 0) {
            progressDialog.show(time);
        } else if (message != null && !message.equals("")) {
            progressDialog.show(message);
        } else {
            progressDialog.show(getString(R.string.load_msg_default));
        }
    }

    @Override
    public void hideLoading() {
        progressDialog.cancel();
    }

    @Override
    public void alertMessage(String message) {
        new AlertView(message, null, BaseActivity.this.getString(R.string.sure), null, null, BaseActivity.this, Style.Alert, null).show();
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            // 点击空白位置 隐藏软键盘
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (mInputMethodManager != null) {
                return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        ButterKnife.unbind(this);
        lookApp.getActivityStack().popActivity(this);
    }

    /**
     * 创建Presenter引用
     * @return T
     */
    protected abstract T createPresenter();

    /**
     * 获取布局
     * @return int
     */
    protected abstract int getLayoutRes();

    /**
     * 存放创建时的初始化
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void onCreateActivity(@Nullable Bundle savedInstanceState);
}

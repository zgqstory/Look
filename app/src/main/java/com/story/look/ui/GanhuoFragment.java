package com.story.look.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.story.look.R;
import com.story.look.base.BaseFragment;

/**
 * 类名称：GanhuoFragment
 * 类描述：干货首页
 * 创建人：story
 * 创建时间：2017/11/23 14:42
 */

public class GanhuoFragment extends BaseFragment {
    @Override
    public View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ganhuo, null);
    }

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {

    }
}

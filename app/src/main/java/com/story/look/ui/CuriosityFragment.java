package com.story.look.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.story.look.R;
import com.story.look.base.BaseFragment;

/**
 * 类名称：CuriosityFragment
 * 类描述：好奇心首页
 * 创建人：story
 * 创建时间：2017/11/23 14:43
 */

public class CuriosityFragment extends BaseFragment {
    @Override
    public View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_curiosity, null);
    }

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {

    }
}

package com.story.look.ui;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.story.look.R;
import com.story.look.base.BaseActivity;
import com.story.look.base.BaseFragment;
import com.story.look.contract.MainContract;
import com.story.look.prefenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity<MainContract.ContractView, MainPresenter> implements MainContract.ContractView {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.content_viewPager)
    ViewPager viewPager;

    private List<String> titleList;
    private List<BaseFragment> fragmentList;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        // 初始化数据
        titleList = new ArrayList<>();
        titleList.add(getString(R.string.tab_msg_zhihu));
        titleList.add(getString(R.string.tab_msg_ganhuo));
        titleList.add(getString(R.string.tab_msg_curiosity));
        fragmentList = new ArrayList<>();
        fragmentList.add(new ZhihuFragment());
        fragmentList.add(new GanhuoFragment());
        fragmentList.add(new CuriosityFragment());
        // 初始化TabLayout和ViewPager
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setOffscreenPageLimit(3);//设置至少3个fragment，防止重复创建和销毁，造成内存溢出
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * TabView页面适配器
     */
    private FragmentPagerAdapter tabPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    };

}

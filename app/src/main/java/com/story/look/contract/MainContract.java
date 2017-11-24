package com.story.look.contract;

import com.story.look.base.BasePresenter;
import com.story.look.base.BaseView;

/**
 * 类名称：MainContract
 * 类描述：定义主界面的View和Presenter的操作
 * 创建人：story
 * 创建时间：2017/11/22 20:27
 */

public interface MainContract {

    interface ContractView extends BaseView {
    }

    abstract class ContractPresenter extends BasePresenter<ContractView> {
    }
}

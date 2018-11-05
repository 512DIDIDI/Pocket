package com.dididi.pocket.core.delegates.bottom;

/**
 * @author dididi(yechao)
 * @describe 点击底部导航栏tab监听事件
 * @since 05/11/2018
 */

public interface IClickTabListener {

    /**
     * 点击tab返回顶部
     */
    void onScrollToTop();

    /**
     * 判断当前页面是否处于顶部
     * @return 是否位于顶部
     */
    boolean isTop();

    /**
     * 点击tab刷新页面
     */
    void onRefresh();
}

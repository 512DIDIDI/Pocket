package com.dididi.pocket.ec.main.message.chat.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 18/03/2019
 * @describe 聊天界面点击更多页面ViewPager适配器
 */

class MorePagerAdapter(private var context: Context, private var viewList: List<View>) : PagerAdapter() {

    override fun getCount() = viewList.size

    override fun isViewFromObject(p0: View, p1: Any) = (p0 == p1)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(viewList[position])
        return viewList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewList[position])
    }

}
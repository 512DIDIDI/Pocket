package com.dididi.pocket.core.delegates.bottom

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.dididi.pocket.core.R
import com.dididi.pocket.core.delegates.PocketDelegate
import com.mikepenz.iconics.view.IconicsTextView
import kotlinx.android.synthetic.main.base_delegate.*
import me.yokeyword.fragmentation.ISupportFragment
import java.util.*


/**
 * @author dididi
 * on 24/07/2018 .
 */

abstract class BaseBottomDelegate : PocketDelegate(), View.OnClickListener {
    //抽象带bottomBar的页面

    protected lateinit var bottomBar: LinearLayoutCompat

    private val tabBeans = ArrayList<BottomTabBean>()
    private val itemDelegate = ArrayList<BottomItemDelegate>()

    private val items = LinkedHashMap<BottomTabBean, BottomItemDelegate>()
    /**
     * 当前fragment页面
     */
    private var mCurrentDelegate = 0
    /**
     * 默认打开fragment页面
     */
    private var mIndexDelegate = 0
    /**
     * 设置点击后tab的颜色
     */
    private var mPressColor = R.color.tabPressColor
    /**
     * 设置默认tab颜色
     */
    private val mNormalColor = R.color.tabNormalColor

    /**
     * 设置底部导航tab与item的关联
     *
     * @param factory 生成linkedHashMap
     * @return 返回关联好的linkedHashMap
     */
    abstract fun setItems(factory: ItemFactory): LinkedHashMap<BottomTabBean, BottomItemDelegate>

    /**
     * 设置tab按下颜色
     *
     * @return 返回color
     */
    abstract fun setPressColor(): Int

    /**
     * 设置默认打开item
     *
     * @return 返回数组下标
     */
    abstract fun setIndexDelegate(): Int

    /**
     * 初始化delegate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIndexDelegate = setIndexDelegate()
        if (setPressColor() != 0) {
            mPressColor = setPressColor()
        }
        val factory = ItemFactory.builder()
        val items = setItems(factory)
        this.items.putAll(items)
        //遍历获取map键值对
        for ((key, value) in this.items) {
            tabBeans.add(key)
            itemDelegate.add(value)
        }
    }

    override fun setLayout(): Any {
        return R.layout.base_delegate
    }

    override fun onBindChildView(savedInstanceState: Bundle?, rootView: View?) {
        val size = items.size
        bottomBar = rootView!!.findViewById(R.id.base_delegate_bar)
        for (i in 0 until size) {
            //加载bottom_bar_layout布局并设置其父布局为mBottomBar
            LayoutInflater.from(context)
                    .inflate(R.layout.bottom_bar_layout, bottomBar)
            //获取mBottomBar的子布局(即bottom_bar_layout)
            val item = bottomBar.getChildAt(i) as RelativeLayout
            //设置item点击事件
            item.tag = i
            item.setOnClickListener(this)
            //获取item的icon和title
            val icon = item.getChildAt(0) as IconicsTextView
            val title = item.getChildAt(1) as AppCompatTextView
            //获取存储的bar
            val bean = tabBeans[i]
            icon.text = bean.icon
            title.text = bean.title
            if (i == mIndexDelegate) {
                item.setBackgroundColor(ContextCompat.getColor(context!!, mPressColor))
                icon.setTextColor(ContextCompat.getColor(context!!, R.color.textColorDark))
                title.setTextColor(ContextCompat.getColor(context!!, R.color.textColorDark))
            }
        }
        //获取存储的delegate转化为数组,具体原因查看源码
        val delegateArray = itemDelegate.toTypedArray<ISupportFragment>()
        //加载多个fragment
        supportDelegate
                .loadMultipleRootFragment(R.id.base_delegate_container, mIndexDelegate, *delegateArray)
    }

    /**
     * 重置bar颜色
     */
    private fun resetColor() {
        val count = bottomBar.childCount
        for (i in 0 until count) {
            val item = bottomBar.getChildAt(i) as RelativeLayout
            val icon = item.getChildAt(0) as IconicsTextView
            val title = item.getChildAt(1) as AppCompatTextView
            item.setBackgroundColor(ContextCompat.getColor(context!!, mNormalColor))
            icon.setTextColor(ContextCompat.getColor(context!!, R.color.textColorHint))
            title.setTextColor(ContextCompat.getColor(context!!, R.color.textColorHint))
        }
    }

    override fun onResume() {
        super.onResume()
        val height = resources.getDimensionPixelSize(R.dimen.bottomBarSize)
        //解决软键盘弹起时顶起底部导航栏
        base_delegate_root_view!!.addOnLayoutChangeListener { _, _, _, _, i3, _, _, _, i7 ->
            if (i3 - i7 < -1) {
                val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 0)
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                bottomBar.layoutParams = params
            } else if (i3 - i7 > 1) {
                val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        height)
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                bottomBar.layoutParams = params
            }
        }
    }

    override fun onClick(view: View) {
        //避免在drawerLayout滑出的时候快速点击bottomBar导致bottomBar消失
        if (bottomBar.visibility == View.GONE) {
            bottomBar.visibility = View.VISIBLE
        }
        val tag = view.tag as Int
        val currentDelegate = itemDelegate[mCurrentDelegate]
        //如果已经选中当前页面，则再次点击滚动到顶部。
        if (tag == mCurrentDelegate) {
            //如果已经位于顶部，则刷新页面
            if (currentDelegate.isTop) {
                currentDelegate.onRefresh()
                return
            }
            currentDelegate.onScrollToTop()
            return
        }
        resetColor()
        //更改颜色
        val item = view as RelativeLayout
        val icon = item.getChildAt(0) as IconicsTextView
        val title = item.getChildAt(1) as AppCompatTextView
        item.setBackgroundColor(ContextCompat.getColor(context!!, mPressColor))
        icon.setTextColor(ContextCompat.getColor(context!!, R.color.textColorDark))
        title.setTextColor(ContextCompat.getColor(context!!, R.color.textColorDark))
        //隐藏当前fragment显示点击的fragment
        supportDelegate.showHideFragment(itemDelegate[tag], currentDelegate)
        //重置tag为当前所选中的fragment
        mCurrentDelegate = tag
    }

}

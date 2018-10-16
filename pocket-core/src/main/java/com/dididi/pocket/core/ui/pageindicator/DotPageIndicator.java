package com.dididi.pocket.core.ui.pageindicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author dididi
 * @describe 页面指示器(小圆点)
 * @since 16/10/2018
 */

public class DotPageIndicator extends LinearLayout {

    private Context mContext = null;
    /**
     * 指示器大小
     */
    private int dotSize = 15;
    /**
     * 指示器间距
     */
    private int margin = 4;

    private List<View> indicatorViews = null;

    public DotPageIndicator(Context context) {
        super(context);
        init(context);
    }

    public DotPageIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DotPageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        //设置水平居中
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        dotSize = SizeUtils.dp2px(dotSize);
        margin = SizeUtils.dp2px(margin);
    }

    /**
     * 初始化指示器，默认选中第一页
     *
     * @param count 指示器数量，即页数
     */
    public void initIndicator(int count) {

        if (indicatorViews == null) {
            indicatorViews = new ArrayList<>();
        } else {
            indicatorViews.clear();
            removeAllViews();
        }
        View view;
        LayoutParams params = new LayoutParams(dotSize, dotSize);
        params.setMargins(margin, margin, margin, margin);
        for (int i = 0; i < count; i++) {
            view = new View(mContext);
            view.setBackgroundResource(android.R.drawable.presence_invisible);
            addView(view, params);
            indicatorViews.add(view);
        }
        if (indicatorViews.size() > 0) {
            //设置默认选中第一个
            indicatorViews.get(0).setBackgroundResource(android.R.drawable.presence_online);
        }
    }

    /**
     * 设置选中页
     *
     * @param selected 页下标，从0开始
     */
    public void setSelectedPage(int selected) {
        for (int i = 0; i < indicatorViews.size(); i++) {
            if (i == selected) {
                indicatorViews.get(i).setBackgroundResource(android.R.drawable.presence_online);
            } else {
                indicatorViews.get(i).setBackgroundResource(android.R.drawable.presence_invisible);
            }
        }
    }
}

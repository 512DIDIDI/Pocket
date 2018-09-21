package com.dididi.pocket.ec.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.dididi.pocket.ec.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by dididi
 * on 28/07/2018 .
 */

public class CircleIconItem extends RelativeLayout {
//  首页快速入口组合控件

    private CircleImageView mIcon;
    private AppCompatTextView mText;
    private Integer mIconDrawable;
    private CharSequence mTextTitle;

    public CircleIconItem(Context context) {
        super(context);
        initView(context);
    }

    public CircleIconItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        initView(context);
    }

    public CircleIconItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        initView(context);
    }

    //设置点击监听
    @Override
    public void setOnClickListener(OnClickListener listener) {
        mIcon.setOnClickListener(listener);
        mText.setOnClickListener(listener);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        //获取自定义属性并设置默认值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIconItem);
        mTextTitle = typedArray.getText(R.styleable.CircleIconItem_circle_icon_text);
        mIconDrawable = typedArray
                .getResourceId(R.styleable.CircleIconItem_circle_icon_src, 0);
        typedArray.recycle();
    }

    private void initView(Context context) {
        //获取控件实例
        LayoutInflater.from(context)
                .inflate(R.layout.item_home_entry_icon_text, this, true);
        mIcon = findViewById(R.id.item_home_entry_icon);
        mText = findViewById(R.id.item_home_entry_text);
        //控件实例赋值自定义属性
        mIcon.setImageResource(mIconDrawable);
        mText.setText(mTextTitle);
    }
}

package com.dididi.pocket.core.ui.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dididi.pocket.core.R;
import com.mikepenz.iconics.view.IconicsTextView;

/**
 * @author dididi
 * @describe recyclerView(listView)含icon与文字的item
 * @since 28/09/2018
 */

public class ListButtonItem extends RelativeLayout {

    private IconicsTextView mIcon;
    private AppCompatTextView mContent;
    private CharSequence mIconText;
    private CharSequence mContentText;

    public ListButtonItem(Context context) {
        super(context);
        initView(context);
    }

    public ListButtonItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        initView(context);
    }

    public ListButtonItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        initView(context);
    }

    private void initTypedArray(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ListButtonItem);
        mIconText = typedArray.getText(R.styleable.ListButtonItem_list_button_icon);
        mContentText = typedArray.getText(R.styleable.ListButtonItem_list_button_content);
        typedArray.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_button, this, true);
        mIcon = findViewById(R.id.item_button_icon);
        mContent = findViewById(R.id.item_button_content);
        mIcon.setText(mIconText);
        mContent.setText(mContentText);
    }
}

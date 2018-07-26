package com.dididi.pocket.ec.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.dididi.pocket.ec.R;
import com.mikepenz.iconics.view.IconicsTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by dididi
 * on 25/07/2018 .
 */

public class SearchBarItem extends RelativeLayout{

    private IconicsTextView mLeftIcon;
    private IconicsTextView mSearchIcon;
    private MaterialEditText mEditInput;

    public SearchBarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_search_bar, this);
        mLeftIcon = findViewById(R.id.item_search_left_icon);
        mSearchIcon = findViewById(R.id.item_search_search);
        mEditInput = findViewById(R.id.item_search_edit);
    }
    //设置左侧图标
    public void setLeftIcon(String text) {
        mLeftIcon.setText(text);
    }
    //隐藏左侧图标
    public void setLeftIconGone(){
        mLeftIcon.setVisibility(GONE);
    }

    //获取输入框文字
    public String getEditText() {
        if (mEditInput.getText() != null) {
            return mEditInput.getText().toString();
        }
        return null;
    }
    //左侧按钮点击监听
    public void setLeftIconListener(OnClickListener listener) {
        mLeftIcon.setOnClickListener(listener);
    }
    //右侧按钮点击监听
    public void setSearchIconListener(OnClickListener listener) {
        mSearchIcon.setOnClickListener(listener);
    }

    public int getLeftIconId(){
        return mLeftIcon.getId();
    }

    public int getSearchIconId(){
        return mSearchIcon.getId();
    }
}

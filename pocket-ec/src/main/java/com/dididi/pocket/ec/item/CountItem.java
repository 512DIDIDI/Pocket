package com.dididi.pocket.ec.item;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.dididi.pocket.ec.R;


/**
 * Created by dididi
 * on 29/07/2018 .
 */

public class CountItem extends RelativeLayout {

    private AppCompatTextView mDecrease;
    private TextInputEditText mCount;
    private AppCompatTextView mIncrease;

    public CountItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_shopCart_count_button, this);
        mDecrease = findViewById(R.id.item_shopCart_count_decrease);
        mCount = findViewById(R.id.item_shopCart_count_number);
        mIncrease = findViewById(R.id.item_shopCart_count_increase);
    }

    public String getCount(){
        if (mCount.getText() !=null){
            return mCount.getText().toString();
        }
        return null;
    }

    public void setDecreaseListener(OnClickListener listener){
        mDecrease.setOnClickListener(listener);
    }

    public void setIncreaseListener(OnClickListener lisener){
        mIncrease.setOnClickListener(lisener);
    }
}

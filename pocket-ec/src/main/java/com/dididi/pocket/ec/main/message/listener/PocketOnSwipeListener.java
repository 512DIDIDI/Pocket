package com.dididi.pocket.ec.main.message.listener;

/**
 * Created by dididi
 * on 29/07/2018 .
 */

public interface PocketOnSwipeListener {
//  提供给外界与adapter的接口

    void onDelete(int position);
    void onTop(int position);
}

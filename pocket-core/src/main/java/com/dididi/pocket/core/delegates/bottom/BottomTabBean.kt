package com.dididi.pocket.core.delegates.bottom

/**
 * @author dididi
 * on 24/07/2018 .
 * @describe 存储bottomBar的tab
 */

class BottomTabBean(val icon: CharSequence, val title: CharSequence) {

    override fun hashCode(): Int {
        return icon.hashCode() * 2 + title.hashCode() * 3
    }

    override fun equals(other: Any?): Boolean {
        if (other is BottomTabBean){
            return icon == other.icon && title == other.title
        }
        return false
    }
}

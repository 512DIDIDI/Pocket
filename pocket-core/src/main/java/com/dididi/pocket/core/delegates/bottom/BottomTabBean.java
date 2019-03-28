package com.dididi.pocket.core.delegates.bottom;

import androidx.annotation.Nullable;

/**
 * Created by dididi
 * on 24/07/2018 .
 */

public final class BottomTabBean {
    //存储bottomBar的tab
    /**
     * CharSequence是String,StringBuilder,StringBuffer的接口
     */
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof BottomTabBean) {
            BottomTabBean item = (BottomTabBean) obj;
            return this.ICON.equals(item.getIcon()) && this.TITLE.equals(item.getTitle());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.ICON.hashCode() + (this.TITLE.hashCode()) * 3;
    }
}

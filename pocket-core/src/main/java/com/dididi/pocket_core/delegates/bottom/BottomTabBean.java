package com.dididi.pocket_core.delegates.bottom;

/**
 * Created by dididi
 * on 24/07/2018 .
 */

public final class BottomTabBean {
    //存储bottomBar的tab
    //CharSequence是String,StringBuilder,StringBuffer的接口
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon,CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}

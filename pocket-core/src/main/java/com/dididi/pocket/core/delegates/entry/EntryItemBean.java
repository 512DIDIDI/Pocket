package com.dididi.pocket.core.delegates.entry;

/**
 * @author dididi
 * @describe 存储入口的icon和title
 * @since 16/10/2018
 */

public class EntryItemBean {

    private CharSequence mIcon;
    private CharSequence mTitle;

    public EntryItemBean(CharSequence mIcon, CharSequence mTitle) {
        this.mIcon = mIcon;
        this.mTitle = mTitle;
    }

    public CharSequence getmIcon() {
        return mIcon;
    }

    public CharSequence getmTitle() {
        return mTitle;
    }

    /**todo:这里bug没写好 */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

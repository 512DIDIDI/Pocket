package com.dididi.pocket.core.delegates.entry;

/**
 * @author dididi
 * @describe 存储入口的icon和title
 * @since 16/10/2018
 */

public class EntryItemBean {

    private CharSequence icon;
    private CharSequence title;

    public EntryItemBean(CharSequence icon, CharSequence title) {
        this.icon = icon;
        this.title = title;
    }

    public CharSequence getIcon() {
        return icon;
    }

    public CharSequence getTitle() {
        return title;
    }

    @Override
    public int hashCode() {
        return this.icon.hashCode() + (this.title.hashCode()) * 3;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EntryItemBean) {
            EntryItemBean item = (EntryItemBean) obj;
            return this.icon.equals(item.getIcon()) && this.title.equals(item.getTitle());
        }
        return false;
    }
}

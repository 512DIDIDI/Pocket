package com.dididi.pocket.ec.icon;

import android.content.Context;
import android.graphics.Typeface;


import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.ITypeface;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by dididi
 * on 17/07/2018 .
 */

public class FontEcModule implements ITypeface {
    //自定义FontModule,扩展图标库
    /** ttf文件名 */
    private static final String TTF_FILE = "fonts/iconfont.ttf";
    private static Typeface typeface = null;
    private static HashMap<String, Character> mChars;

    @Override
    public IIcon getIcon(String key) {
        return EcIcons.valueOf(key);
    }

    @Override
    public HashMap<String, Character> getCharacters() {
        if (mChars == null) {
            HashMap<String, Character> aChars = new HashMap<>();
            for (EcIcons ecIcons : EcIcons.values()) {
                aChars.put(ecIcons.name(), ecIcons.character);
            }
            mChars = aChars;
        }
        return mChars;
    }

    @Override
    //前缀
    public String getMappingPrefix() {
        return "Ali";
    }

    @Override
    //字体库名称
    public String getFontName() {
        return "Icon";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public int getIconCount() {
        return mChars.size();
    }

    @Override
    public Collection<String> getIcons() {
        Collection<String> icons = new LinkedList<>();
        for (EcIcons value : EcIcons.values()) {
            icons.add(value.name());
        }
        return icons;
    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getDescription() {
        return "ali icon";
    }

    @Override
    public String getLicense() {
        return null;
    }

    @Override
    public String getLicenseUrl() {
        return null;
    }

    @Override
    public Typeface getTypeface(Context ctx) {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(ctx.getAssets(),
                        "fonts/" + TTF_FILE);
            } catch (Exception e) {
                return null;
            }
        }
        return typeface;
    }
}

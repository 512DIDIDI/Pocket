package com.dididi.pocket.ec.icon;


import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.ITypeface;

/**
 * Created by dididi
 * on 17/07/2018 .
 */

public enum EcIcons implements IIcon {
    //自定义图标  实现iconics的IIcon接口
    //unicode编码
    icon_scan('\ue614'),
    icon_ali_pay('\ue806');

    char character;

    EcIcons(char character) {
        this.character = character;
    }


    @Override
    public String getFormattedName() {
        return "{" + name() + "}";
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public char getCharacter() {
        return character;
    }

    private static ITypeface iTypeface;

    @Override
    public ITypeface getTypeface() {
        if (iTypeface == null) {
            iTypeface = new FontEcModule();
        }
        return iTypeface;
    }
}


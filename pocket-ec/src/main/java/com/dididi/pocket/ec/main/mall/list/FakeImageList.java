package com.dididi.pocket.ec.main.mall.list;

import com.dididi.pocket.ec.R;

import java.util.ArrayList;

/**
 * Created by dididi
 * on 26/07/2018 .
 */

public final class FakeImageList {
    private final ArrayList<Integer> IMAGES = new ArrayList<>();

    private void initImages() {
        IMAGES.add(R.mipmap.banner_01);
        IMAGES.add(R.mipmap.banner_02);
        IMAGES.add(R.mipmap.banner_03);
        IMAGES.add(R.mipmap.banner_04);
        IMAGES.add(R.mipmap.banner_05);
    }

    public ArrayList<Integer> getImages() {
        initImages();
        return IMAGES;
    }
}

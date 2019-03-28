package com.dididi.pocket.core.ui.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;


/**
 * @author dididi
 * on 26/07/2018 .
 */

public class GlideImageLoader extends ImageLoader {
    /** 使用glide加载banner图片 */
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .into(imageView);
    }
}

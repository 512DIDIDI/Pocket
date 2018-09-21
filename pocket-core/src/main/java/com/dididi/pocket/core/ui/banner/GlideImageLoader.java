package com.dididi.pocket.core.ui.banner;

import android.content.Context;
import android.widget.ImageView;

import com.dididi.pocket.core.ui.GlideApp;
import com.youth.banner.loader.ImageLoader;


/**
 * Created by dididi
 * on 26/07/2018 .
 */

public class GlideImageLoader extends ImageLoader {
    /**使用glide加载banner图片*/
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideApp.with(context)
                .load(path)
                .into(imageView);
    }
}

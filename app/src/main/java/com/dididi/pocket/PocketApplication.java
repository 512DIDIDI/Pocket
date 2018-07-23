package com.dididi.pocket;

import android.app.Application;

import com.dididi.pocket.ec.icon.FontEcModule;
import com.dididi.pocket_core.app.Pocket;
import com.dididi.pocket_core.net.interceptor.DebugInterceptor;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;


/**
 * Created by dididi
 * on 17/07/2018 .
 */

public class PocketApplication extends Application {
    //自定义application
    @Override
    public void onCreate() {
        super.onCreate();
        //获取context传入配置文件
        Pocket.init(this)
                .withIcon(new FontAwesome())
                .withIcon(new GoogleMaterial())
                //引用自定义FontModule
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1/")
                .configure();
    }
}

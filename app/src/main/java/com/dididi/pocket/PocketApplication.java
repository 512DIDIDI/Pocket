package com.dididi.pocket;

import android.app.Application;

import com.dididi.pocket.ec.icon.FontEcModule;
import com.dididi.pocket.core.app.Pocket;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;

import org.litepal.LitePal;


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
                //引用自定义FontModule
                //.withInterceptor(new DebugInterceptor("index", R.raw.test))
                .withIcon(new FontEcModule())
                //.withApiHost("http://192.168.1.105:3000/")
                .configure();
        //初始化litePal数据库
        LitePal.initialize(this);
    }
}

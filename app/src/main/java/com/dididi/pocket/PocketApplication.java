package com.dididi.pocket;

import android.app.Application;

import com.dididi.pocket.core.fakedata.FakeUser;
import com.dididi.pocket.ec.icon.FontEcModule;
import com.dididi.pocket.core.app.Pocket;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;

import org.litepal.LitePal;


/**
 * @author dididi
 * @since 17/07/2018 .
 */

public class PocketApplication extends Application {

    /**
     * 自定义application
     */
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
        //假用户数据初始化
        FakeUser.init();
    }
}

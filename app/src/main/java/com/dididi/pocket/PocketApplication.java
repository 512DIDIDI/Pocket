package com.dididi.pocket;

import android.support.multidex.MultiDexApplication;

import com.dididi.pocket.core.im.BaseIMConfigs;
import com.dididi.pocket.core.app.Pocket;
import com.dididi.pocket.core.fakedata.FakeUser;
import com.dididi.pocket.core.util.Constants;
import com.dididi.pocket.ec.icon.FontEcModule;
import com.dididi.pocket.ec.main.message.chat.model.C2CChatManager;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.tencent.imsdk.session.SessionWrapper;


/**
 * @author dididi
 * @since 17/07/2018 .
 */

public class PocketApplication extends MultiDexApplication {

    private static final String TAG = "PocketApplication";

    /**
     * 自定义application
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (SessionWrapper.isMainProcess(this)) {
            //获取context传入配置文件
            Pocket.init(this)
                    .withIcon(new FontAwesome())
                    //.withInterceptor(new DebugInterceptor("index", R.raw.test))
                    //引用自定义FontModule
                    .withIcon(new FontEcModule())
                    .withApiHost(Constants.BASE_URL)
                    //配置腾讯云通信
                    .withTimConfig(this, Constants.SDK_APP_ID, BaseIMConfigs.Companion.getDefaultConfgis())
                    .configure();
            C2CChatManager.Companion.getInstance().init();
            FakeUser.init();
        }
    }
}

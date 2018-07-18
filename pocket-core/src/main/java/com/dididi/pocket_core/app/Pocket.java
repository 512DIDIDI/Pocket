package com.dididi.pocket_core.app;

import android.content.Context;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by dididi
 * on 17/07/2018 .
 */

public final class Pocket {

    //传入ApplicationContext,并将其存储到Configurator的HashMap中
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),
                context.getApplicationContext());
        return Configurator.getInstance();
    }

    private static HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getPocketConfigs();
    }
    //获取全局context
    public static Context getApplicationContext() {
        return (Context) getConfigurations()
                .get(ConfigType.APPLICATION_CONTEXT.name());
    }
}

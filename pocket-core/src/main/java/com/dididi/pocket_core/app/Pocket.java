package com.dididi.pocket_core.app;

import android.content.Context;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by dididi
 * on 17/07/2018 .
 */

public final class Pocket {

    public static Configurator init(Context context) {
        //传入全局context,提供全局访问context
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),
                context.getApplicationContext());
        return Configurator.getInstance();
    }

    private static HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getPocketConfigs();
    }
}

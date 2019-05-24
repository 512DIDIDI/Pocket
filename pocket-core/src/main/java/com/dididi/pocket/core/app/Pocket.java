package com.dididi.pocket.core.app;

import android.content.Context;

import com.dididi.pocket.core.im.BaseIMConfigs;

import java.util.HashMap;

/**
 * Created by dididi
 * on 17/07/2018 .
 */

public final class Pocket {

    /** 传入ApplicationContext到配置文件中 */
    public static Configurator init(Context context) {
        getConfigurator().setConfiguration(ConfigType.APPLICATION_CONTEXT,
                context.getApplicationContext());
        return getConfigurator();
    }
    /** 获取全部配置文件 */
    public static HashMap<Enum<ConfigType>, Object> getConfigurations() {
        return getConfigurator().getPocketConfigs();
    }

    /** 获取配置文件实例 */
    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    /** 获取全局context */
    public static Context getApplicationContext() {
        return (Context) getConfigurations()
                .get(ConfigType.APPLICATION_CONTEXT);
    }

    /** 获取IM配置文件 */
    public static BaseIMConfigs getBaseIMConfigs(){
        return (BaseIMConfigs) getConfigurations().get(ConfigType.TIM_BASE_CONFIG);
    }

}

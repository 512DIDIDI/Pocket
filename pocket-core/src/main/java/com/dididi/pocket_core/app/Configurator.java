package com.dididi.pocket_core.app;

import android.content.Context;

import com.dididi.pocket_core.Util.LogUtil;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.typeface.ITypeface;
import com.mikepenz.iconics.utils.GenericsUtil;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by dididi
 * on 17/07/2018 .
 */

public class Configurator {
    private static final String TAG = "Configurator";
    //使用HashMap不用WeakHashMap,不会被自动回收,配置文件需要伴随整个app的生命周期(使用key限制传入值)
    private static final HashMap<Enum<ConfigType>, Object> POCKET_CONFIGS = new HashMap<>();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final HashMap<String, ITypeface> ICONS = new HashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    //私有构造函数
    private Configurator() {
        //初始化配置为false
        POCKET_CONFIGS.put(ConfigType.CONFIG_READY, false);
    }

    //静态内部类单例模式
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Enum<ConfigType>, Object> getPocketConfigs() {
        return POCKET_CONFIGS;
    }

    //配置方法
    public final void configure() {
        LogUtil.d(TAG, "configure");
        initIcons();
        //配置已完成
        POCKET_CONFIGS.put(ConfigType.CONFIG_READY, true);
        LogUtil.d(TAG, "configure succeed");
    }

    //传入域名
    public final Configurator withApiHost(String host) {
        POCKET_CONFIGS.put(ConfigType.API_HOST, host);
        return this;
    }

    //传入自定义Icon
    public final Configurator withIcon(ITypeface iTypeface) {
        validateFont(iTypeface);
        ICONS.put(iTypeface.getMappingPrefix(), iTypeface);
        return this;
    }
    //传入拦截器
    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        POCKET_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        POCKET_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    //初始化icon
    private void initIcons() {
        String[] fonts = GenericsUtil.getFields(Pocket.getApplicationContext());
        for (String fontsClassPath : fonts) {
            try {
                ITypeface typeface = (ITypeface) Class.forName(fontsClassPath).newInstance();
                validateFont(typeface);
                ICONS.put(typeface.getMappingPrefix(), typeface);
            } catch (Exception e) {
                LogUtil.e("Android-Iconics", "Can't init: " + fontsClassPath);
            }
        }
    }

    @SuppressWarnings("unchecked")
    //获取配置文件
    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) POCKET_CONFIGS.get(key);
    }

    //设置配置文件
    final void setConfiguration(Enum<ConfigType> key, Object object) {
        POCKET_CONFIGS.put(key, object);
    }

    //确认是否配置完成
    private void checkConfiguration() {
        //写类变量和方法变量的时候尽量让它的不可变性达到最大(能用final修饰就用)
        final boolean isReady = (boolean) POCKET_CONFIGS.get(ConfigType.CONFIG_READY);
        if (!isReady) {
            //保证配置完成,如果没有准备好,抛出运行异常
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    //是否是有效字体库
    private static void validateFont(ITypeface font) {
        if (font.getMappingPrefix().length() != 3) {
            throw new IllegalArgumentException(
                    "The mapping prefix of a font must be three characters long.");
        }
    }
}

package com.dididi.pocket.core.app;

import android.app.Activity;
import android.content.Context;

import com.dididi.pocket.core.BackgroundTasks;
import com.dididi.pocket.core.im.BaseIMConfigs;
import com.dididi.pocket.core.util.Constants;
import com.dididi.pocket.core.util.LogUtil;
import com.mikepenz.iconics.typeface.ITypeface;
import com.mikepenz.iconics.utils.GenericsUtil;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;

/**
 * Created by dididi
 * on 17/07/2018 .
 */

public class Configurator {
    private static final String TAG = "Configurator";
    /**
     * 使用HashMap不用WeakHashMap,不会被自动回收,配置文件需要伴随整个app的生命周期(使用key限制传入值)
     */
    private static final HashMap<Enum<ConfigType>, Object> POCKET_CONFIGS = new HashMap<>();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final HashMap<String, ITypeface> ICONS = new HashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static BaseIMConfigs baseIMConfigs;

    /**
     * 私有构造函数
     */
    private Configurator() {
        //初始化配置为false
        POCKET_CONFIGS.put(ConfigType.CONFIG_READY, false);
    }

    /**
     * 静态内部类单例模式
     */
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Enum<ConfigType>, Object> getPocketConfigs() {
        return POCKET_CONFIGS;
    }

    /**
     * 配置方法
     */
    public final void configure() {
        initIcons();
        //配置已完成
        POCKET_CONFIGS.put(ConfigType.CONFIG_READY, true);
        LogUtil.d(TAG, "configure succeed");
    }

    /**
     * 传入域名
     */
    public final Configurator withApiHost(String host) {
        POCKET_CONFIGS.put(ConfigType.API_HOST, host);
        return this;
    }

    /**
     * 传入自定义Icon
     */
    public final Configurator withIcon(ITypeface iTypeface) {
        validateFont(iTypeface);
        ICONS.put(iTypeface.getMappingPrefix(), iTypeface);
        return this;
    }

    /**
     * 传入拦截器
     */
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

    public final Configurator withActivity(Activity activity) {
        POCKET_CONFIGS.put(ConfigType.ACTIVITY, activity);
        return this;
    }

    /**
     * 传入腾讯云通信配置文件
     *
     * @param sdkAppId 腾讯云通信sdk App id
     * @param config 腾讯云通信配置
     */
    public final Configurator withTimConfig(Context context, int sdkAppId, BaseIMConfigs config) {
        baseIMConfigs = config;
        baseIMConfigs.setAppCacheDir(context.getFilesDir().getPath());
        POCKET_CONFIGS.put(ConfigType.TIM_BASE_CONFIG, config);
        initTimConfig(context, sdkAppId);
        return this;
    }

    /**
     * 初始化腾讯云通信配置
     */
    private void initTimConfig(Context context, int sdkAppId) {
        //初始化sdk配置
        TIMSdkConfig sdkConfig = baseIMConfigs.getSdkConfig();
        if (sdkConfig == null) {
            sdkConfig = new TIMSdkConfig(sdkAppId)
                    .setAccoutType(Constants.TIM_ACCOUNT_TYPE)
                    .setLogLevel(TIMLogLevel.DEBUG)
                    .enableLogPrint(true);
        }
        TIMManager.getInstance().init(context, sdkConfig);
        //初始化用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //用户状态监听
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        if (baseIMConfigs.getImEventListener() != null) {
                            baseIMConfigs.getImEventListener().onForceOffline();
                        }
                    }

                    @Override
                    public void onUserSigExpired() {
                        if (baseIMConfigs.getImEventListener() != null) {
                            baseIMConfigs.getImEventListener().onUserSignExpired();
                        }
                    }
                })
                //连接状态监听
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        if (baseIMConfigs.getImEventListener() != null) {
                            baseIMConfigs.getImEventListener().onConnected();
                        }
                    }

                    @Override
                    public void onDisconnected(int i, String s) {
                        if (baseIMConfigs.getImEventListener() != null) {
                            baseIMConfigs.getImEventListener().onDisconnected(i, s);
                        }
                    }

                    @Override
                    public void onWifiNeedAuth(String s) {
                        if (baseIMConfigs.getImEventListener() != null) {
                            baseIMConfigs.getImEventListener().onWifiNeedAuth(s);
                        }
                    }
                })
                //刷新监听
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {

                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> list) {
                        if (baseIMConfigs.getImEventListener() != null) {
                            baseIMConfigs.getImEventListener().onRefreshConversation(list);
                        }
                    }
                })
                //群组监听
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem timGroupTipsElem) {
                        if (baseIMConfigs.getImEventListener() != null) {
                            baseIMConfigs.getImEventListener().onGroupTipsEvent(timGroupTipsElem);
                        }
                    }
                });
        //新消息通知
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                if (baseIMConfigs.getImEventListener() != null) {
                    baseIMConfigs.getImEventListener().onNewMessage(list);
                }
                return true;
            }
        });
        TIMUserConfigMsgExt ext = new TIMUserConfigMsgExt(userConfig);
        //禁用自动上报，通过调用已读上报接口来实现已读功能
        ext.setAutoReportEnabled(false);
        TIMManager.getInstance().setUserConfig(ext);
        BackgroundTasks.Companion.initInstance();
    }

    /**
     * 初始化icon
     */
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
    /** 获取配置文件 */
    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) POCKET_CONFIGS.get(key);
    }

    /**
     * 设置配置文件
     */
    final void setConfiguration(Enum<ConfigType> key, Object object) {
        POCKET_CONFIGS.put(key, object);
    }

    /**
     * 确认是否配置完成
     */
    private void checkConfiguration() {
        //写类变量和方法变量的时候尽量让它的不可变性达到最大(能用final修饰就用)
        final boolean isReady = (boolean) POCKET_CONFIGS.get(ConfigType.CONFIG_READY);
        if (!isReady) {
            //保证配置完成,如果没有准备好,抛出运行异常
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    /**
     * 是否是有效字体库
     */
    private static void validateFont(ITypeface font) {
        if (font.getMappingPrefix().length() != 3) {
            throw new IllegalArgumentException(
                    "The mapping prefix of a font must be three characters long.");
        }
    }
}

package com.softvision.botanica;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.softvision.botanica.bl.BusinessLogic;
import com.softvision.botanica.common.util.GeoLocationUtils;

public class BotanicaApplication extends Application {
    private static Context appContext;
    private static Handler mainHandler;

    public static Context getContext() {
        return appContext;
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        mainHandler = new Handler();
        GeoLocationUtils.lazyInitializeSingleton(appContext);
        BusinessLogic.getFacade().setContext(appContext);
    }
}

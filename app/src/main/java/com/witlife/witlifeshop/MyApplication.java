package com.witlife.witlifeshop;

import android.app.Application;
import android.content.Context;

/**
 * Created by bruce on 10/08/2017.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
    }

    public static Context getContext(){
        return context;
    }
}

package com.witlife.witlifeshop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bruce on 10/08/2017.
 */

public class CacheUtils {


    public static final String WITLIFE_SHOP = "witlife_shop";

    public static String getString(Context context, String key){
        String result = "";

        SharedPreferences sp = context.getSharedPreferences(WITLIFE_SHOP, context.MODE_PRIVATE);
        result = sp.getString(key, "");
        return result;
    }

    public static void putString(Context context, String key, String value) {

        SharedPreferences sp = context.getSharedPreferences(WITLIFE_SHOP, context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }
}

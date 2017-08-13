package com.witlife.witlifeshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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

    public static void putBitmap(String imageUrl, Bitmap bitmap){

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            try {
                String fileName = MD5Encoder.encode(imageUrl);
                File file = new File(Environment.getExternalStorageDirectory() + "witlifeShop", fileName);

                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }

                if (!file.exists()) {
                    file.createNewFile();
                }

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));

            } catch(Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static Bitmap getBitmap(String imageUrl){

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                String fileName = MD5Encoder.encode(imageUrl);

                File file = new File(Environment.getExternalStorageDirectory() + "witlifeShop", fileName);

                if (file.exists()){
                    FileInputStream is = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    return bitmap;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

package com.witlife.witlifeshop.http;

import android.content.pm.LauncherApps;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.witlife.witlifeshop.base.BaseCallback;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 8/08/2017.
 */

public class HttpHelper {

    private OkHttpClient client;
    private Handler handler;
    private static  HttpHelper httpHelper;

    public HttpHelper() {
        client = new OkHttpClient();

        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);

        handler = new Handler(Looper.getMainLooper());
    }

    public static HttpHelper getInstance(){
        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        return httpHelper;
    }

    public void httpPost(String url, BaseCallback callback, Map<String, String> params){
        Request request = buildRequest(url, HttpMethod.POST, params);
        doRequest(request, callback);
    }

    public void httpGet(String url, BaseCallback callback){
        Request request = buildRequest(url, HttpMethod.GET, null);
        doRequest(request, callback);
    }

    private void doRequest(Request request, final BaseCallback callback) {

        callback.onBeforeRequest(request);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callbackFailure(callback, request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {

                callbackResponse(callback, response);

                if (response.isSuccessful()) {
                    String result = response.body().string();

                    if (callback.mType == String.class){
                        callbackSuccess(callback, response, result);

                    } else {
                        Object object = new Gson().fromJson(result, callback.mType);
                        callbackSuccess(callback, response, object);
                    }
                } else {
                    callbackError(callback, response, null);
                }
            }
        });
    }

    public Request buildRequest(String url, HttpMethod method, Map<String, String> params){

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        if (method == HttpMethod.GET){
            builder.get();
        } else if (method == HttpMethod.POST) {
            RequestBody body = buildFromData(params);
            builder.post(body);
        }

        return builder.build();
    }

    private RequestBody buildFromData(Map<String, String> params) {

        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null) {
            for (Map.Entry<String,String> entry: params.entrySet()){
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private void callbackError(final BaseCallback callback, final Response response, final Exception e){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), null);
            }
        });
    }

    private void callbackSuccess(final BaseCallback callback, final Response response, final Object object){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, object);
            }
        });
    }

    private void callbackFailure(final BaseCallback callback, final Request request, final Exception e){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }

    private void callbackResponse(final BaseCallback callback, final Response response){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response);
            }
        });
    }

    enum HttpMethod{
        GET,
        POST
    }
}

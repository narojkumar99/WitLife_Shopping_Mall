package com.witlife.witlifeshop.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.base.BaseCallback;
import com.witlife.witlifeshop.bean.MainFragmentBean;
import com.witlife.witlifeshop.http.HttpHelper;

/**
 * Created by bruce on 25/08/2017.
 */

public abstract class LoadingPage extends FrameLayout {

    private static final int STATE_LOADING = 1;
    private static final int STATE_EMPTY = 2;
    private static final int STATE_ERROR = 3;
    private static final int STATE_SUCCESS = 4;

    private Context context;
    private View view_empty;
    private View view_error;
    private View view_loading;
    private int current_state = STATE_LOADING;
    private View view_success;
    private LayoutParams params;

    private HttpHelper httpHelper = HttpHelper.getInstance();

    public LoadingPage(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        if (view_empty == null) {
            view_empty = View.inflate(context, R.layout.page_empty, null);
            addView(view_empty, params);
        }
        if (view_error == null) {
            view_error = View.inflate(context, R.layout.page_error, null);
            addView(view_error, params);
        }
        if (view_loading == null) {
            view_loading = View.inflate(context, R.layout.page_loading, null);
            addView(view_loading, params);
        }
        if (view_success == null) {
            view_success = View.inflate(context, layoutId(), null);
            addView(view_success, params);
        }
        showPage();

    }

    public abstract int layoutId();

    private void showPage() {
        //show on UI thread
        view_empty.setVisibility(GONE);
        view_loading.setVisibility(GONE);
        view_error.setVisibility(GONE);
        view_success.setVisibility(GONE);

        switch (current_state) {
            case STATE_LOADING:
                view_loading.setVisibility(VISIBLE);
                break;
            case STATE_EMPTY:
                view_empty.setVisibility(VISIBLE);
                break;
            case STATE_ERROR:
                view_error.setVisibility(VISIBLE);
                break;
            case STATE_SUCCESS:
                view_success.setVisibility(VISIBLE);
                break;
        }
    }

    public void getInternetData() {
        if (TextUtils.isEmpty(url())) {
            current_state = STATE_EMPTY;
            showPage();
            return;
        }

        httpHelper.httpGet(url(), new BaseCallback<String>() {
            @Override
            public void onBeforeRequest(Request request) {
                current_state = STATE_LOADING;
                showPage();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                current_state = STATE_ERROR;
                showPage();
            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, String content) {
                current_state = STATE_SUCCESS;
                dataOnSuccess(content, view_success);
                showPage();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                current_state = STATE_EMPTY;
                showPage();
            }
        });
    }

    protected abstract void dataOnSuccess(String content, View view_success);

    protected abstract String url();
}

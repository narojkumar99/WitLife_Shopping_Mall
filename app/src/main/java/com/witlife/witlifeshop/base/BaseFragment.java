package com.witlife.witlifeshop.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.bean.MainFragmentBean;
import com.witlife.witlifeshop.view.LoadingPage;

/**
 * Created by bruce on 7/08/2017.
 */

public abstract class BaseFragment extends Fragment{

    private Context context;
    private LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getContext();

        loadingPage = new LoadingPage(context){

            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected void dataOnSuccess(String content, View view_success) {
                initView(view_success);
                initData(content);

            }

            @Override
            protected String url() {
                return getUrl();
            }
        };

        return loadingPage;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoadingPage();
    }

    private void showLoadingPage() {
        loadingPage.getInternetData();
    }

    protected abstract String getUrl();

    protected abstract void initView(View view);

    protected abstract void initData(String content);

    protected abstract int getLayoutId();
}

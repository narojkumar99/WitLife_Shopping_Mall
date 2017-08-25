package com.witlife.witlifeshop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.adapter.GridViewListAdapter;
import com.witlife.witlifeshop.adapter.MainRecyclerAdapter;
import com.witlife.witlifeshop.base.BaseCallback;
import com.witlife.witlifeshop.base.BaseFragment;
import com.witlife.witlifeshop.bean.MainFragmentBean;
import com.witlife.witlifeshop.http.HttpHelper;
import com.witlife.witlifeshop.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.witlife.witlifeshop.utils.Constants.BASE_URl_IMAGE;

/**
 * Created by bruce on 7/08/2017.
 */

public class MainFragment extends BaseFragment {
    private Context context;

    private EditText edSearch;
    private Button messageBtn;
    private RecyclerView recyclerView;

    private MainRecyclerAdapter adapter;

    private MainFragmentBean.ResultBean resultBean;

    protected void initData(String content) {

        MainFragmentBean bean = new Gson().fromJson(content, MainFragmentBean.class);

        resultBean = bean.getResult();
        initRecyclerView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    protected String getUrl() {
        return Constants.HOME_URL;
    }

    public void initView(View view) {

        edSearch = (EditText) view.findViewById(R.id.ed_search);
        messageBtn = (Button) view.findViewById(R.id.messageBtn);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void initRecyclerView() {

        adapter = new MainRecyclerAdapter(getContext(), resultBean);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(manager);
    }
}

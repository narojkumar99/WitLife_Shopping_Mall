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
    private View view;
    private RecyclerView recyclerView;

    private MainRecyclerAdapter adapter;

    private MainFragmentBean.ResultBean resultBean;

    private HttpHelper httpHelper = HttpHelper.getInstance();
    private String url;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_fragment, null);
        context = getContext();

        initData();
        initView();

        return view;
    }

    private void initData() {

        url = Constants.HOME_URL;

        httpHelper.httpGet(url, new BaseCallback<MainFragmentBean>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, MainFragmentBean bean) {

                resultBean = bean.getResult();
                initRecyclerView();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    public void initView() {
        initSearchBar();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void initRecyclerView() {

        adapter = new MainRecyclerAdapter(context, resultBean);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(manager);
    }

    private void initSearchBar() {
        edSearch = (EditText) view.findViewById(R.id.ed_search);
        messageBtn = (Button) view.findViewById(R.id.messageBtn);

    }

    @Override
    public void onStop() {
        // sliderShow.stopAutoCycle();
        super.onStop();
    }
}

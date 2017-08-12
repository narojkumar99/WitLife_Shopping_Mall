package com.witlife.witlifeshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.witlife.witlifeshop.adapter.CartListRecyclerAdapter;
import com.witlife.witlifeshop.base.BaseFragment;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.bean.CartBean;
import com.witlife.witlifeshop.bean.GoodsBean;

import java.util.List;

/**
 * Created by bruce on 7/08/2017.
 */

public class CartFragment extends BaseFragment implements View.OnClickListener {

    private Button editBtn;
    private Button calBtn;
    private Button deleteBtn;
    private Button collectBtn;
    private RecyclerView recyclerView;
    private CheckBox allChecked;
    private TextView textView;
    private TextView tvTotal;

    private boolean isEditStatus = true;

    private List<GoodsBean> goodsBeans;
    CartListRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart_fragment, null);
        bindView(view);

        initView();
        setListener();
        return view;
    }

    private void initView() {
        goodsBeans = CartBean.getInstance().getAllData();

        if(goodsBeans != null && goodsBeans.size() > 0){
            adapter = new CartListRecyclerAdapter(getContext(), goodsBeans, tvTotal, allChecked);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);
            DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
            recyclerView.addItemDecoration(divider);

            showTotalPrice();
        }
        initAllCheckbox();
        Log.e("fragment--ALL BOX", allChecked.isChecked()+"");
    }

    private void initAllCheckbox() {
        if(goodsBeans != null && goodsBeans.size() >0){
            adapter.checkAllItemCheckbox();
        } else allChecked.setChecked(false);
    }

    private void showTotalPrice() {
        double total = 0.0;
        for (int i = 0; i< goodsBeans.size(); i++){
            GoodsBean goodsBean = goodsBeans.get(i);
            if (goodsBean.isChildSelected()){
                total += Double.parseDouble(goodsBean.getCover_price()) * goodsBean.getNumber();

            }
        }
        tvTotal.setText("$" + String.valueOf(total));
    }

    private void bindView(View view) {
        editBtn = (Button) view.findViewById(R.id.editBtn);
        calBtn = (Button) view.findViewById(R.id.calBtn);
        deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
        collectBtn = (Button) view.findViewById(R.id.collectBtn);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        allChecked = (CheckBox) view.findViewById(R.id.allChecked);
        textView = (TextView) view.findViewById(R.id.textview);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
    }

    private void setListener() {
        editBtn.setOnClickListener(this);
        calBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        collectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.editBtn:
                setEditButtonState();
                break;
            case R.id.calBtn:
                Toast.makeText(getContext(), "结算", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteBtn:
                adapter.deleteBean();
                showTotalPrice();
                if(goodsBeans != null && goodsBeans.size() >0){
                    adapter.checkAllItemCheckbox();
                } else allChecked.setChecked(false);
                break;
            case R.id.collectBtn:
                break;
            default:
                break;
        }
    }

    private void setEditButtonState() {
        if (isEditStatus) {
            isEditStatus = false;
            editBtn.setText("完成");
            calBtn.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            tvTotal.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.VISIBLE);
            collectBtn.setVisibility(View.VISIBLE);
        } else {
            isEditStatus = true;
            editBtn.setText("编辑");
            calBtn.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            tvTotal.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.GONE);
            collectBtn.setVisibility(View.GONE);
        }
    }
}

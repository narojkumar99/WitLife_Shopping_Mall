package com.witlife.witlifeshop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.bean.CartBean;
import com.witlife.witlifeshop.bean.GoodsBean;
import com.witlife.witlifeshop.utils.Constants;
import com.witlife.witlifeshop.view.CartListViewHolder;
import com.witlife.witlifeshop.view.NumberChangerSubView;

import java.util.List;

/**
 * Created by bruce on 10/08/2017.
 */

public class CartListRecyclerAdapter extends RecyclerView.Adapter<CartListViewHolder>  {

    private final List<GoodsBean> goodsBeans;
    private Context context;
    private OnItemClickListener listener;
    private TextView tvTotal;
    private CheckBox allChecked;
    private boolean isAllChecked = true;

    public CartListRecyclerAdapter(Context context, List<GoodsBean> goodsBeans, TextView tvTotal, CheckBox allChecked) {
        this.context = context;
        this.goodsBeans = goodsBeans;
        this.tvTotal = tvTotal;
        this.allChecked = allChecked;

        allChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                allItemsCheckState(isChecked);
                Log.e("All checkbox", isChecked+"");
                showTotalPrice();
            }
        });
    }

    private void allItemsCheckState(boolean isChecked) {
        if (goodsBeans != null && goodsBeans.size()> 0){
            for(int i = 0; i < goodsBeans.size(); i++){
                goodsBeans.get(i).setIsChildSelected(isChecked);
                Log.e("checkbox"+i, isChecked+"");
                notifyItemChanged(i);
            }
           // notifyDataSetChanged();
        }

    }

    @Override
    public CartListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, null);//??

        return new CartListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(final CartListViewHolder holder, int position) {
        final GoodsBean item = goodsBeans.get(position);

        holder.tvPrice.setText("$" + item.getCover_price());
        holder.tvTitle.setText(item.getName());
        Glide.with(context)
                .load(Constants.BASE_URl_IMAGE + item.getFigure())
                .into(holder.imageview);
        holder.numberView.setValue(item.getNumber());

        if (item.isChildSelected()) {
            holder.checkbox.setChecked(true);
        } else {
            holder.checkbox.setChecked(false);
            isAllChecked = false;
            allChecked.setChecked(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(item.isChildSelected()) {
                    item.setIsChildSelected(false);
                    holder.checkbox.setChecked(false);
                    allChecked.setChecked(false);
                } else {
                    item.setIsChildSelected(true);
                    holder.checkbox.setChecked(true);
                    allChecked.setChecked(true);
                    for(int i = 0; i< goodsBeans.size();i++){
                        if(!goodsBeans.get(i).isChildSelected()) {
                            allChecked.setChecked(false);
                            break;
                        }
                    }
                }

                CartBean.getInstance().UpdateData(item);
                showTotalPrice();
                notifyDataSetChanged();
            }
        });

        holder.numberView.setOnNumberChangeListener(new NumberChangerSubView.OnNumberChangeListener() {
            @Override
            public void addNumber(View view, int value) {
                item.setNumber(value);
                CartBean.getInstance().UpdateData(item);
                showTotalPrice();
                notifyDataSetChanged();
            }

            @Override
            public void subNumber(View view, int value) {
                item.setNumber(value);
                CartBean.getInstance().UpdateData(item);
                showTotalPrice();
                notifyDataSetChanged();
            }
        });
    }

    private void showTotalPrice() {
        double total = 0.0;
        for (int i = 0; i< goodsBeans.size(); i++){
            GoodsBean goodsBean = goodsBeans.get(i);
            if (goodsBean.isChildSelected()){
                total += Double.parseDouble(goodsBean.getCover_price()) * goodsBean.getNumber();

            }
        }
        tvTotal.setText(String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return goodsBeans.size();
    }


    public interface OnItemClickListener{
       void onItemClick(View view, int position);//??
    }

    public void setOnItemClickListener(OnItemClickListener listener ){
        this.listener = listener;
    }
}

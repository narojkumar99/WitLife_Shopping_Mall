package com.witlife.witlifeshop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.bean.GoodsBean;
import com.witlife.witlifeshop.bean.MainFragmentBean;
import com.witlife.witlifeshop.utils.Constants;

import java.util.List;

/**
 * Created by bruce on 8/08/2017.
 */

class SecskillAdapter extends RecyclerView.Adapter<SecskillAdapter.MyViewHolder> {

    private final List<MainFragmentBean.ResultBean.SeckillInfoBean.ListBean> secskills;
    private Context context;
    private View.OnClickListener listener;

    public SecskillAdapter(Context context, List<MainFragmentBean.ResultBean.SeckillInfoBean.ListBean> seckills) {
        this.context = context;
        this.secskills = seckills;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_secskill, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context)
                .load(Constants.BASE_URl_IMAGE + secskills.get(position).getFigure())
                .into(holder.imageview);
        holder.tvOriginalPrice.setText("$" + secskills.get(position).getOrigin_price());
        holder.tvOriginalPrice.setPaintFlags(holder.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvCoverPrice.setText("$" + secskills.get(position).getCover_price());

    }

    @Override
    public int getItemCount() {
        return secskills.size();
    }

    public void setClickListener(View.OnClickListener callback){
        listener = callback;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageview;
        public TextView tvCoverPrice;
        public TextView tvOriginalPrice;
        public LinearLayout linear;

        public MyViewHolder(View view) {
            super(view);

            imageview = (ImageView) view.findViewById(R.id.imageview);
            tvCoverPrice = (TextView) view.findViewById(R.id.tvCoverPrice);
            tvOriginalPrice = (TextView) view.findViewById(R.id.tvOriginalPrice);
            linear = (LinearLayout) view.findViewById(R.id.linear);
        }
    }
}

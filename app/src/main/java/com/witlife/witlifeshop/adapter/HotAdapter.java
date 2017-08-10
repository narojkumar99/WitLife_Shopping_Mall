package com.witlife.witlifeshop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.bean.MainFragmentBean;
import com.witlife.witlifeshop.utils.Constants;

import java.util.List;

/**
 * Created by bruce on 8/08/2017.
 */

class HotAdapter extends BaseAdapter{

    private Context context;
    private List<MainFragmentBean.ResultBean.HotInfoBean> hots;

    public HotAdapter(Context context, List<MainFragmentBean.ResultBean.HotInfoBean> hots) {
        this.context = context;
        this.hots = hots;
    }

    @Override
    public int getCount() {
        return hots.size();
    }

    @Override
    public Object getItem(int position) {
        return hots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(context, R.layout.item_hot, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        Glide.with(context)
                .load(Constants.BASE_URl_IMAGE + hots.get(position).getFigure())
                .into(imageView);

        tvPrice.setText("$" + hots.get(position).getCover_price());
        tvTitle.setText(hots.get(position).getName());
        return convertView;
    }
}

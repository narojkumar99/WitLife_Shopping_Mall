package com.witlife.witlifeshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.witlife.witlifeshop.bean.MainFragmentBean;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.utils.Constants;

import java.util.List;

/**
 * Created by bruce on 8/08/2017.
 */

public class GridViewListAdapter extends BaseAdapter {

    private List<MainFragmentBean.ResultBean.ChannelInfoBean> channels;
    private Context context;
    private TextView channelTitle;
    private ImageView imageview;

    public GridViewListAdapter(Context context, List<MainFragmentBean.ResultBean.ChannelInfoBean> channels) {
        this.context = context;
        this.channels = channels;
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public MainFragmentBean.ResultBean.ChannelInfoBean getItem(int position) {
        return channels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(context, R.layout.channal_gridview, null);
        imageview = (ImageView) convertView.findViewById(R.id.imageview);
        channelTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        if(channels != null){
            channelTitle.setText(channels.get(position).getChannel_name());

            Glide.with(context)
                    .load(Constants.BASE_URl_IMAGE + channels.get(position).getImage())
                    .into(imageview);

        }
        return convertView;
    }
}

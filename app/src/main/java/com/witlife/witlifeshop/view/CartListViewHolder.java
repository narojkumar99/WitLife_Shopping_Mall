package com.witlife.witlifeshop.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.adapter.CartListRecyclerAdapter;

/**
 * Created by bruce on 10/08/2017.
 */

public class CartListViewHolder extends RecyclerView.ViewHolder {

    public CheckBox checkbox;
    public ImageView imageview;
    public TextView tvTitle;
    public TextView tvPrice;
    public NumberChangerSubView numberView;
    private CartListRecyclerAdapter.OnItemClickListener itemClickListener;

    public CartListViewHolder(View view, final CartListRecyclerAdapter.OnItemClickListener listener) {
        super(view);

        itemClickListener = listener;

        checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        tvPrice= (TextView) view.findViewById(R.id.tvPrice);
        tvTitle= (TextView) view.findViewById(R.id.tvTitle);
        imageview= (ImageView) view.findViewById(R.id.imageview);
        numberView= (NumberChangerSubView) view.findViewById(R.id.number);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(v, getLayoutPosition());
                }
            }
        });
    }
}

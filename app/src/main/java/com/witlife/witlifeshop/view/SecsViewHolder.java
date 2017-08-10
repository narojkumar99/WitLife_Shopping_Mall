package com.witlife.witlifeshop.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.witlife.witlifeshop.R;

/**
 * Created by bruce on 8/08/2017.
 */

public class SecsViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView recyclerView;
    private TextView textView;

    public SecsViewHolder(Context context, View view) {
        super(view);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewItem);
        textView = (TextView) view.findViewById(R.id.tvTime);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}

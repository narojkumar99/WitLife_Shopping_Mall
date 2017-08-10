package com.witlife.witlifeshop.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;

import com.witlife.witlifeshop.R;

/**
 * Created by bruce on 8/08/2017.
 */

public class RecomViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private GridView gridview;

    public RecomViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        gridview = (GridView) view.findViewById(R.id.recommendGV);
    }

    public GridView getGridview() {
        return gridview;
    }

    public void setGridview(GridView gridview) {
        this.gridview = gridview;
    }
}

package com.witlife.witlifeshop.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;

import com.witlife.witlifeshop.R;

/**
 * Created by bruce on 8/08/2017.
 */

public class ChannelViewHolder extends RecyclerView.ViewHolder {

    private GridView gridView;

    public GridView getGridView() {
        return gridView;
    }

    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

    public ChannelViewHolder(View view) {
        super(view);

        gridView = (GridView) view.findViewById(R.id.gridview);

    }


}

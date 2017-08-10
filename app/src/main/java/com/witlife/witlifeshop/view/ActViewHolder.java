package com.witlife.witlifeshop.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.witlife.witlifeshop.R;

/**
 * Created by bruce on 8/08/2017.
 */

public class ActViewHolder extends RecyclerView.ViewHolder {

    private ViewPager viewPager;

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public ActViewHolder(Context context, View view) {
        super(view);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //viewPager.setPageTransformer(true, new An(1.0, 0.5));
    }
}

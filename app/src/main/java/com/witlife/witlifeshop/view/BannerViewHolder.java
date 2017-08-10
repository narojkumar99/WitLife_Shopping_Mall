package com.witlife.witlifeshop.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.witlife.witlifeshop.R;

/**
 * Created by bruce on 8/08/2017.
 */

public class BannerViewHolder extends RecyclerView.ViewHolder {

    private SliderLayout sliderShow;

    public SliderLayout getSliderShow() {
        return sliderShow;
    }

    public void setSliderShow(SliderLayout sliderShow) {
        this.sliderShow = sliderShow;
    }

    public BannerViewHolder(View view) {
        super(view);

        sliderShow = (SliderLayout) view.findViewById(R.id.slider);
    }
}

package com.witlife.witlifeshop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.bean.GoodsBean;
import com.witlife.witlifeshop.bean.MainFragmentBean;
import com.witlife.witlifeshop.utils.Constants;

import java.util.List;

/**
 * Created by bruce on 8/08/2017.
 */

class ActViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<MainFragmentBean.ResultBean.ActInfoBean> acts;

    public ActViewPagerAdapter(Context context, List<MainFragmentBean.ResultBean.ActInfoBean> acts) {
        this.context = context;
        this.acts = acts;
    }

    @Override
    public int getCount() {
        return acts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(R.drawable.home_scroll_default);

        container.addView(imageView);
        Glide.with(context)
                .load(Constants.BASE_URl_IMAGE + acts.get(position).getIcon_url())
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}

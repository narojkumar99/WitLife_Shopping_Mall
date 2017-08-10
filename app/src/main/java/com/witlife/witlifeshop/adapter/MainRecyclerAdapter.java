package com.witlife.witlifeshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.witlife.witlifeshop.activity.DetailInfoActivity;
import com.witlife.witlifeshop.activity.MainActivity;
import com.witlife.witlifeshop.bean.GoodsBean;
import com.witlife.witlifeshop.bean.MainFragmentBean;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.view.ActViewHolder;
import com.witlife.witlifeshop.view.BannerViewHolder;
import com.witlife.witlifeshop.view.ChannelViewHolder;
import com.witlife.witlifeshop.view.HotViewHolder;
import com.witlife.witlifeshop.view.RecomViewHolder;
import com.witlife.witlifeshop.view.SecsViewHolder;

import java.util.List;

import static com.witlife.witlifeshop.utils.Constants.BASE_URl_IMAGE;

/**
 * Created by bruce on 8/08/2017.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter{

    public static final String GOODS_BEAN = "goods_bean";
    private static int BANNER = 0;
    private static int CHANNEL = 1;
    private static int ACT = 2;
    private static int SECSKILL = 3;
    private static int RECOMMEND = 4;
    private static int HOT = 5;

    private Context context;
    private MainFragmentBean.ResultBean resultBean;

    public MainRecyclerAdapter(Context context, MainFragmentBean.ResultBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == BANNER) {
            View view = LayoutInflater.from(context).inflate(R.layout.main_banner_layout, parent, false);
            return new BannerViewHolder(view);
        } else if(viewType == CHANNEL){
            View view = LayoutInflater.from(context).inflate(R.layout.main_channal_layout, parent, false);
            return new ChannelViewHolder(view);
        } else if (viewType == ACT) {
            View view = LayoutInflater.from(context).inflate(R.layout.main_act_layout, parent, false);
            return new ActViewHolder(context, view);
        } else if (viewType == SECSKILL){
            View view = LayoutInflater.from(context).inflate(R.layout.main_secskill_layout, parent, false);
            return new SecsViewHolder(context, view);
        } else if(viewType == RECOMMEND){
            View view = LayoutInflater.from(context).inflate(R.layout.main_recommend_layout, parent, false);
            return new RecomViewHolder(context, view);
        } else if(viewType == HOT) {
            View view = LayoutInflater.from(context).inflate(R.layout.main_hot_layout, parent, false);
            return new HotViewHolder(context, view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == BANNER) {
            bindBanner((BannerViewHolder) holder, resultBean.getBanner_info());
        } else if (position == CHANNEL) {
            bindChannel((ChannelViewHolder) holder, resultBean.getChannel_info());
        } else if(position == ACT) {
            bindAct((ActViewHolder) holder, resultBean.getAct_info());
        } else if(position == SECSKILL){
            bindSecskill((SecsViewHolder)holder, resultBean.getSeckill_info());
        } else if(position == RECOMMEND){
            bindRecommend((RecomViewHolder)holder, resultBean.getRecommend_info());
        } else {
            bindHot((HotViewHolder)holder, resultBean.getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {

        switch (position){
            case 0:
                return BANNER;
            case 1:
                return CHANNEL;
            case 2:
                return ACT;
            case 3:
                return SECSKILL;
            case 4:
                return RECOMMEND;
            case 5:
                return HOT;
        }
        return -1;
    }

    private void bindBanner(BannerViewHolder holder, final List<MainFragmentBean.ResultBean.BannerInfoBean> banners) {

        if (banners != null) {
            for (int i = 0; i < banners.size(); i++) {
                DefaultSliderView sliderView = new DefaultSliderView(context);
                sliderView.image(BASE_URl_IMAGE + banners.get(i).getImage());
                final int position = i;
                sliderView.setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                clickEvent(banners, position);
                            }
                        });
                holder.getSliderShow().addSlider(sliderView);
            }
        }
        holder.getSliderShow().setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        holder.getSliderShow().setPresetTransformer(SliderLayout.Transformer.RotateUp);
        holder.getSliderShow().setDuration(2000);
    }

    private void clickEvent(List<MainFragmentBean.ResultBean.BannerInfoBean> banners, int position) {
        GoodsBean goodsBean = new GoodsBean();
        goodsBean.setFigure(banners.get(position).getImage());
        goodsBean.setCover_price("");
        goodsBean.setName("");
        goodsBean.setProduct_id("");

        Intent intent = new Intent(context, DetailInfoActivity.class);
        intent.putExtra(GOODS_BEAN, goodsBean);
        context.startActivity(intent);
    }

    private void bindChannel(ChannelViewHolder holder, List<MainFragmentBean.ResultBean.ChannelInfoBean> channels) {

        if(channels != null){
            GridViewListAdapter adapter = new GridViewListAdapter(context, channels);
            holder.getGridView().setAdapter(adapter);
        }
    }

    private void bindAct(ActViewHolder holder, List<MainFragmentBean.ResultBean.ActInfoBean> acts) {

        if (acts != null){
            ActViewPagerAdapter adapter = new ActViewPagerAdapter(context, acts);
            holder.getViewPager().setAdapter(adapter);
        }
    }

    private void bindSecskill(final SecsViewHolder holder, final MainFragmentBean.ResultBean.SeckillInfoBean seckills) {
        if(seckills != null){
            LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            SecskillAdapter adapter = new SecskillAdapter(context, seckills.getList());
            holder.getRecyclerView().setAdapter(adapter);
            holder.getRecyclerView().setLayoutManager(manager);
            holder.getTextView().setText(seckills.getEnd_time());

            final List<MainFragmentBean.ResultBean.SeckillInfoBean.ListBean> secBeans = seckills.getList();

            adapter.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getRecyclerView().getChildAdapterPosition(v);

                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setFigure(secBeans.get(position).getFigure());
                    goodsBean.setCover_price(secBeans.get(position).getCover_price());
                    goodsBean.setName(secBeans.get(position).getName());
                    goodsBean.setProduct_id(secBeans.get(position).getProduct_id());

                    Intent intent = new Intent(context, DetailInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    context.startActivity(intent);
                }
            });
        }

    }

    private void bindRecommend(RecomViewHolder holder, final List<MainFragmentBean.ResultBean.RecommendInfoBean> recommends) {
        if(recommends != null ){
            //GridLayoutManager manager = new GridLayoutManager(context, 3);
            RecommendAdapter adapter = new RecommendAdapter(context, recommends);
            holder.getGridview().setAdapter(adapter);

            holder.getGridview().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setFigure(recommends.get(position).getFigure());
                    goodsBean.setCover_price(recommends.get(position).getCover_price());
                    goodsBean.setName(recommends.get(position).getName());
                    goodsBean.setProduct_id(recommends.get(position).getProduct_id());

                    Intent intent = new Intent(context, DetailInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void bindHot(HotViewHolder holder, final List<MainFragmentBean.ResultBean.HotInfoBean> hots) {
        if(hots != null ){
            //GridLayoutManager manager = new GridLayoutManager(context, 3);
            HotAdapter adapter = new HotAdapter(context, hots);
            holder.getGridview().setAdapter(adapter);
            holder.getGridview().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setFigure(hots.get(position).getFigure());
                    goodsBean.setCover_price(hots.get(position).getCover_price());
                    goodsBean.setName(hots.get(position).getName());
                    goodsBean.setProduct_id(hots.get(position).getProduct_id());

                    Intent intent = new Intent(context, DetailInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }
}


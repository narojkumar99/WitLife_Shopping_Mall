package com.witlife.witlifeshop.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.bean.CartBean;
import com.witlife.witlifeshop.bean.GoodsBean;
import com.witlife.witlifeshop.utils.Constants;
import com.witlife.witlifeshop.view.CartListViewHolder;
import com.witlife.witlifeshop.view.NumberChangerSubView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by bruce on 10/08/2017.
 */

public class CartListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final List<GoodsBean> goodsBeans;
    private Context context;
    private OnItemClickListener listener = null;
    private TextView tvTotal;
    private CheckBox allCheckbox;

    public CartListRecyclerAdapter(Context context, final List<GoodsBean> goodsBeans, TextView tvTotal, final CheckBox allChecked) {
        this.context = context;
        this.goodsBeans = goodsBeans;
        this.tvTotal = tvTotal;
        this.allCheckbox = allChecked;

        Log.e("adapter--ALL BOX", allCheckbox.isChecked()+"");
        allCheckbox.setChecked(true);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GoodsBean goodsBean = goodsBeans.get(position);
                goodsBean.setIsChildSelected(!goodsBean.isChildSelected());
                notifyItemChanged(position);
                checkAllItemCheckbox();
                CartBean.getInstance().UpdateData(goodsBean);
                showTotalPrice();
            }
        });

        allCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = allCheckbox.isChecked();
                allItemsCheckState(isChecked);
                showTotalPrice();
            }
        });
    }

    public void checkAllItemCheckbox() { //setup ALL(checkbox), according to every item's checkbox
        allCheckbox.setChecked(true);
        for(int i = 0; i< goodsBeans.size();i++){
            if(!goodsBeans.get(i).isChildSelected()) {
                allCheckbox.setChecked(false);
                break;
            }
        }
    }

    private void allItemsCheckState(boolean isChecked) {// setup every item's checkbox, according to All(checkbox)
        if (goodsBeans != null && goodsBeans.size()> 0){
            for(int i = 0; i < goodsBeans.size(); i++){
                goodsBeans.get(i).setIsChildSelected(isChecked);
                notifyItemChanged(i);
            }
            allCheckbox.setChecked(isChecked);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, null);//??

        return new CartListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        final GoodsBean item = goodsBeans.get(position);
        CartListViewHolder holder = (CartListViewHolder)viewHolder;

        holder.tvPrice.setText("$" + item.getCover_price());
        holder.tvTitle.setText(item.getName());
        Glide.with(context)
                .load(Constants.BASE_URl_IMAGE + item.getFigure())
                .into(holder.imageview);
        holder.numberView.setValue(item.getNumber());

        holder.checkbox.setChecked(item.isChildSelected());

        holder.numberView.setOnNumberChangeListener(new NumberChangerSubView.OnNumberChangeListener() {
            @Override
            public void addNumber(View view, int value) {
                item.setNumber(value);
                CartBean.getInstance().UpdateData(item);
                showTotalPrice();
            }

            @Override
            public void subNumber(View view, int value) {
                item.setNumber(value);
                CartBean.getInstance().UpdateData(item);
                showTotalPrice();
            }
        });
    }

    private void showTotalPrice() {
        double total = 0.0;
        for (int i = 0; i< goodsBeans.size(); i++){
            GoodsBean goodsBean = goodsBeans.get(i);
            if (goodsBean.isChildSelected()){
                total += Double.parseDouble(goodsBean.getCover_price()) * goodsBean.getNumber();

            }
        }
        tvTotal.setText("$" + String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return goodsBeans.size();
    }

    public void deleteBean() {

        if(goodsBeans != null && goodsBeans.size() > 0){
            for(Iterator i = goodsBeans.iterator(); i.hasNext();){

                GoodsBean bean = (GoodsBean) i.next();
                if(bean.isChildSelected()){
                    int position = goodsBeans.indexOf(bean);
                    CartBean.getInstance().deleteData(bean);
                    i.remove();
                    notifyItemRemoved(position);
                }
            }
        }
    }


    public interface OnItemClickListener{
       void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}

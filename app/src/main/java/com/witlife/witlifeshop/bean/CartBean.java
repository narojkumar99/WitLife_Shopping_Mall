package com.witlife.witlifeshop.bean;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.witlife.witlifeshop.MyApplication;
import com.witlife.witlifeshop.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruce on 10/08/2017.
 */

public class CartBean {

    public static final String JSON_CART = "json_cart";
    private Context context;

    private SparseArray<GoodsBean> datas;
    private static CartBean cartBean;

    public CartBean(Context context) {
        this.context = context;
        datas = new SparseArray<>();
        listToSparse();
    }

    public static CartBean getInstance() {
        if(cartBean == null){
            cartBean = new CartBean(MyApplication.getContext());
        }
        return cartBean;
    }

    private void listToSparse() {
        List<GoodsBean> goodsBeans = getAllData();

        if(goodsBeans != null && goodsBeans.size() > 0) {
            for(int i = 0; i< goodsBeans.size(); i++){
                GoodsBean goodsBean = goodsBeans.get(i);
                datas.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }
        }
    }

    private List<GoodsBean> parseToList() {
        List<GoodsBean> goodsBeans = new ArrayList<>();

        if(datas != null && datas.size() > 0){
            for (int i = 0; i< datas.size(); i++){
                GoodsBean tempBean = datas.valueAt(i);
                goodsBeans.add(tempBean);
            }
        }
        return goodsBeans;
    }

    public List<GoodsBean> getAllData() {
        return getDataFromLocal();
    }

    private List<GoodsBean> getDataFromLocal() {
        List<GoodsBean> beans = new ArrayList<>();
        String savedJson = CacheUtils.getString(context, JSON_CART);

        if(!TextUtils.isEmpty(savedJson)){
            beans = new Gson().fromJson(savedJson, new TypeToken<List<GoodsBean>>(){}.getType());
        }

        return beans;
    }

    public void addGoods(GoodsBean goodsBean) {
        int key = Integer.parseInt(goodsBean.getProduct_id());
        goodsBean.setIsChildSelected(true);

        if(datas.get(key) != null){
            GoodsBean beanInDB = datas.get(key);
            goodsBean.setNumber(beanInDB.getNumber() + goodsBean.getNumber());
        }

        datas.put(key, goodsBean);
        commit();
    }

    private void commit() {
        List<GoodsBean> goodsBeans = parseToList();
        String json = new Gson().toJson(goodsBeans);

        CacheUtils.putString(context, JSON_CART, json);
    }

    public void UpdateData(GoodsBean goodsBean) {
        datas.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        commit();
    }

    public void deleteData(GoodsBean bean) {
        datas.delete(Integer.parseInt(bean.getProduct_id()));
        commit();
    }
}

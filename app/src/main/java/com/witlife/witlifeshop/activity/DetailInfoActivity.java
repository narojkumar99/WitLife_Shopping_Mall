package com.witlife.witlifeshop.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.bean.CartBean;
import com.witlife.witlifeshop.bean.GoodsBean;
import com.witlife.witlifeshop.activity.MainActivity;
import com.witlife.witlifeshop.utils.CacheUtils;
import com.witlife.witlifeshop.utils.Constants;
import com.witlife.witlifeshop.view.NumberChangerSubView;

import java.util.zip.Inflater;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by bruce on 9/08/2017.
 */

public class DetailInfoActivity extends AppCompatActivity{

    private GoodsBean goodsBean;
    private ImageButton backBtn;
    private ImageButton moreBtn;
    private ImageView imageview;
    private TextView tvTitle;
    private TextView tvPrice;
    private Button contactBtn;
    private Button collectBtn;
    private Button cartBtn;
    private Button addToBtn;
    private Button bgBtn;
    private Button shareBtn;
    private LinearLayout linearlayout;

    private CartBean cartBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_info);

        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goods_bean");

        bindView();

        if (goodsBean != null){
            initView();

            setListener();
        }

        cartBean = CartBean.getInstance();
    }

    private void setListener() {
        addToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //MainActivity
            }
        });

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearlayout.getVisibility() == View.VISIBLE){
                    linearlayout.setVisibility(View.GONE);
                } else linearlayout.setVisibility(View.VISIBLE);
            }
        });

        bgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearlayout.setVisibility(View.GONE);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
    }

    private void addToCart() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.popup_add_to_cart, null);
        final PopupWindow popup = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        //popup.setAnimationStyle();

        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        NumberChangerSubView nas_goodinfo_num = (NumberChangerSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        final RadioButton radioS = (RadioButton) view.findViewById(R.id.radioS);
        final RadioButton radioM = (RadioButton) view.findViewById(R.id.radioM);
        final RadioButton radioL = (RadioButton) view.findViewById(R.id.radioL);
        final RadioButton radioXL = (RadioButton) view.findViewById(R.id.radioXL);

        Glide.with(this).load(Constants.BASE_URl_IMAGE + goodsBean.getFigure()).into(iv_goodinfo_photo);
        tv_goodinfo_name.setText(goodsBean.getName());
        tv_goodinfo_price.setText("$" + goodsBean.getCover_price());

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                cartBean.addGoods(goodsBean);
                Toast.makeText(DetailInfoActivity.this, "添加购物车成功", Toast.LENGTH_LONG).show();
            }
        });

        nas_goodinfo_num.setOnNumberChangeListener(new NumberChangerSubView.OnNumberChangeListener() {
            @Override
            public void addNumber(View view, int value) {
                goodsBean.setNumber(value);
            }

            @Override
            public void subNumber(View view, int value) {
                goodsBean.setNumber(value);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                radioS.setChecked(false);
                radioM.setChecked(false);
                radioL.setChecked(false);
                radioXL.setChecked(false);

                switch (checkedId){
                    case R.id.radioS:
                        radioS.setChecked(true);
                        break;
                    case R.id.radioM:
                        radioM.setChecked(true);
                        break;
                    case R.id.radioL:
                        radioL.setChecked(true);
                        break;
                    case R.id.radioXL:
                        radioXL.setChecked(true);
                        break;
                }
            }
        });

        popup.showAtLocation(DetailInfoActivity.this.findViewById(R.id.container),
                Gravity.BOTTOM,0, 0);
    }

    private void initView() {
        Glide.with(this)
                .load(Constants.BASE_URl_IMAGE + goodsBean.getFigure())
                .into(imageview);
        if(!TextUtils.isEmpty(goodsBean.getName())) {
            tvTitle.setText(goodsBean.getName());
        } else tvTitle.setText("");

        if(!TextUtils.isEmpty(goodsBean.getCover_price())) {
            tvPrice.setText("$" + goodsBean.getCover_price());
        } else tvPrice.setText("$");

    }

    private void bindView() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        moreBtn = (ImageButton) findViewById(R.id.moreBtn);
        imageview = (ImageView) findViewById(R.id.imageview);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        contactBtn = (Button) findViewById(R.id.contactBtn);
        collectBtn = (Button) findViewById(R.id.collectBtn);
        cartBtn = (Button) findViewById(R.id.cartBtn);
        addToBtn = (Button) findViewById(R.id.addToBtn);
        shareBtn = (Button) findViewById(R.id.shareBtn);
        bgBtn = (Button) findViewById(R.id.bgBtn);
        linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(goodsBean.getName());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(Constants.BASE_URl_IMAGE + goodsBean.getFigure());

        //oks.setImagePath(Environment.getExternalStorageState()"/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(goodsBean.getName());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}

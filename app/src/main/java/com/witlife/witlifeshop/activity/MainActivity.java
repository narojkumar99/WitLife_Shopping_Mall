package com.witlife.witlifeshop.activity;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.witlife.witlifeshop.R;
import com.witlife.witlifeshop.bean.Tab;
import com.witlife.witlifeshop.fragment.CartFragment;
import com.witlife.witlifeshop.fragment.CategoryFragment;
import com.witlife.witlifeshop.fragment.CommunityFragment;
import com.witlife.witlifeshop.fragment.MainFragment;
import com.witlife.witlifeshop.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;
    private List<Tab> tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();

        initTabHost();
    }

    private void initTabHost() {
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_container);

        for (int i = 0; i <tabs.size(); i++){
            Tab tab = tabs.get(i);
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tab.getTitle());
            tabSpec.setIndicator(initIndicator(tab.getIcon(), tab.getTitle()));
            tabHost.addTab(tabSpec,tab.getFragment(), null);
        }

        tabHost.setCurrentTab(0);
    }

    private void initTab() {
        tabs = new ArrayList<>(5);
        tabs.add(new Tab("Main", R.drawable.main_home_selector, MainFragment.class));
        tabs.add(new Tab("Category", R.drawable.main_type_selector, CategoryFragment.class));
        tabs.add(new Tab("Community", R.drawable.main_community_selector, CommunityFragment.class));
        tabs.add(new Tab("Cart", R.drawable.main_cart_selector, CartFragment.class));
        tabs.add(new Tab("User", R.drawable.main_user_selector, UserFragment.class));

    }

    private View initIndicator(int icon, String title){
        ImageView imageview;
        TextView tvTitle;

        View view = View.inflate(this, R.layout.tab_indicator, null);
        imageview = (ImageView) view.findViewById(R.id.imageview);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        imageview.setImageResource(icon);
        tvTitle.setText(title);
        return view;
    }

    public FragmentTabHost getTabHost(){
        if(tabHost != null){
            return tabHost;
        } else return null;

    }
}

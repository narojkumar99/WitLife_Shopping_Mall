package com.witlife.witlifeshop.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.witlife.witlifeshop.R;

/**
 * Created by bruce on 9/08/2017.
 */

public class NumberChangerSubView extends LinearLayout implements View.OnClickListener {

    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;
    private Context context;

    private Button subBtn;
    private Button addBtn;
    private TextView tvValue;

    private OnNumberChangeListener listener;

    public NumberChangerSubView(Context context) {
        this(context, null);
    }

    public NumberChangerSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberChangerSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initView();
        setListener();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.number_change_view, this, true);

        subBtn = (Button) view.findViewById(R.id.subBtn);
        addBtn = (Button) view.findViewById(R.id.addBtn);
        tvValue = (TextView) view.findViewById(R.id.tvValue);

        tvValue.setText(String.valueOf(value));
    }

    private void setListener() {
        subBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tvValue.setText(value+ "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.subBtn:
                if(value > getMinValue()) {
                    value--;
                    setValue(value);
                }
                if(listener != null){
                    listener.addNumber(v, value);
                }
                break;

            case R.id.addBtn:
                if (value < getMaxValue()){
                    value++;
                    setValue(value);
                }
                if(listener != null){
                    listener.subNumber(v, value);
                }
                break;

            default:
                break;
        }
    }

    public interface OnNumberChangeListener {
        void addNumber(View view, int value);
        void subNumber(View view, int value);
    }

    public void setOnNumberChangeListener(OnNumberChangeListener listener){
        this.listener = listener;
    }
}

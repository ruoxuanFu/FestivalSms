package com.quaie.wms.myapplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quaie.wms.myapplication.R;

/**
 * Created by yue on 2017/1/12.
 * 　　　　　　　  ┏┓　 ┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　     ┃
 * 　　　　　　　┃　　　━　    ┃ ++ + + +
 * 　　　　　　 ████━████     ┃++  ++
 * 　　　　　　　┃　　　　　　 ┃ +
 * 　　　　　　　┃　　　┻　　　┃  +  +
 * 　　　　　　　┃　　　　　　 ┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */

public class TitleBar extends LinearLayout {

    private TextView mTitleTv;
    private TextView mTitleL;
    private TextView mTitleR;
    private String mTitleText;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_titlebar, this);
        mTitleTv = (TextView) this.findViewById(R.id.titlebar_tv_center);
        mTitleL = (TextView) this.findViewById(R.id.titlebar_tv_left);
        mTitleR = (TextView) this.findViewById(R.id.titlebar_tv_right);
        initAttrs(attrs);
        mTitleTv.setText(mTitleText);
    }

    private void initAttrs(AttributeSet attrs) {

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mTitleText = ta.getString(R.styleable.TitleBar_centerText);

    }

    public void setCenterText(String text) {
        mTitleText = text;
        mTitleTv.setText(mTitleText);
    }

    public void setLandRClick(OnClickListener LonClickListener, OnClickListener RonClickListener) {
        mTitleL.setOnClickListener(LonClickListener);
        mTitleR.setOnClickListener(RonClickListener);
    }
}

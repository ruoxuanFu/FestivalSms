package com.quaie.wms.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.quaie.wms.myapplication.Bean.FestivalLab;
import com.quaie.wms.myapplication.Bean.Msg;
import com.quaie.wms.myapplication.Fragment.FestivalCategoryFragment;
import com.quaie.wms.myapplication.Utils.L;
import com.quaie.wms.myapplication.View.TitleBar;

public class ChooseMsgActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mLvMsgs;
    private FloatingActionButton mFabToSend;
    private TitleBar mTitleBar;

    private ArrayAdapter<Msg> mAdapter;
    private int mFestivalId;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_msg);

        mInflater = LayoutInflater.from(this);
        mFestivalId = getIntent().getIntExtra(FestivalCategoryFragment.ID_FESTIVAL, -1);

        initView();
    }

    private void initView() {
        mLvMsgs = (ListView) findViewById(R.id.id_lv_msgs);
        mFabToSend = (FloatingActionButton) findViewById(R.id.id_fab_toSend);
        mTitleBar = (TitleBar) findViewById(R.id.id_tb_title);

        mFabToSend.setOnClickListener(this);

        mAdapter = new ArrayAdapter<Msg>(getApplicationContext(), -1, FestivalLab.getmInstance().getMsgByFestivalId(mFestivalId)) {
            @NonNull
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_msg, parent, false);
                }

                TextView content = (TextView) convertView.findViewById(R.id.id_tv_content);
                Button toSend = (Button) convertView.findViewById(R.id.id_bt_tosend);

                content.setText("    " + getItem(position).getContent());

                L.e(getItem(position).getContent());

                toSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendMsgActivity.toActivity(ChooseMsgActivity.this,
                                mFestivalId,
                                getItem(position).getId());
                    }
                });
                return convertView;
            }
        };

        mLvMsgs.setAdapter(mAdapter);

        mTitleBar.setCenterText(FestivalLab.getmInstance().getFestivalById(mFestivalId).getName());
        mTitleBar.setLandRClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.e("title bar right");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_fab_toSend:
                SendMsgActivity.toActivity(ChooseMsgActivity.this, mFestivalId, -1);
                break;
        }
    }
}

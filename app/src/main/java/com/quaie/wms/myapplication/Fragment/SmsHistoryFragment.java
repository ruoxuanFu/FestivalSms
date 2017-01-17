package com.quaie.wms.myapplication.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quaie.wms.myapplication.Bean.SendedMsg;
import com.quaie.wms.myapplication.Db.SmsProvider;
import com.quaie.wms.myapplication.R;
import com.quaie.wms.myapplication.Utils.L;
import com.quaie.wms.myapplication.View.FlowLayout;

/**
 * Created by yue on 2017/1/17.
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

public class SmsHistoryFragment extends ListFragment {

    private LayoutInflater mLayoutInflater;
    private CursorAdapter mCursorAdapter;
    private static final int LOADER_ID = 1;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutInflater = LayoutInflater.from(getActivity());

        initLoader();

        setUpListAdapter();
    }

    private void setUpListAdapter() {

        mCursorAdapter = new CursorAdapter(getActivity(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = mLayoutInflater.inflate(R.layout.item_sendedmsg, parent, false);

                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView tvMsg = (TextView) view.findViewById(R.id.id_tv_sendedmsg);
                FlowLayout fl = (FlowLayout) view.findViewById(R.id.id_fl_sendedmsg);
                TextView tvFes = (TextView) view.findViewById(R.id.id_tv_sendedmsg_fes);
                TextView tvDate = (TextView) view.findViewById(R.id.id_tv_sendedmsg_date);

                tvMsg.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_MSG)));
                tvFes.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_FESTIVAL_NAME)));
                tvDate.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_DATE)));

                String names = cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_NAME));
                if (TextUtils.isEmpty(names)) {
                    return;
                }

                fl.removeAllViews();
                for (String name : names.split(":")) {
                    addNameTag(name, fl);
                }

            }
        };

        setListAdapter(mCursorAdapter);

    }

    private void addNameTag(String name, FlowLayout fl) {
        TextView textView = (TextView) mLayoutInflater.inflate(R.layout.item_addcontacts, fl, false);
        textView.setText(name);
        fl.addView(textView);
    }

    private void initLoader() {

        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {

                CursorLoader loader = new CursorLoader(getActivity(), SmsProvider.URI_SMS_ALL, null, null, null, null);

                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if (loader.getId() == LOADER_ID) {
                    mCursorAdapter.swapCursor(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mCursorAdapter.swapCursor(null);
            }
        });
    }
}

package com.quaie.wms.myapplication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.quaie.wms.myapplication.Bean.Festival;
import com.quaie.wms.myapplication.Bean.FestivalLab;
import com.quaie.wms.myapplication.Bean.Msg;
import com.quaie.wms.myapplication.Bean.SendedMsg;
import com.quaie.wms.myapplication.Biz.SmsBiz;
import com.quaie.wms.myapplication.DO.SaveSendInfo;
import com.quaie.wms.myapplication.Utils.L;
import com.quaie.wms.myapplication.View.FlowLayout;
import com.quaie.wms.myapplication.View.TitleBar;
import com.quaie.wms.myapplication.View.YuanXingDaiJinDuProgress;

import java.util.HashSet;

public class SendMsgActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_FESTIVAL_ID = "festivalId";
    private static final String KEY_MSG_ID = "msgID";
    private static final int CODE_REQUEST = 1;
    private static final int SHOW_PROGRESS = 1;

    private int mFestivalId;
    private int mMsgId;

    private Festival mFestival;
    private Msg mMsg;

    private TitleBar mTitleBar;
    private EditText mEtMsg;
    private Button mBtnAdd;
    private FlowLayout mFlContacts;
    private FloatingActionButton mFabSend;
    private YuanXingDaiJinDuProgress mRpProgressbar;
    private FrameLayout mFlLoading;
    private Button mBtnClear;
    private Button mBtnReAdd;

    //加载FlowLayout需要用到的
    private LayoutInflater mInflater;

    //存储查找到的联系人和电话号
    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mContactNums = new HashSet<>();

    //监听发送广告状态的广播
    public static final String ACTION_SEND_MSG = "action_send_msg";
    public static final String ACTION_DELIVER_MSG = "action_deliver_msg";
    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mSendBr;
    private BroadcastReceiver mDeliverBr;

    private SmsBiz mSmsBiz;

    //控制progressbar
    private boolean showbar = true;
    //实际发送的条数
    private int mSendedCount = 0;
    //成功发送的条数
    private int mSuccessSendEdCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        mInflater = LayoutInflater.from(this);

        initView();

        initData();

        initReceiver();
    }

    //接受广播
    private void initReceiver() {
        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        Intent deliverIntent = new Intent(ACTION_DELIVER_MSG);
        mDeliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

        //注册广播
        registerReceiver(mSendBr = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (getResultCode() == RESULT_OK) {
                    mSuccessSendEdCount++;
                    L.Toa(SendMsgActivity.this, "短信发送成功" + mSuccessSendEdCount + "/" + mSendedCount);

                } else {
                    L.Toa(SendMsgActivity.this, "短息发送失败");
                    showbar = false;
                    mFlLoading.setVisibility(View.GONE);
                }

                if (mSuccessSendEdCount == mSendedCount) {
                    finish();
                }
            }
        }, new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeliverBr = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == RESULT_OK) {
                    L.e("联系人成功接受到短信");
                } else {
                    L.e("联系人未成功接受到短信");
                }
            }
        }, new IntentFilter(ACTION_DELIVER_MSG));
    }

    private void initData() {
        mFestivalId = getIntent().getIntExtra(KEY_FESTIVAL_ID, -1);
        mMsgId = getIntent().getIntExtra(KEY_MSG_ID, -1);

        mFestival = new Festival(mFestivalId, FestivalLab.getmInstance().getFestivalById(mFestivalId).getName());

        //当有msgid传入时，给edittext赋值
        if (mMsgId != -1) {
            mMsg = FestivalLab.getmInstance().getMsgByMsgId(mMsgId);
            mEtMsg.setText(mMsg.getContent());
        }
    }


    /**
     * 传递节日id和短信id给此Activity
     *
     * @param context
     * @param festivalId
     * @param msgId
     */
    public static void toActivity(Context context, int festivalId, int msgId) {
        Intent intent = new Intent(context, SendMsgActivity.class);
        intent.putExtra(KEY_FESTIVAL_ID, festivalId);
        intent.putExtra(KEY_MSG_ID, msgId);
        context.startActivity(intent);
    }

    private void initView() {

        mTitleBar = (TitleBar) findViewById(R.id.id_sma_titlebar);
        mEtMsg = (EditText) findViewById(R.id.id_et_content);
        mBtnAdd = (Button) findViewById(R.id.id_bt_addcontacts);
        mFlContacts = (FlowLayout) findViewById(R.id.id_fl_contacts);
        mFabSend = (FloatingActionButton) findViewById(R.id.id_fab_send);
        mRpProgressbar = (YuanXingDaiJinDuProgress) findViewById(R.id.id_Rp_progressbar);
        mFlLoading = (FrameLayout) findViewById(R.id.id_fl_loading);
        mBtnClear = (Button) findViewById(R.id.id_bt_clear);
        mBtnReAdd = (Button) findViewById(R.id.id_bt_readd);

        mBtnClear.setOnClickListener(this);
        mBtnReAdd.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mFabSend.setOnClickListener(this);

        mFlLoading.setVisibility(View.GONE);

        mTitleBar.setCenterText("挥洒祝福");
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
            case R.id.id_bt_addcontacts:
                //读取并添加联系人
                addContacts();
                break;
            case R.id.id_fab_send:
                if (mSmsBiz == null) {
                    mSmsBiz = new SmsBiz();
                }

                if (mContactNums.size() == 0) {
                    L.Toa(this, "请选择联系人");
                    return;
                } else if (TextUtils.isEmpty(mEtMsg.getText().toString().trim())) {
                    L.Toa(this, "请输入祝福短信");
                    return;
                }

                //记录短信发送
                SaveSendInfo saveSendInfo = new SaveSendInfo(getApplicationContext());
                saveSendInfo.Save(creatSendedMsg(mEtMsg.getText().toString().trim()));

                //发送短信，并记录数量
                mSendedCount = mSmsBiz.sendMsg(mContactNums, mEtMsg.getText().toString().trim(), mSendPi, mDeliverPi);
                mSuccessSendEdCount = 0;

                //显示上层页面
                mFlLoading.setVisibility(View.VISIBLE);
                //让progressbar转起来
                showProgressBar();
                break;
            case R.id.id_bt_clear:
                if (mEtMsg.getText().length() > 0) {
                    mEtMsg.setText("");
                } else {
                    L.Toa(getApplicationContext(), "请重新撰写祝福吧。");
                }
                break;
            case R.id.id_bt_readd:

                break;
        }
    }

    private SendedMsg creatSendedMsg(String msg) {

        L.e("11111");

        SendedMsg sendedMsg = new SendedMsg();
        sendedMsg.setMsg(msg);
        sendedMsg.setFestivalName(mFestival.getName());

        String names = "";
        for (String name : mContactNames) {
            names += name + ":";
        }
        sendedMsg.setNames(names.substring(0, names.length() - 1));

        String numbers = "";
        for (String number : mContactNums) {
            numbers += number + ":";
        }
        sendedMsg.setNumbers(numbers.substring(0, numbers.length() - 1));

        return sendedMsg;
    }

    private void showProgressBar() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                int count = 2;

                while (showbar) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mRpProgressbar.incrementProgressBy(count += 0.5);

                    if (mRpProgressbar.getProgress() >= 99) {
                        mRpProgressbar.setProgress(0);
                        count = 2;
                    }
                }
            }
        }).start();
    }

    private void addContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CODE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_REQUEST:
                if (resultCode == RESULT_OK) {
                    //查询数据库
                    Uri contactUri = data.getData();
                    //获得cursor
                    Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                    cursor.moveToFirst();

                    //获得联系人姓名
                    String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    String number = gerContactNum(cursor);

                    if (!TextUtils.isEmpty(number)) {
                        mContactNames.add(contactName);
                        mContactNums.add(number);
                        addTag(contactName);
                    }
                }
                break;
        }
    }

    //创建添加的联系人数据加入到FlowLayout中
    private void addTag(String contactName) {
        TextView view = (TextView) mInflater.inflate(R.layout.item_addcontacts, mFlContacts, false);
        view.setText(contactName);
        L.e(contactName);
        mFlContacts.addView(view);
    }


    private String gerContactNum(Cursor cursor) {

        //拿到电话薄中电话的数量
        int numCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

        String number = null;
        if (numCount > 0) {
            //如果有存在电话记录

            //获得ContactsID
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            //通过ContactsID拿到phoneCursor
            Cursor phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null,
                    null);

            //遍历phone
            phoneCursor.moveToFirst();
            //得到电话
            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            phoneCursor.close();

        }
        cursor.close();
        return number;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendBr);
        unregisterReceiver(mDeliverBr);
    }
}

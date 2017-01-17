package com.quaie.wms.myapplication.Biz;

import android.app.PendingIntent;
import android.content.Context;
import android.telephony.SmsManager;

import com.quaie.wms.myapplication.Utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by yue on 2017/1/13.
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
 * <p>
 * 发送短信
 */

public class SmsBiz {

    private SmsManager smsManager;

    //后面两个参数，是为了监听短信的发送状态，短信是否发送成功，或者对方是否接收到
    public int sendMsg(String phoneNum, String msg, PendingIntent sentPi, PendingIntent deliverPi) {

        //拿到实例
        if (smsManager == null) {
            smsManager = SmsManager.getDefault();
        }
        //发送短信内容
        ArrayList<String> contents = smsManager.divideMessage(msg);
        for (String content : contents) {
            smsManager.sendTextMessage(phoneNum, null, content, sentPi, deliverPi);
        }

        return contents.size();
    }

    //给多个联系人发送
    public int sendMsg(Set<String> phoneNums, String msg, PendingIntent sentPi, PendingIntent deliverPi) {

        //有多少条
        int resalt = 0;

        //遍历出电话号码并且调用sendMsg()方法，逐个发送短信
        for (String phoneNum : phoneNums) {
            L.e(phoneNum);
            int count = sendMsg(phoneNum, msg, sentPi, deliverPi);
            resalt += count;
        }

        return resalt;
    }
}

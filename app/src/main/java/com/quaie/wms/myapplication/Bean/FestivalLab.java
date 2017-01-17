package com.quaie.wms.myapplication.Bean;

import com.quaie.wms.myapplication.Utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by yue on 2017/1/11.
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
 * 假数据
 */

public class FestivalLab {

    public static FestivalLab mInstance;

    private List<Festival> mFestivals = new ArrayList<>();

    private List<Msg> mMsg = new ArrayList<>();

    private FestivalLab() {
        mFestivals.add(new Festival(1, "国庆节"));
        mFestivals.add(new Festival(2, "中秋节"));
        mFestivals.add(new Festival(3, "端午节"));
        mFestivals.add(new Festival(4, "劳动节"));
        mFestivals.add(new Festival(5, "元旦节"));
        mFestivals.add(new Festival(6, "春节"));
        mFestivals.add(new Festival(7, "元宵节"));
        mFestivals.add(new Festival(8, "七夕节"));

        mMsg.add(new Msg(1, 1, "国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节"));
        mMsg.add(new Msg(2, 1, "国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节"));
        mMsg.add(new Msg(3, 1, "国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节"));
        mMsg.add(new Msg(4, 1, "国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节"));
        mMsg.add(new Msg(5, 1, "国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节"));
        mMsg.add(new Msg(6, 1, "国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节"));
        mMsg.add(new Msg(7, 1, "国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节"));
        mMsg.add(new Msg(8, 1, "国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节"));
        mMsg.add(new Msg(9, 1, "国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节国庆节"));

        mMsg.add(new Msg(1, 2, "中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节"));
        mMsg.add(new Msg(2, 2, "中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节"));
        mMsg.add(new Msg(3, 2, "中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节"));
        mMsg.add(new Msg(4, 2, "中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节"));
        mMsg.add(new Msg(5, 2, "中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节"));
        mMsg.add(new Msg(6, 2, "中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节"));
        mMsg.add(new Msg(7, 2, "中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节"));
        mMsg.add(new Msg(8, 2, "中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节"));
        mMsg.add(new Msg(9, 2, "中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节中秋节"));

        mMsg.add(new Msg(1, 3, "端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节"));
        mMsg.add(new Msg(2, 3, "端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节"));
        mMsg.add(new Msg(3, 3, "端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节"));
        mMsg.add(new Msg(4, 3, "端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节"));
        mMsg.add(new Msg(5, 3, "端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节"));
        mMsg.add(new Msg(6, 3, "端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节"));
        mMsg.add(new Msg(7, 3, "端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节"));
        mMsg.add(new Msg(8, 3, "端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节"));
        mMsg.add(new Msg(9, 3, "端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节端午节"));

    }

    public List<Festival> getmFestivals() {
        return new ArrayList<Festival>(mFestivals);
    }

    public Festival getFestivalById(int fstid) {
        for (Festival festival : mFestivals) {
            if (festival.getId() == fstid) {
                return festival;
            }
        }
        return null;
    }

    public List<Msg> getMsgByFestivalId(int fstid) {
        List<Msg> msgs = new ArrayList<>();
        for (Msg msg : mMsg) {

            if (msg.getFestivalId() == fstid) {
                msgs.add(msg);
            }
        }
        return msgs;
    }

    public Msg getMsgByMsgId(int msgid) {
        Msg rmsg;
        for (Msg msg : mMsg) {
            if (msg.getId() == msgid) {
                return rmsg = msg;
            }
        }
        return null;
    }

    public static FestivalLab getmInstance() {
        if (mInstance == null) {
            synchronized (FestivalLab.class) {
                if (mInstance == null) {
                    mInstance = new FestivalLab();
                }
            }
        }
        return mInstance;
    }
}

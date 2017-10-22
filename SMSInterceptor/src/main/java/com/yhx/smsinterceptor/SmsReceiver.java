package com.yhx.smsinterceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import static java.nio.file.Paths.get;

/**
 * Created by Administrator on 2017/10/22.
 */

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //取出短信内容
        Bundle bundle = intent.getExtras();
        //数组中的每一个元素就是一条短信
        Object[] objects = (Object[]) bundle.get("pdus");

        for (Object object : objects) {
            //把数组中的元素转换成短信对象
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
            //获取对方号码
            String address = sms.getOriginatingAddress();
            //获取短信内容
            String body = sms.getMessageBody();
            if ("13888".equals(address)){
                //拦截短信
                abortBroadcast();
            }
        }
    }
}

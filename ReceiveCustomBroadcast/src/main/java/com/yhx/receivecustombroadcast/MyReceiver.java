package com.yhx.receivecustombroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yhx on 2017/10/27.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("接收到自定义广播");
    }
}

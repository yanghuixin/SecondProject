package com.yhx.local;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yhx on 2017/10/27.
 */

public class XianZF extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String order = getResultData();
        System.out.println("县政府收到文件：" + order);
}
}

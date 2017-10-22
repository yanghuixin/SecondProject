package com.yhx.ipradio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/10/22.
 */

public class CalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String number = getResultData();
        String newNumber = "17591" + number;
        setResultData(newNumber);
    }
}

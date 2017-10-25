package com.yhx.sdlistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/10/25.
 */

public class SDReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //判断收到的是什么广播
        String action = intent.getAction();
        switch (action){
            case Intent.ACTION_MEDIA_MOUNTED:
                Toast.makeText(context,"SD卡就绪",Toast.LENGTH_LONG).show();
                break;
            case Intent.ACTION_MEDIA_REMOVED:
                Toast.makeText(context,"SD卡被拔出了",Toast.LENGTH_LONG).show();
                break;
            case Intent.ACTION_MEDIA_UNMOUNTED:
                Toast.makeText(context,"SD卡被卸载了",Toast.LENGTH_LONG).show();
                break;
        }
    }
}

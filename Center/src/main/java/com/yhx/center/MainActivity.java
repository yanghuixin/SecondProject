package com.yhx.center;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        Intent intent = new Intent();
        intent.setAction("com.center.fdm");
        //发送自定义有序广播
        //resultReceiver:所有广播接受者收到广播之后，最后收到广播，并且一定能收到广播
        sendOrderedBroadcast(intent,null,new MyReceiver(),null,0,"每人发100斤大米",null);
    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String order = getResultData();
            System.out.println("反贪局收到文件：" + order);
        }
    }
}

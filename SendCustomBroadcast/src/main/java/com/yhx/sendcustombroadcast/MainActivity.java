package com.yhx.sendcustombroadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        //发送自定义广播
        Intent intent = new Intent();
        intent.setAction("a.b.c");
        sendBroadcast(intent);
    }
}

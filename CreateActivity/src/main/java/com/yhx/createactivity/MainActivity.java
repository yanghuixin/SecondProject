package com.yhx.createactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:110"));
        startActivity(intent);
    }

    public void cleck2(View view) {
        Intent intent = new Intent();
        intent.setClass(this,SecondActivity.class);
        startActivity(intent);
    }

    public void cleck3(View view) {
        Intent intent = new Intent();
        //arg0：目标Activity所在的应用包名
        //arg1：目标Activity的类名
        intent.setClassName("com.android.dialer","com.android.dialer.DialtactsActivity");
        startActivity(intent);
    }

    public void cleck4(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        startActivity(intent);
    }

    public void cleck5(View view) {
        Intent intent = new Intent();
        intent.setAction("com.yada.yhx");
        intent.setData(Uri.parse("yhx:ohohoh"));
        //如果没有设置Category，系统自动添加默认Category
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }
}

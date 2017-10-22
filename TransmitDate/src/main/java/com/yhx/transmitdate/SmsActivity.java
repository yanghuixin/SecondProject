package com.yhx.transmitdate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        final String[] smss = new String[]{
                "我正在开会，一会回你",
                "我正在吃饭，一会回你",
                "我已死，有事烧纸"
        };
        //显示联系人列表
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(new ArrayAdapter<String>(this,R.layout.item_listview,R.id.tv,smss));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //position:点击某个条目时，会通过position告诉程序员点击的是哪一个条目
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                //把要传递的数据封装至intent中
                intent.putExtra("sms",smss[position]);
                //当Activity销毁时，返回至上一个Activity，会把intent对象传递给上一个Activity
                setResult(1,intent);
                //销毁当前Activity
                finish();
            }
        });
    }
}

package com.yhx.transmitdate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        final String[] names = new String[]{
                "春晓",
                "苗润",
                "爱的结晶"
        };
        //显示联系人列表
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(new ArrayAdapter<String>(this,R.layout.item_listview,R.id.tv,names));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //position:点击某个条目时，会通过position告诉程序员点击的是哪一个条目
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                //把要传递的数据封装至intent中
                intent.putExtra("name",names[position]);
                //当Activity销毁时，返回至上一个Activity，会把intent对象传递给上一个Activity
                setResult(0,intent);
                //销毁当前Activity
                finish();
            }
        });
    }
}

package com.yhx.transmitdate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final int NAME = 0;
    public static final int CONTENT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chooseContent(View view) {
        Intent intent = new Intent(this,ContentActivity.class);
        startActivityForResult(intent,NAME);
    }

    public void chooseSms(View view) {
        Intent intent = new Intent(this,SmsActivity.class);
        startActivityForResult(intent,CONTENT);
    }

    //Activity销毁时返回intent对象，那么就会回调这个方法，方法中传入的intent封装了data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case NAME:
                String name =data.getStringExtra("name");
                EditText et_name = (EditText) findViewById(R.id.et_name);
                et_name.setText(name);
                return;
            case CONTENT:
                String sms =data.getStringExtra("sms");
                EditText et_content = (EditText) findViewById(R.id.et_content);
                et_content.setText(sms);
                return;
        }
    }
}

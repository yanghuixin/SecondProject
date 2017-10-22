package com.yhx.transmitdate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final int NAME = 0;
    public static final int CONTENT = 1;
    public static final int HANDLE_EXCEPTION = 2;
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

    public void Admit(View view) {
        Intent intent = new Intent(this,HandleExceptionActivity.class);
        startActivityForResult(intent,HANDLE_EXCEPTION);
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
            case HANDLE_EXCEPTION:
                if (resultCode == 1){
                    String name2 =data.getStringExtra("name");
                    EditText et_name2 = (EditText) findViewById(R.id.et_name);
                    et_name2.setText(name2);
                }else if (resultCode == 2){
                    String sms2 =data.getStringExtra("sms");
                    EditText et_content2 = (EditText) findViewById(R.id.et_content);
                    et_content2.setText(sms2);
                }
                return;
        }
    }
}

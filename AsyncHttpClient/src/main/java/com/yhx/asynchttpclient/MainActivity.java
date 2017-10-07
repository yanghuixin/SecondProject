package com.yhx.asynchttpclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void get_click(View view) {
        //获取用户输入的账号密码
        EditText et_name = (EditText) findViewById(R.id.et_name);
        EditText et_pass = (EditText) findViewById(R.id.et_pass);

        String name = et_name.getText().toString();
        final String pass = et_pass.getText().toString();

        final String path = "http://192.168.2.127:8080/Web/servlet/Login";

        //使用异步HttpClient发送get请求
        AsyncHttpClient client = new AsyncHttpClient();
        //把要提交的数据封装到rp对象中
        RequestParams rp = new RequestParams();
        rp.add("name",name);
        rp.add("pass",pass);
        //发送get请求
        client.get(path,rp,new MyHandler());
    }

    public void post_click(View view) {
        //获取用户输入的账号密码
        EditText et_name = (EditText) findViewById(R.id.et_name);
        EditText et_pass = (EditText) findViewById(R.id.et_pass);

        String name = et_name.getText().toString();
        String pass = et_pass.getText().toString();

        String path = "http://192.168.2.127:8080/Web/servlet/Login";
        //使用异步HttpClient发送post请求
        AsyncHttpClient client = new AsyncHttpClient();
        //把要提交的数据封装到rp对象中
        RequestParams rp = new RequestParams();
        rp.add("name",name);
        rp.add("pass",pass);
        //发送post请求
        client.post(path,rp,new MyHandler());
    }

    class MyHandler extends AsyncHttpResponseHandler{

        //请求成功时调用
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Toast.makeText(MainActivity.this,new String(responseBody),Toast.LENGTH_LONG).show();
        }

        //请求失败时调用
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(MainActivity.this,new String(responseBody),Toast.LENGTH_LONG).show();
        }
    }
}

package com.yada.secondproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yada.secondproject.Tools.Tools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(MainActivity.this,(String)msg.obj,Toast.LENGTH_LONG).show();
        }
    };

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

        final String path = "http://192.168.2.127:8080/Web/servlet/Login?name=" + URLEncoder.encode(name) + "&pass=" +pass;

        Thread t = new Thread(){
            @Override
            public void run() {
                //使用httpclient框架提交
                //1、创建client对象
                HttpClient client = new DefaultHttpClient();
                //2、创建get请求对象
                HttpGet get = new HttpGet(path);
                try {
                    //3、使用client发送get请求
                    HttpResponse response = client.execute(get);
                    //获取状态行
                    StatusLine line = response.getStatusLine();
                    //获取状态码
                    int code = line.getStatusCode();
                    if ( code == 200){
                        //获取实体
                        HttpEntity entity = response.getEntity();
                        InputStream is = entity.getContent();
                        String text = Tools.getTextFromStream(is);

                        Message msg = handler.obtainMessage();
                        msg.obj = text;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    public void post_click(View view) {
        //获取用户输入的账号密码
        EditText et_name = (EditText) findViewById(R.id.et_name);
        EditText et_pass = (EditText) findViewById(R.id.et_pass);

        final String name = et_name.getText().toString();
        final String pass = et_pass.getText().toString();

        final String path = "http://192.168.2.127:8080/Web/servlet/Login";

        Thread t = new Thread(){
            @Override
            public void run() {
                //1、创建client对象
                HttpClient client = new DefaultHttpClient();
                //2、创建post请求对象
                HttpPost post = new HttpPost(path);
                //把要提交的数据封装到post中
                //创建实体对象
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                BasicNameValuePair bnvp1 = new BasicNameValuePair("name",name);
                BasicNameValuePair bnvp2 = new BasicNameValuePair("pass",pass);
                parameters.add(bnvp1);
                parameters.add(bnvp2);
                UrlEncodedFormEntity entity = null;
                try {
                    //把集合对象封装到实体中
                    entity = new UrlEncodedFormEntity(parameters,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //把实体对象封装至POST对象中
                post.setEntity(entity);
                try {
                    HttpResponse response = client.execute(post);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        InputStream is = response.getEntity().getContent();
                        String text = Tools.getTextFromStream(is);
                        Message msg = handler.obtainMessage();
                        msg.obj = text;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}

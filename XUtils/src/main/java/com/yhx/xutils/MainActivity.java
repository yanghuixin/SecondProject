package com.yhx.xutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pb;
    private TextView tv_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.pb);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
    }

    public void click(View view) {
        String url = "http://192.168.2.127:8080/Firefox64_56.0.0.6478_bd.exe";
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(url,//目标文件的网址
                "sdcard/Firefox64_56.0.0.6478_bd.exe",//指定存储的路径及文件名
                true,//是否支持断点续传
                true,//如果在响应头包含文件名，下载完成后自动重命名
                new RequestCallBack<File>(){

                    //下载完成后调用
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        TextView tv_success = (TextView) findViewById(R.id.tv_success);
                        tv_success.setText(responseInfo.result.getPath());
                    }

                    //下载失败后调用
                    @Override
                    public void onFailure(HttpException e, String s) {
                        TextView tv_failure = (TextView) findViewById(R.id.tv_failure);
                        tv_failure.setText(s);
                    }

                    //下载过程中不断调用
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        pb.setMax((int)total);
                        pb.setProgress((int)current);
                        tv_progress.setText(current * 100 / total + "%");
                    }
                });
    }
}

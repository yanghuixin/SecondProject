package com.yhx.multithreaddownload;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String path = "http://192.168.2.127:8080/Firefox64_56.0.0.6478_bd.exe";
    int threadCount = 3;
    int finishedThread = 0;
    //所有线程下载总进度
    int downloadProgress = 0;
    private ProgressBar pb;
    private TextView tv;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv.setText((long)pb.getProgress() * 100 / pb.getMax() + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.pb);
        tv = (TextView) findViewById(R.id.tv);
    }

    public void click(View view) {
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    //发送Http请求，拿到目标文件长度
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);

                    if(conn.getResponseCode() == 200){
                        //获取长度
                        int length = conn.getContentLength();

                        //创建临时文件
                        File file = new File(Environment.getExternalStorageDirectory(),getNameFromPath(path));
                        RandomAccessFile raf = new RandomAccessFile(file, "rwd");

                        //设置临时文件大小与目标文件一致
                        raf.setLength(length);
                        raf.close();

                        //设置进度条的最大值
                        pb.setMax(length);

                        //设置每个线程下载区间
                        int size = length / threadCount ;
                        for (int i = 0; i < threadCount ; i++) {
                            int startIndex = i * size;
                            int endIndex = (i + 1) * size -1;
                            if (i == threadCount - 1) {
                                endIndex = length - 1;
                            }
                            System.out.println("线程：" + i + "下载的区间：" + startIndex + "~" + endIndex);
                            new DownLoadThread(i, startIndex, endIndex).start();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    public String getNameFromPath(String path){
        int index = path.lastIndexOf("/");
        return path.substring(index + 1);

    }

    class DownLoadThread extends Thread {
        private int threadId;
        private int startIndex;
        private int endIndex;

        public DownLoadThread(int threadId, int startIndex, int endIndex) {
            super();
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            //发送http请求，请求要下载的数据
            try {
                //创建一个文本临时文件，保存下载进度
                File fileProgress = new File(Environment.getExternalStorageDirectory(),threadId + ".txt");
                int lastProgress = 0 ;
                if (fileProgress.exists()) {
                    //读取进度临时文件中的内容
                    FileInputStream fis = new FileInputStream(fileProgress);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                    //得到上一次下载进度
                    lastProgress = Integer.parseInt(br.readLine());
                    //改变下载的开始位置，上次下载过的，就不在下载了
                    startIndex += lastProgress;
                    fis.close();
                    //把上一次的下载进度加到进度条中
                    downloadProgress += lastProgress;
                    pb.setProgress(downloadProgress);

                    //发送消息，让文本进度条改变
                    handler.sendEmptyMessage(1);
                }
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(8000);
                //设置请求数据的区间
                conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                //请求部分数据，成功的响应码是206
                if (conn.getResponseCode() == 206 ) {
                    InputStream is = conn.getInputStream();

                    byte[] b = new byte[1024];
                    int len = 0 ;
                    //当前线程下载的总进度
                    int total = lastProgress ;
                    File file = new File(Environment.getExternalStorageDirectory(),getNameFromPath(path));
                    RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                    //设置写入的开始位置
                    raf.seek(startIndex);
                    while ((len = is.read(b)) != -1) {
                        raf.write(b,0,len);
                        total += len;
                        System.out.println("线程" + threadId + "下载了：" + total);

                        RandomAccessFile rafProgress = new RandomAccessFile(fileProgress, "rwd");
                        //每次下载1024个字节，就马上把1024写入进度临时文件
                        rafProgress.write((total + "").getBytes());
                        rafProgress.close();
                        //每次下载len个长度的字节，马上把len加到下载进度中，让进度条能反映这len个长度的下载进度
                        downloadProgress += len;
                        pb.setProgress(downloadProgress);

                        //发送消息，让文本进度条改变
                        handler.sendEmptyMessage(1);
                    }
                    raf.close();
                    System.out.println("线程" + threadId + "下载完毕-------------");
                    //判断三条线程全部下载完毕，才去删除进度临时文件
                    finishedThread++;
                    synchronized (path) {
                        if (finishedThread == threadCount) {
                            for (int i = 0; i < threadCount; i++) {
                                File f = new File(Environment.getExternalStorageDirectory(),i + ".txt");
                                f.delete();
                            }
                            finishedThread = 0;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

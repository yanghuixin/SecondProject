package com.yhx.transmitdate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HandleExceptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_exception);

        final TextView tv_name = (TextView) findViewById(R.id.tv_name);
        final TextView tv_content = (TextView) findViewById(R.id.tv_content);

        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name",tv_name.getText());
                setResult(1,intent);
                finish();
            }
        });

        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("sms",tv_content.getText());
                setResult(2,intent);
                finish();
            }
        });
    }
}

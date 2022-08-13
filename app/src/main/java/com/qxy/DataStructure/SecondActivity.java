package com.qxy.DataStructure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView textView; // 测试token，到时候删除
    Button button;
    String contentType = "application/json";
    int type;  // 榜单类型 1 电影 ，2 电视剧 ， 3 综艺
    int version; // 榜单版本：空值默认为本周榜单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.test_textView);

        textView.setText("视频");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent2);
            }
        });


    }
}
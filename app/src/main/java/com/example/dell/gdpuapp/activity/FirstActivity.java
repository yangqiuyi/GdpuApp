package com.example.dell.gdpuapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dell.gdpuapp.R;

/**
 * Created by dell on 2016/5/7.
 */
public class FirstActivity extends Activity {



    TextView mTextView1;
    TextView mTextView2;
    TextView mTextView3;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        context = this;


        mTextView1=(TextView)findViewById(R.id.tv1);
        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent();
                intent1.setClass(context, NewsMainActivity.class);//方法2
                startActivity(intent1);
            }
        });






        mTextView2=(TextView)findViewById(R.id.tv2);
        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent();
                intent2.setClass(context, MediaMainActivity.class);//方法2
                startActivity(intent2);
            }
        });


        mTextView3=(TextView)findViewById(R.id.tv3);
        mTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent();
                intent3.setClass(context, ScholarshipMainActivity.class);//方法2
                startActivity(intent3);
            }
        });





    }


}
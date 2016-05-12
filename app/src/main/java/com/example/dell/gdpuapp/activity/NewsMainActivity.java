package com.example.dell.gdpuapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.gdpuapp.Environment;
import com.example.dell.gdpuapp.GdpuApplication;
import com.example.dell.gdpuapp.R;
import com.example.dell.gdpuapp.adapter.NewsPlateAdapter;
import com.example.dell.gdpuapp.adapter.PlateAdapter;
import com.example.dell.gdpuapp.modle.ItemModel;
import com.example.dell.gdpuapp.modle.ItemNewsModel;
import com.example.dell.gdpuapp.modle.NewsPlate;
import com.example.dell.gdpuapp.modle.NormalPlate;
import com.example.dell.gdpuapp.net.NewsGetter;
import com.example.dell.gdpuapp.util.ToastUtil;

/**
 * Created by dell on 2016/5/7.
 */
public class NewsMainActivity extends AppCompatActivity {

    private ListView mListView;
    private NewsGetter mNewsGetter;
    private Context mContext;
    private static final int MSG_DATA = 0;
    private static final int MSG_DATA_FAIL = 1;
    private NewsPlate news;
    private static final String TAG = "NewsMainActivity";
    private MainHandler mMainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //ui
        setContentView(R.layout.activity_news);//
        mListView = (ListView) findViewById(R.id.news_lisview);

        //data
        mNewsGetter = new NewsGetter();
        mContext = this;


        //主线程
        mMainHandler = new MainHandler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long begin = System.currentTimeMillis();
                    news =  mNewsGetter.getNews(Environment.GDPU);
                    if( news == null
                            || news.getNewsPlate().getData() == null || news.getNewsPlate().getData().size() == 0
                            || news.getItemNewsModels() == null || news.getItemNewsModels().size() == 0){
                        Log.e(TAG, " news data is null ");
                        mMainHandler.sendEmptyMessage(MSG_DATA_FAIL);
                        return;
                    }

                    Log.i(TAG, "spare time = " + (System.currentTimeMillis() - begin));
                    mMainHandler.sendEmptyMessage(MSG_DATA);//通知  如果不为空，就发信息通知
                } catch (Exception e) {
                    Log.e(TAG, "Exception =" + e.toString());
                }
            }
        }).start();


    }

    private NewsPlateAdapter newsPlateAdapter;

    private class MainHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;  // 收消息

            if(what == MSG_DATA){
                newsPlateAdapter = new NewsPlateAdapter(news, mContext);
                mListView.setAdapter(newsPlateAdapter); // 广药新闻数据到了
                //
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        NewsPlate newsPlate = newsPlateAdapter.getData();

                        Intent intent = new Intent(NewsMainActivity.this, WebViewActivity.class);

                        if(position < newsPlateAdapter.getData().getItemNewsModels().size()){
                            ItemNewsModel itemNewsModel = newsPlate.getItemNewsModels().get(position);
                            Log.d(TAG, "itemNewsModel.getUrl() = " + itemNewsModel.getUrl());

                            intent.putExtra(WebViewActivity.KEY , itemNewsModel.getUrl());
                            startActivity(intent);
                            return;
                        }else {
                            ItemModel itemModel = newsPlate.getNewsPlate().getData().get(position - (newsPlate.getItemNewsModels().size()));
                            Log.d(TAG, "itemModel.getUrl() = " + itemModel.getUrl());
                            intent.putExtra(WebViewActivity.KEY , itemModel.getUrl());
                            startActivity(intent);
                            return;
                        }

                    }
                });

            }else if(what == MSG_DATA_FAIL){
                ToastUtil.toastShort(GdpuApplication.inst().getResources().getString(R.string.warning_net));
            }
        }
    }
    }

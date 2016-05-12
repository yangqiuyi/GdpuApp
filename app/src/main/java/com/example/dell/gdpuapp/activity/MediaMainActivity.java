package com.example.dell.gdpuapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.dell.gdpuapp.Environment;
import com.example.dell.gdpuapp.GdpuApplication;
import com.example.dell.gdpuapp.R;
import com.example.dell.gdpuapp.adapter.PlateAdapter;
import com.example.dell.gdpuapp.modle.NormalPlate;
import com.example.dell.gdpuapp.net.MediaGetter;
import com.example.dell.gdpuapp.util.ToastUtil;

/**
 * Created by dell on 2016/5/7.
 */
public class MediaMainActivity extends AppCompatActivity  {

    private ListView mListView;
    private NormalPlate mMediaGDPU;
    private MediaGetter mMediaGetter;
    private Context mContext;
    private MainHandler mMainHandler;
    private static final int MSG_DATA = 0;
    private static final String TAG = "MediaMainActivity";
    private static final int MSG_DATA_FAIL = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ui
        setContentView(R.layout.activity_media);//
        mListView = (ListView) findViewById(R.id.media_lisview);



        //data
        mMediaGetter = new  MediaGetter();//  private MediaGetter mMediaGetter;
        mContext = this;

        //主线程
        mMainHandler = new MainHandler();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mMediaGDPU = mMediaGetter.getMedia(Environment.GDPU);// private NormalPlate mMediaGDPU;

                    if(mMediaGDPU == null || mMediaGDPU.getData() == null || mMediaGDPU.getUrlMore() == null){
                        Log.e(TAG, "mMediaGDPU is null ");
                        mMainHandler.sendEmptyMessage(MSG_DATA_FAIL);
                        return;
                    }


                    mMainHandler.sendEmptyMessage(MSG_DATA);



                } catch (Exception e) {
                    Log.e(TAG, "Exception =" + e.toString());
                }
            }
        }).start();


    }




    private class MainHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            //  super.handleMessage(msg);
            //todo 收消息
            int what = msg.what;
            if(what == MSG_DATA){

                //todo 媒体广药数据到了
                mListView.setAdapter(new PlateAdapter(mMediaGDPU, mContext));//将Adapter应用到继承自AdapterView类上，你需要调用View的setAdapter方法，传入一个Adapter实例
                                                                                 //将mMediaGDPU绑定到ListView中用于显示每个项目的简单TextView控件上。
            }else if ((what == MSG_DATA_FAIL)) {
                ToastUtil.toastShort(GdpuApplication.inst().getResources().getString(R.string.warning_net));
            }
        }
    }





}
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
import com.example.dell.gdpuapp.net.ScholarshipGetter;
import com.example.dell.gdpuapp.util.ToastUtil;

public class ScholarshipMainActivity extends AppCompatActivity {

    private ListView mListView;
    private MainHandler mMainHandler;
    private static final String TAG = "ScholarshipMainActivity";
    private ScholarshipGetter mScholarshipGetter;
    private NormalPlate mMediaGDPU;
    private MediaGetter mMediaGetter;
    private NormalPlate mScholarship;
    private static final int MSG_DATA_FAIL = 1;
    private static final int MSG_DATA = 0;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ui
        setContentView(R.layout.activity_main);//
        mListView = (ListView) findViewById(R.id.scholar_lisview);

        //data
        mScholarshipGetter = new ScholarshipGetter();
        mContext = this;

        //主线程
        mMainHandler = new MainHandler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long begin = System.currentTimeMillis();
                    mScholarship = mScholarshipGetter.getScholarship(Environment.GDPU);
                  // mMediaGDPU = mMediaGetter.getMedia(Environment.GDPU);
                    if(mScholarship == null||mScholarship.getData()==null||mScholarship.getUrlMore()==null){
                        Log.e(TAG, "mScholarship is null ");
                        mMainHandler.sendEmptyMessage(MSG_DATA_FAIL);
                        return;
                    }

                    Log.i(TAG, "spare time = " + (System.currentTimeMillis() - begin));

                    //通知  如果不为空，就发信息通知
                    mMainHandler.sendEmptyMessage(MSG_DATA);

                   // mMediaGDPU = mMediaGetter.getMedia(Environment.GDPU);

                } catch (Exception e) {
                    Log.e(TAG, "Exception =" + e.toString());
                }
            }
        }).start();



    }

    private class MainHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
          //  super.handleMessage(msg);
            //todo 收消息
            int what = msg.what;
            if(what == MSG_DATA){
                //todo 学术活动数据到了
                mListView.setAdapter(new PlateAdapter(mScholarship, mContext));
                }else if ((what == MSG_DATA_FAIL)) {
                ToastUtil.toastShort(GdpuApplication.inst().getResources().getString(R.string.warning_net));
            }
        }
    }

}










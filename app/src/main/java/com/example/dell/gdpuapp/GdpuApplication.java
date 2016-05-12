package com.example.dell.gdpuapp;

import android.app.Application;
import android.content.Context;

public class GdpuApplication extends Application{

    public static GdpuApplication mGdpuApplication;

    public static GdpuApplication inst(){
        return mGdpuApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGdpuApplication = this;
    }

}

package com.example.dell.gdpuapp.util;

import android.app.Application;
import android.widget.Toast;

import com.example.dell.gdpuapp.GdpuApplication;


public class ToastUtil {

    public static void toastLong(String str){
        Toast.makeText(GdpuApplication.inst(),str,Toast.LENGTH_LONG).show();

    }

    public static void toastShort(String str){
        Toast.makeText(GdpuApplication.inst(),str,Toast.LENGTH_SHORT).show();

    }




}

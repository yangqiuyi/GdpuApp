package com.example.dell.gdpuapp.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by davi on 16-5-12.
 */
public class NetUtil {

    private static byte[] getUrlBytes(String urlSpec) throws IOException {

        URL url = new URL(urlSpec);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 这里强制转换，是因为下面要用到HttpURLConnection.getInputStream
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.i("NetUtil", "连接不成功");
                return null;
            }

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.close();
            return out.toByteArray();

        }finally {
            conn.disconnect();
        }

    }

    /**
     * 下载URL指定的资源(即将getUrlBytes方法的返回值byte[]转换成String类型)
     *
     * @return 返回类型为String
     */
    public static String getUrl(String urlSpec) {
        String result = null;
        try {
            result = new String(getUrlBytes(urlSpec));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // String htmlString = getUrl(urlSpec);


}

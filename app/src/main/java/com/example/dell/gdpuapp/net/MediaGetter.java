package com.example.dell.gdpuapp.net;

import android.util.Log;

import com.example.dell.gdpuapp.Environment;
import com.example.dell.gdpuapp.modle.ItemModel;
import com.example.dell.gdpuapp.modle.NormalPlate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by dell on 2016/5/4.
 */


//负责网络分析
public class MediaGetter {

    private static String TAG = "MediaGetter";



    public NormalPlate getMedia (String url) {  //返回一个mediaGDPU对象

        NormalPlate mediaGDPU = new NormalPlate();

        try {
            Document doc = Jsoup.connect(url).timeout(Environment.TIME_OUT).get();//把html文件下载下来，转换为Document
            if (doc == null) {
                Log.e(TAG, "doc == null, net is fail!");
                return null;

            }

            Elements elementsParents = doc.select("div.new_left_bot_left*");// <div class=""new_left_bot_left left"">  集合  select返回一个数组
            if (elementsParents == null || elementsParents.size() == 0) {         //  Element 节点   Elements类似数组
                Log.e(TAG, "doc.select(new_left_bot_left left 媒体广药 is fail !!!!!");
                return null;
            }


            Element masthead = elementsParents.first();//<div class=""new_left_bot_left leftt">媒体广药本身
            Elements articleElements = masthead.children();//0 1  </div>--更多的链接      <ul>包含6个item


            //0
            Element new_left_bot_right_navHref = articleElements.select("a").first();//a的first()就是自己本身
            String urlSon = new_left_bot_right_navHref.attr("href");//找到href属性  即链接
            mediaGDPU.setUrlMore(urlSon);//save到mediaGDPU大对象中，到时候直接操作mediaGDPU对象


            //1
            ArrayList<ItemModel> items = new ArrayList<ItemModel>();
            Elements elementsLIs = articleElements.select("li");
            int size = elementsLIs.size();

            for(int i = 0; i < size; i++){
                //标题 链接
                Element elementLIContent=  elementsLIs.get(i).select("a").first();//<a </a>这一段本身
                String hrefLI = elementLIContent.attr("href");   //属性href 即 点击了跳到另一个画面的链接
                String titleLI = elementLIContent.attr("title");//属性title 即标题
                Log.i(TAG, "hrefLI = " + hrefLI + "   " + "titleLI = " + titleLI);
                //日期
                Element dateElement =  elementsLIs.get(i).select("span").first();
                String date = dateElement.ownText();
                Log.i(TAG,"date = " + date);
                //save
                ItemModel mediaModel =  new ItemModel();
                mediaModel.setDate(date);     //  把日期set到mediaModel
                mediaModel.setTitle(titleLI); //  把标题set到mediaModel
                mediaModel.setUrl(hrefLI);   //   把链接set到mediaModel
              items.add(i,mediaModel);    // ArrayList< ItemModel> items = new ArrayList< ItemModel>();
            }
           mediaGDPU.setData(items);        //把item放到scholarship对象，到时候直接操作scholarship

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediaGDPU;
    }
}
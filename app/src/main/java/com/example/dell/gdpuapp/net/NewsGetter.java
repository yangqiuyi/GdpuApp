package com.example.dell.gdpuapp.net;

import android.util.Log;

import com.example.dell.gdpuapp.Environment;
import com.example.dell.gdpuapp.modle.ItemModel;
import com.example.dell.gdpuapp.modle.ItemNewsModel;
import com.example.dell.gdpuapp.modle.NewsPlate;
import com.example.dell.gdpuapp.modle.NormalPlate;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class NewsGetter {

    private static String TAG = "NewsGetter";

    public NewsPlate getNews(String url) {  //返回一个news对象

        ArrayList<ItemNewsModel> itemNewsModels = new ArrayList<>();
        NormalPlate news = new NormalPlate();

        try {

            Document doc = Jsoup.connect(url).timeout(Environment.TIME_OUT).get();//把html文件下载下来，转换为Document
            if (doc == null){
                Log.e(TAG, "doc == null, net is fail!");
                return null;
            }

            Elements elementsParents = doc.select("div.new_left_top_nav*");//div class="new_left_top_nav"  集合  select返回一个数组
            if(elementsParents == null || elementsParents.size() == 0){         //  Element 节点   Elements类似数组
                Log.e(TAG, "doc.select(div.new_left_bot_right* 广药新闻 is fail !!!!!");
                return null;
            }
            //url
            Element masthead = elementsParents.first();
            Elements articleElements =  masthead.children();
            Element new_left_bot_right_navHref = articleElements.select("a").first();//a的first()就是自己本身
            String urlSon =  new_left_bot_right_navHref.attr("href");//找到href属性  即链接
            news.setUrlMore(urlSon);//save到scholarship大对象中，到时候直接操作scholarship对象

            //title desc
            Elements third_elementsParents = doc.select("div.new_left_top_left*");//找到div.new_left_top_left放进集合里面，只有一个元素
            Element third_masthead = third_elementsParents.first();//div.new_left_top_left自己本身（第一个元素）
            Elements third_articleElements =  third_masthead.children();
            Elements third_elementsLIs = third_articleElements.select("li");
            int third_size = third_elementsLIs.size();
            for(int i = 0; i < third_size; i++){
                Element element = third_elementsLIs.get(i);
                Element elementLIContent= element.select("a").first();//<a </a>这一段本身
                String hrefLI = elementLIContent.attr("href");   //属性href 即 点击了跳到另一个画面的链接
                String titleLI = elementLIContent.attr("title");//属性title 即标题
                Element elementP = element.select("p").first();
                String strP = elementP.ownText();
               //save
                itemNewsModels.add(new ItemNewsModel(titleLI,strP,hrefLI));
            }
            //7 item
            Elements second_elementsParents = doc.select("div.new_left_right*");// <div class="new_left_bot_right right">  集合  select返回一个数组
            Element second_masthead = second_elementsParents.first();//<div class="new_left_bot_right right">学术活动本身
            Elements second_articleElements =  second_masthead.children();//    <ul>包含个item

            ArrayList<ItemModel> items = new ArrayList<ItemModel>();
            Elements second_elementsLIs = second_articleElements.select("li");
            int size = second_elementsLIs.size();//size = 7  即item有7个
            for(int i = 0; i < size; i++){
                //标题 链接
                Element elementLIContent=  second_elementsLIs.get(i).select("a").first();//<a </a>这一段本身
                String hrefLI = elementLIContent.attr("href");   //属性href 即 点击了跳到另一个画面的链接
                String titleLI = elementLIContent.attr("title");//属性title 即标题
                Log.i(TAG, "hrefLI = " + hrefLI + "   " + "titleLI = " + titleLI);
                //日期
                Element dateElement =  second_elementsLIs.get(i).select("span").first();
                String date = dateElement.ownText();
                Log.i(TAG,"date = " + date);
                //save
                ItemModel newsModel =  new ItemModel();
                newsModel.setDate(date);     //  把日期set到scholarshipModel
                newsModel.setTitle(titleLI); //  把标题set到scholarshipModel
                newsModel.setUrl(hrefLI);   //   把链接set到scholarshipModel
                items.add(i,newsModel);    // ArrayList<ScholarshipModel> items = new ArrayList<ScholarshipModel>();
            }
            news.setData(items);        //把item放到scholarship对象，到时候直接操作scholarship
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2合一
        NewsPlate newsPlate = new NewsPlate(news, itemNewsModels);

        return  newsPlate;
    }


}
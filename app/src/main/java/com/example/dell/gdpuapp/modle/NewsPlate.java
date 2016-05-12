package com.example.dell.gdpuapp.modle;

import java.util.ArrayList;

/**
 * Created by dell on 2016/5/7.    news模块包括普通的item模块和特殊的news模块
 */
public class NewsPlate {

    private NormalPlate newsPlate;                       //包括   更多的链接和item
    private ArrayList<ItemNewsModel> itemNewsModels;    //包括标题和描述

    public NewsPlate(NormalPlate newsPlate, ArrayList<ItemNewsModel> itemNewsModels) {
        this.newsPlate = newsPlate;
        this.itemNewsModels = itemNewsModels;
    }

    public NormalPlate getNewsPlate() {
        return newsPlate;
    }

    public ArrayList<ItemNewsModel> getItemNewsModels() {
        return itemNewsModels;
    }
}

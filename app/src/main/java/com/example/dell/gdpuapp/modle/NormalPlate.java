package com.example.dell.gdpuapp.modle;

import java.util.ArrayList;

/**
 * 板块 包括链接和item
 */
public class NormalPlate {
    private String urlMore;              //更多的链接
    private ArrayList<ItemModel> data;   //放item


    public void setUrlMore(String urlMore) {  this.urlMore = urlMore;
    }

    public String getUrlMore() {
        return urlMore;
    }

    public ArrayList<ItemModel> getData() {return data;}

    public void setData(ArrayList<ItemModel> data) {
        this.data = data;
    }
}

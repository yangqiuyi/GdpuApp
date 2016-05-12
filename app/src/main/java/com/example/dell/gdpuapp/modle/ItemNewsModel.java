package com.example.dell.gdpuapp.modle;

/*包括标题和描述*/
public class ItemNewsModel {

    private String title;
    private String desc;
    private String url;

    public ItemNewsModel(String title, String desc, String url) {
        this.title = title;
        this.desc = desc;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }
}

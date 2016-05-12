package com.example.dell.gdpuapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dell.gdpuapp.R;
import com.example.dell.gdpuapp.modle.ItemNewsModel;
import com.example.dell.gdpuapp.modle.NewsPlate;
import com.example.dell.gdpuapp.viewholder.OneTextViewHolder;

/**
 * Created by dell on 2016/5/8.
 */
public class NewsPlateAdapter extends BaseAdapter {

    private NewsPlate mData;
    private int mDataSize;
    private LayoutInflater mLayoutInflater;

    private static final int TYPE_HEAD = 0;//前面2个
    private static final int TYPE_NORMAL = 1;//普通item，7个的那种


    public NewsPlateAdapter(NewsPlate data, Context context) {
        this.mData = data;
        mDataSize = data.getItemNewsModels().size() + data.getNewsPlate().getData().size();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public NewsPlate getData(){
        return mData;
    }

    @Override
    public int getCount() {
        return mDataSize;
    }

    @Override
    public Object getItem(int position) {//数据
        if(position < mData.getItemNewsModels().size()){
            return mData.getItemNewsModels().get(position);
        }

        return mData.getNewsPlate().getData().get(position - (mData.getItemNewsModels().size()));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return null;
        int type = getItemViewType(position);
        View view = convertView;
        if(view == null){
            switch (type){
                case TYPE_HEAD:
                    view = mLayoutInflater.inflate(R.layout.item_news_desc, parent, false);
                    HeadItemViewHolder headItemViewHolder = new HeadItemViewHolder();
                    headItemViewHolder.title = (TextView)view.findViewById(R.id.tv_title);
                    headItemViewHolder.desc = (TextView)view.findViewById(R.id.tv_desc);
                    view.setTag(headItemViewHolder);
                    break;
                case TYPE_NORMAL:
                    view = mLayoutInflater.inflate(R.layout.item_normal, parent, false);
                    OneTextViewHolder oneTextViewHolder = new OneTextViewHolder();
                    oneTextViewHolder.content = (TextView) view.findViewById(R.id.tv_title);
                    view.setTag(oneTextViewHolder);
                    break;
            }
        }

        //data --> ui
        if(position < mData.getItemNewsModels().size()){
            if(type == TYPE_HEAD){
                HeadItemViewHolder headItemViewHolder = (HeadItemViewHolder)view.getTag();
                ItemNewsModel itemNewsModel = mData.getItemNewsModels().get(position);
                headItemViewHolder.title.setText(itemNewsModel.getTitle());
                headItemViewHolder.desc.setText(itemNewsModel.getDesc());
            }
        }else {
            if(type == TYPE_NORMAL){
                Object object = view.getTag();
                if(object instanceof OneTextViewHolder){
                    OneTextViewHolder oneTextViewHolder = (OneTextViewHolder)object;
                    int newPosition = position - mData.getItemNewsModels().size();
                    oneTextViewHolder.content.setText(mData.getNewsPlate().getData().get(newPosition).getTitle());
                }
            }
        }

        return view;

    }

    @Override
    public int getItemViewType(int position) {
        if(position < mData.getItemNewsModels().size()){
            return TYPE_HEAD;
        }

        return TYPE_NORMAL;
    }

    class HeadItemViewHolder{
        private TextView title;
        private TextView desc;
    }



}

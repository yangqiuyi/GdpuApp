package com.example.dell.gdpuapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dell.gdpuapp.R;
import com.example.dell.gdpuapp.modle.NormalPlate;
import com.example.dell.gdpuapp.viewholder.OneTextViewHolder;


public class PlateAdapter extends BaseAdapter {

    private NormalPlate mPlate;
    private LayoutInflater inflater;//LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
    private static final String TAG = "PlateAdapter";

    public PlateAdapter(NormalPlate plate, Context context) {
        this.mPlate = plate;
        inflater = LayoutInflater.from(context);//inflater就相当于将一个xml中定义的布局找出来.
    }


    public int getCount() {
        Log.e(TAG, "方法 getCount()");  //测试
        return mPlate.getData().size();

    }//   ArrayList<ItemModel> 数组的长度


    public Object getItem(int position) {
        Log.e(TAG, "方法 getItem(int position)");
        return mPlate.getData().get(position);
    }   //下标


    public long getItemId(int position) {
        Log.e(TAG, "getItemId(int position)");
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        //position 0

        View view = convertView;

        if (view == null){
            //xml
            view = inflater.inflate(R.layout.item_normal, parent, false);//xml   把listview_item xml给了view

            OneTextViewHolder viewHolder = new OneTextViewHolder();
            viewHolder.content = (TextView) view.findViewById(R.id.tv_title);//xml   把listview_item xml的textviewId给了content，
            //    操作content相当于操作listview_item的Textview
            view.setTag(viewHolder);
        }

        //ui data
        OneTextViewHolder viewHolder = (OneTextViewHolder) view.getTag();
        viewHolder.content.setText(mPlate.getData().get(position).getTitle());//  获得数据给content
        Log.e(TAG, "方法 getView(int position, View convertView, ViewGroup parent)");
        return view;
    }


}

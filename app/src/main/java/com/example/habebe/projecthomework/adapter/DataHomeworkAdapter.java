package com.example.habebe.projecthomework.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.habebe.projecthomework.R;
import com.example.habebe.projecthomework.activity.DataHomeWorkActivity;
import com.example.habebe.projecthomework.dao.DataHomeworkCollectionDao;
import com.example.habebe.projecthomework.dao.DataHomeworkItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Habebe on 8/4/2559.
 */
public class DataHomeworkAdapter extends BaseAdapter {

    Activity context;
    List<DataHomeworkItemDao> list;

    public DataHomeworkAdapter(Activity context, List<DataHomeworkItemDao> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size(); //ส่งขนาดของ List ที่เก็บข้อมุลอยู่
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder = new CustomViewHolder();
        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);
            initInstances(convertView, holder);
            convertView.setTag(holder);

        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(list.get(position).getName());
        holder.tvDetail.setText(list.get(position).getDetail());
        holder.tvDate.setText(list.get(position).getExpSent());
        return convertView;
    }

    private void initInstances(View convertView, CustomViewHolder holder) {
        holder.tvTitle = (TextView) convertView.findViewById(R.id.tvLabel);
        holder.tvDetail = (TextView) convertView.findViewById(R.id.tvDetail);
        holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
    }

    public void setData(List<DataHomeworkItemDao> data){
        list = data;
        notifyDataSetChanged();
    }

    private class CustomViewHolder {
        TextView tvTitle, tvDetail,tvDate;
    }


}


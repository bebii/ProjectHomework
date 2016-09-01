package com.example.habebe.projecthomework.dao;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.habebe.projecthomework.R;

import java.util.List;

/**
 * Created by Habebe on 23/5/2559.
 */
public class ChapterAdapter extends BaseAdapter {
    private Activity context;
    private List<ChapterDao> data;

    public ChapterAdapter(Activity context, List<ChapterDao> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ChapterDao getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder = new CustomViewHolder();
        if(convertView == null){
            convertView = context.getLayoutInflater().inflate(R.layout.chapter_item, parent, false);
            holder.tvChapterName = (TextView) convertView.findViewById(R.id.tvChapterName);
            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }
        holder.tvChapterName.setText(data.get(position).getName());
        return convertView;
    }

    private class CustomViewHolder {
        TextView tvChapterName;
    }
}

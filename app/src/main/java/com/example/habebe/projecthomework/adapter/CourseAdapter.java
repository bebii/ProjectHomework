package com.example.habebe.projecthomework.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.habebe.projecthomework.R;
import com.example.habebe.projecthomework.dao.CourseDao;

import java.util.List;

/**
 * Created by Habebe on 14/4/2559.
 */
public class CourseAdapter extends BaseAdapter {

    private Activity context;
    private List<CourseDao> data;

    public CourseAdapter(Activity context, List<CourseDao> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder = new CustomViewHolder();
        if(convertView == null){
            convertView = context.getLayoutInflater().inflate(R.layout.course_item, parent, false);
            holder.tvCourseName = (TextView) convertView.findViewById(R.id.tvCourseName);
            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }
        holder.tvCourseName.setText(data.get(position).getName());
        return convertView;
    }

    private class CustomViewHolder {
        TextView tvCourseName;
    }
}

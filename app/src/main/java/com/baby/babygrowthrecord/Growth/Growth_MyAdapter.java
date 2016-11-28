package com.baby.babygrowthrecord.Growth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.baby.babygrowthrecord.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Growth_MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Growth_Class> growth_classes = new ArrayList<>();
    private TextView year;
    private TextView week;
    private TextView duration;
    private TextView content;
    private ImageView img_first;
    private ImageView img_second;


    public Growth_MyAdapter(Context context, ArrayList<Growth_Class> growth_classes) {
        this.context = context;
        this.growth_classes = growth_classes;
    }

    @Override
    public int getCount() {
        return growth_classes.size();
    }

    @Override
    public Object getItem(int position) {
        return growth_classes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return growth_classes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.growth_item,null);
        }

        year = (TextView)convertView.findViewById(R.id.growth_left_year);
        year.setText(growth_classes.get(position).getYear());
        week = (TextView)convertView.findViewById(R.id.growth_left_week);
        week.setText(growth_classes.get(position).getWeek());
        duration = (TextView)convertView.findViewById(R.id.growth_right_duration);
        duration.setText(growth_classes.get(position).getDuration());
        content = (TextView)convertView.findViewById(R.id.growth_right_content);
        content.setText(growth_classes.get(position).getContent());
        img_first = (ImageView)convertView.findViewById(R.id.growth_firstimg);
        img_first.setImageResource(growth_classes.get(position).getImg_first());
        img_second = (ImageView)convertView.findViewById(R.id.growth_secondimg);
        img_second.setImageResource(growth_classes.get(position).getImg_second());

        return convertView;
    }
}

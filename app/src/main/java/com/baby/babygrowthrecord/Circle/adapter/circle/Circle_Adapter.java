
package com.baby.babygrowthrecord.Circle.adapter.circle;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baby.babygrowthrecord.Circle.babycircle.CircleMainActivity;
import com.baby.babygrowthrecord.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Circle_Adapter extends BaseAdapter {
    private List<Circle> circleList = new ArrayList<>();
    private Context context;
    private TextView friend_name;
    private TextView friend_phone;
    private TextView content;
    private ImageView photo;
    private ImageView voice;
    private ImageView video;

    public Circle_Adapter( Context context,List<Circle> circleList) {
        this.context = context;
        this.circleList = circleList;
    }

    @Override
    public int getCount() {
        return circleList.size();
    }

    @Override
    public Object getItem(int position) {
            return circleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return circleList.get(position).getCircle_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_baby_circleitem,null);
        }
        friend_name = (TextView)convertView.findViewById(R.id.friend_name);
        friend_name.setText(circleList.get(position).getFriend_name());
        friend_phone = (TextView)convertView.findViewById(R.id.friend_phone);
        friend_phone.setText(circleList.get(position).getFriend_phone());
        content = (TextView)convertView.findViewById(R.id.friend_content);
        content.setText(circleList.get(position).getFriend_content());
        photo = (ImageView) convertView.findViewById(R.id.imagethree);
        photo.setImageResource(circleList.get(position).getFriend_photo());
        voice = (ImageView)convertView.findViewById(R.id.imageone);
        voice.setImageResource(circleList.get(position).getFriend_voice());
        video = (ImageView)convertView.findViewById(R.id.imagetwo);
        video.setImageResource(circleList.get(position).getFriend_video());
        return convertView;
    }

}





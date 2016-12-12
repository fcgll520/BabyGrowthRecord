package com.baby.babygrowthrecord.Growth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.baby.babygrowthrecord.Fragment.GrowthFragment;
import com.baby.babygrowthrecord.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Growth_Activity_Bron extends AppCompatActivity {
    private TextView growth_left_year;
    private ArrayList<Growth_Class> growth_classes = new ArrayList<>();
    private TextView growth_left_week;
    private TextView growth_right_duration;
    private TextView growth_right_content;
    private ImageView growth_firstimg;
    private ImageView growth_secondimg;
    private CircleImageView growth_head;
    private TextView growth_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth);
        //获取用户头像和用户名
        growth_head =(CircleImageView)findViewById(R.id.growth_head);
        growth_name =(TextView) findViewById(R.id.growth_name);
        GrowthFragment g=new GrowthFragment();
        g.getUserInfo(growth_head,growth_name);
        //
        Intent i=getIntent();
        long id=i.getLongExtra("grow_id",-1);
        Log.e("id:",""+id);
        if(id!=-1){
            growth_left_year=(TextView)findViewById(R.id.growth_left_year);
            growth_left_year.setText(i.getStringExtra("grow_year"));

            growth_left_week=(TextView)findViewById(R.id.growth_left_week);
            growth_left_week.setText(i.getStringExtra("grow_week"));

            growth_right_duration=(TextView)findViewById(R.id.growth_right_duration);
            growth_right_duration.setText(i.getStringExtra("grow_time"));

            growth_right_content=(TextView)findViewById(R.id.growth_right_content);
            growth_right_content.setText(i.getStringExtra("grow_content"));

            growth_firstimg=(ImageView)findViewById(R.id.growth_firstimg);
//            growth_secondimg=(ImageView)findViewById(R.id.growth_secondimg);
            ImageLoader.getInstance().displayImage(i.getStringExtra("grow_pic1"),growth_firstimg);
//            ImageLoader.getInstance().displayImage(i.getStringExtra("grow_pic2"),growth_secondimg);
        }
    }
}

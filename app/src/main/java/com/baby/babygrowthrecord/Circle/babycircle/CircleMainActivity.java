package com.baby.babygrowthrecord.Circle.babycircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baby.babygrowthrecord.Circle.adapter.circle.Circle;
import com.baby.babygrowthrecord.Circle.adapter.circle.Circle_Adapter;
import com.baby.babygrowthrecord.Growth.Growth_Activity;
import com.baby.babygrowthrecord.Growth.Growth_Activity_Bron;
import com.baby.babygrowthrecord.Growth.Growth_MyAdapter;
import com.baby.babygrowthrecord.R;

import java.util.ArrayList;
import java.util.List;

import static com.baby.babygrowthrecord.R.drawable.third;

public class CircleMainActivity extends AppCompatActivity {
    private ArrayList<Circle> circleArrayList = new ArrayList<>();
    private ListView listView;
    private Circle_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_circleitem);
        //1.修改当前activity的布局文件，加入ListView控件设置id
        //2.定义数据源，字符串数组
        getDate();
        adapter = new Circle_Adapter(CircleMainActivity.this,circleArrayList);
        //3.定义item布局，使用Android内置ListView的item布局
        listView = (ListView)findViewById(R.id.circlelist);
        //4.根据数据源与item布局定义adapter
        //5.得到、ListView对象并设置adapter
        listView.setAdapter(adapter);
        //6.点击事件
     /*   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Growth_Activity.this,Growth_Activity_Bron.class);
                intent.putExtra("index",position);
                startActivityForResult(intent,1);
            }
        });*/

    }

    private void getDate() {
        circleArrayList.add(new Circle (1,"asun","来自 iphone6","今天带宝宝出去玩了",R.drawable.one,R.drawable.first,R.drawable.third));
        circleArrayList.add(new Circle (2,"asun","来自 iphone6","今天带宝宝出去玩了",R.drawable.one,R.drawable.first,R.drawable.third));
        circleArrayList.add(new Circle (3,"asun","来自 iphone6","今天带宝宝出去玩了",R.drawable.one,R.drawable.first,R.drawable.third));
        circleArrayList.add(new Circle (4,"asun","来自 iphone6","今天带宝宝出去玩了",R.drawable.one,R.drawable.first,R.drawable.third));
        circleArrayList.add(new Circle (5,"asun","来自 iphone6","今天带宝宝出去玩了",R.drawable.one,R.drawable.first,R.drawable.third));
        circleArrayList.add(new Circle (6,"asun","来自 iphone6","今天带宝宝出去玩了",R.drawable.one,R.drawable.first,R.drawable.third));

    }

}

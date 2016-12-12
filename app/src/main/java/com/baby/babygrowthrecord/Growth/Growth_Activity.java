package com.baby.babygrowthrecord.Growth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.baby.babygrowthrecord.R;

import java.util.ArrayList;

public class Growth_Activity extends AppCompatActivity {
    private ArrayList<Growth_Class> growth_classes = new ArrayList<>();
    private ListView growth_listview;
    private Growth_MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.growth_listview);

        //1.修改当前activity的布局文件，加入ListView控件设置id
        //2.定义数据源，字符串数组
        getDate();
        myAdapter = new Growth_MyAdapter(Growth_Activity.this,growth_classes);
        //3.定义item布局，使用Android内置ListView的item布局
        growth_listview = (ListView)findViewById(R.id.growth_listview);
        //4.根据数据源与item布局定义adapter
        //5.得到、ListView对象并设置adapter
        growth_listview.setAdapter(myAdapter);
        //6.点击事件
        growth_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Growth_Activity.this,Growth_Activity_Bron.class);
                intent.putExtra("index",position);
                startActivityForResult(intent,1);
            }
        });
    }

    private void getDate() {
//        growth_classes.add(new Growth_Class(0L,"14.08.18","周一","出生","宝宝今天出生，顺产8斤2两，非常可爱的小胖妞，爸爸妈妈一定好好记录你的成长",R.drawable.hundred_first,R.drawable.hundred_second));
//        growth_classes.add(new Growth_Class(1L,"14.09.18","周四","1月（31天）","宝宝今天满月了，爸爸带宝贝去打了乙肝疫苗第二针，有点儿疼，宝贝哭了一小会儿",R.drawable.born,R.drawable.head_sculpture));
//        growth_classes.add(new Growth_Class(2L,"14.11.25","周三","3月+8天","宝宝今天100天了，爸爸妈妈带宝宝去拍了百天照。",R.drawable.hundred_first,R.drawable.hundred_second));
//        growth_classes.add(new Growth_Class(3L,"14.08.18","周一","出生","宝宝今天出生，顺产8斤2两，非常可爱的小胖妞，爸爸妈妈一定好好记录你的成长",R.drawable.hundred_first,R.drawable.hundred_second));
//        growth_classes.add(new Growth_Class(4L,"14.09.18","周四","1月（31天）","宝宝今天满月了，爸爸带宝贝去打了乙肝疫苗第二针，有点儿疼，宝贝哭了一小会儿",R.drawable.born,R.drawable.head_sculpture));
//        growth_classes.add(new Growth_Class(5L,"14.11.25","周三","3月+8天","宝宝今天100天了，爸爸妈妈带宝宝去拍了百天照。",R.drawable.hundred_first,R.drawable.hundred_second));
    }
}

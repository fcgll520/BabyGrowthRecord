package com.baby.babygrowthrecord.Growth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


import com.baby.babygrowthrecord.R;

import java.util.ArrayList;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.growth);
        Intent i=getIntent();
        int a= i.getIntExtra("index",-1);
        if(a!=-1){
            getDate();
            growth_left_year=(TextView)findViewById(R.id.growth_left_year);
            growth_left_year.setText(growth_classes.get(a).getYear());
            growth_left_week=(TextView)findViewById(R.id.growth_left_week);
            growth_left_week.setText(growth_classes.get(a).getWeek());
            growth_right_duration=(TextView)findViewById(R.id.growth_right_duration);
            growth_right_duration.setText(growth_classes.get(a).getDuration());
            growth_right_content=(TextView)findViewById(R.id.growth_right_content);
            growth_right_content.setText(growth_classes.get(a).getContent());

            growth_firstimg=(ImageView)findViewById(R.id.growth_firstimg);
            growth_firstimg.setImageResource(growth_classes.get(a).getImg_first());
            growth_secondimg=(ImageView)findViewById(R.id.growth_secondimg);
            growth_secondimg.setImageResource(growth_classes.get(a).getImg_second());


        }
    }
    private void getDate() {
        growth_classes = new ArrayList();
        growth_classes.add(new Growth_Class(0L,"14.08.18","周一","出生","宝宝今天出生，顺产8斤2两，非常可爱的小胖妞，爸爸妈妈一定好好记录你的成长",R.drawable.hundred_first,R.drawable.hundred_second));
        growth_classes.add(new Growth_Class(1L,"14.09.18","周四","1月（31天）","宝宝今天满月了，爸爸带宝贝去打了乙肝疫苗第二针，有点儿疼，宝贝哭了一小会儿",R.drawable.born,R.drawable.head_sculpture));
        growth_classes.add(new Growth_Class(2L,"14.11.25","周三","3月+8天","宝宝今天100天了，爸爸妈妈带宝宝去拍了百天照。",R.drawable.hundred_first,R.drawable.hundred_second));
        growth_classes.add(new Growth_Class(3L,"14.08.18","周一","出生","宝宝今天出生，顺产8斤2两，非常可爱的小胖妞，爸爸妈妈一定好好记录你的成长",R.drawable.hundred_first,R.drawable.hundred_second));
        growth_classes.add(new Growth_Class(4L,"14.09.18","周四","1月（31天）","宝宝今天满月了，爸爸带宝贝去打了乙肝疫苗第二针，有点儿疼，宝贝哭了一小会儿",R.drawable.born,R.drawable.head_sculpture));
        growth_classes.add(new Growth_Class(5L,"14.11.25","周三","3月+8天","宝宝今天100天了，爸爸妈妈带宝宝去拍了百天照。",R.drawable.hundred_first,R.drawable.hundred_second));
    }
}

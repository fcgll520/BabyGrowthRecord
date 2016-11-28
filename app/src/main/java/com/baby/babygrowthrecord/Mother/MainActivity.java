package com.baby.babygrowthrecord.Mother;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baby.babygrowthrecord.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_main);

        //获取listview
        mListView=(ListView) findViewById(R.id.ListView);
        //配置适配器
        GoogleCardAdapter mAdapter=new GoogleCardAdapter(this, getItems());
        mListView.setAdapter(mAdapter);
        //给item设置监听

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,CardMessage.class);
                startActivity(intent);
            }
        });
    }
    private List<GoogleCard> getItems()
    {
        List<GoogleCard> mCards=new ArrayList<GoogleCard>();

        //第一张卡片
        GoogleCard mCard=new GoogleCard("纸尿裤的选择和使用",R.drawable.mother_item1);
        mCards.add(mCard);

        //第二张卡片
        GoogleCard mCard1=new GoogleCard("就是他们让宝贝越来越笨！",R.drawable.mother_pic1);
        mCards.add(mCard1);

        //第三张卡片
        GoogleCard mCard2=new GoogleCard("纸尿裤的选择和使用",R.drawable.mother_item1);
        mCards.add(mCard2);

        //第四张卡片
        GoogleCard mCard3=new GoogleCard("纸尿裤的选择和使用",R.drawable.mother_item1);
        mCards.add(mCard3);
        return mCards;
    }

}

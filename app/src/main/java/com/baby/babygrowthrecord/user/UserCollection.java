package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.baby.babygrowthrecord.Mother.CardMessage;
import com.baby.babygrowthrecord.Mother.GoogleCard;
import com.baby.babygrowthrecord.Mother.GoogleCardAdapter;
import com.baby.babygrowthrecord.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2016/11/22.
 */
public class UserCollection extends Activity {
    private ListView mListView;
    private List<GoogleCard> mCards=new ArrayList<GoogleCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_collection);
        init();
    }

    private void init() {
        getItems();
        //获取listview
        mListView=(ListView) findViewById(R.id.lv_userCollection);
        //配置适配器
        GoogleCardAdapter mAdapter=new GoogleCardAdapter(UserCollection.this,mCards);
        mListView.setAdapter(mAdapter);
        //给item设置监听

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(UserCollection.this,CardMessage.class);
                startActivity(intent);
            }
        });
    }

    private void getItems()
    {

       /* //第一张卡片
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
        mCards.add(mCard3);*/
    }
    public void backOnClick(View view){
        finish();
    }
}

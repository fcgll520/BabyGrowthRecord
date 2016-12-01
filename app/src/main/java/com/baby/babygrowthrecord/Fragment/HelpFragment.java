package com.baby.babygrowthrecord.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baby.babygrowthrecord.Mother.CardMessage;
import com.baby.babygrowthrecord.Mother.GoogleCard;
import com.baby.babygrowthrecord.Mother.GoogleCardAdapter;
import com.baby.babygrowthrecord.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/11/22.
 */
public class HelpFragment extends Fragment{
    private View view;
    private ListView mListView;
    private List<GoogleCard> mCards=new ArrayList<GoogleCard>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_mother_main, container, false);
        getItems();
        //获取listview
        mListView=(ListView) view.findViewById(R.id.ListView);
        //配置适配器
        GoogleCardAdapter mAdapter=new GoogleCardAdapter(getActivity(),mCards);
        mListView.setAdapter(mAdapter);
        //给item设置监听

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),CardMessage.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getItems()
    {

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
    }

}

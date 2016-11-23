package com.example.apple.cardexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView=(ListView) findViewById(R.id.ListView);
        GoogleCardAdapter mAdapter=new GoogleCardAdapter(this, getItems());
        mListView.setAdapter(mAdapter);
    }
    private List<GoogleCard> getItems()
    {
        List<GoogleCard> mCards=new ArrayList<GoogleCard>();
        for(int i=0;i<20;i++)
        {
            GoogleCard mCard=new GoogleCard("这是第"+(i+1)+"张卡片", getResource(i));
            mCards.add(mCard);
        }
        return mCards;
    }

    private int getResource(int Index)
    {
        int mResult=0;
        switch(Index%2)
        {
            case 0:
                mResult=R.drawable.item3;
                break;
            case 1:
                mResult=R.drawable.top;
                break;
        }
        return mResult;
    }
}

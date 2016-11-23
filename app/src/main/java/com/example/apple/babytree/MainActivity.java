package com.example.apple.babytree;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.Window;
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

        //第一张卡片
        GoogleCard mCard=new GoogleCard("纸尿裤的选择和使用",R.drawable.item1);
        mCards.add(mCard);

        //第二张卡片
        GoogleCard mCard1=new GoogleCard("就是他们让宝贝越来越笨！",R.drawable.pic1);
        mCards.add(mCard1);

        //第三张卡片
        GoogleCard mCard2=new GoogleCard("纸尿裤的选择和使用",R.drawable.item1);
        mCards.add(mCard2);

        //第四张卡片
        GoogleCard mCard3=new GoogleCard("纸尿裤的选择和使用",R.drawable.item1);
        mCards.add(mCard3);
        return mCards;
    }

}

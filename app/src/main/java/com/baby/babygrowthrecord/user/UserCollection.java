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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2016/11/22.
 */
public class UserCollection extends Activity {
    private ListView mListView;
    private List<GoogleCard> mCards=new ArrayList<GoogleCard>();
    private GoogleCardAdapter mAdapter;

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
        mAdapter = new GoogleCardAdapter(UserCollection.this,mCards);
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
        //从服务器获取信息并解析
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(UserCollection.this,Utils.StrUrl+"essay/test",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject object;
                mCards.clear();
                for (int i=0;i<response.length();i++){
                    try {
                        object=response.getJSONObject(i);
                        GoogleCard card=new GoogleCard(object.getInt("essay_id"),object.getString("essay_title"),
                                Utils.StrUrl+object.getString("essay_photo"));
                        mCards.add(card);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mListView.setAdapter(mAdapter);
            }
        });
    }
    public void backOnClick(View view){
        finish();
    }
}

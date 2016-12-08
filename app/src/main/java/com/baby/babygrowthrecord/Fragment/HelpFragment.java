package com.baby.babygrowthrecord.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

        //获取listview
        mListView=(ListView) view.findViewById(R.id.ListView);

        //配置适配器
        final GoogleCardAdapter mAdapter=new GoogleCardAdapter(getActivity(),mCards);
        mListView.setAdapter(mAdapter);
        //给item设置监听

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),CardMessage.class);
                intent.putExtra("essay_id",mCards.get(i).getId());
                startActivity(intent);
            }
        });

        mListView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });

        //网络请求
        //从服务器获取信息并解析
        AsyncHttpClient client =   new AsyncHttpClient();
        String url = "http://169.254.76.180:8080/essay/test";

        client.get(getActivity(), url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println(response.toString());
                try {
                    for (int i=0;i<response.length();i++){
                        JSONObject data=response.getJSONObject(i);
                        GoogleCard mCard=new GoogleCard(data.getInt("essay_id"),data.getString("essay_title"),data.getString("essay_photo"));
                        mCards.add(mCard);
                        Log.e("essay","true");
                    }
                } catch (JSONException e) {
                    Log.e("essay","cuowu");
                    e.printStackTrace();
                }

                mListView.setAdapter(mAdapter);
            }
            //            @Override
//            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//                System.out.println(response.toString());
//                try {
//                    for (int i=0;i<response.length();i++){
//                        JSONObject data=response.getJSONObject(i);
//                        GoogleCard mCard=new GoogleCard(data.getInt("essay_id"),data.getString("essay_title"),data.getString("essay_photo"));
//                        mCards.add(mCard);
//                        Log.e("essay","true");
//                    }
//                } catch (JSONException e) {
//                    Log.e("essay","cuowu");
//                    e.printStackTrace();
//                }
//
//                mListView.setAdapter(mAdapter);
//            }
        });
        return view;
    }

}

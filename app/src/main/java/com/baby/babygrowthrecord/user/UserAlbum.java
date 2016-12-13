package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by think on 2016/11/22.
 */
public class UserAlbum extends Activity {
    private RecyclerView rvList;
    private UserAlbumAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_album);
        init();
        getData();
    }

    private void init() {
        rvList=(RecyclerView)findViewById(R.id.rv_userAlbum_list);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        myAdapter = new UserAlbumAdapter();
        rvList.setAdapter(myAdapter);
    }

    public void getData() {
        final ArrayList<String> list=new ArrayList<>();
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(UserAlbum.this, Utils.StrUrl+"user/getUserAlbumInfoById/"+Utils.userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject object;
                for (int i=0;i<response.length();i++){
                    try {
                        list.add(Utils.StrUrl+response.getJSONObject(i).getString("friend_photo"));
                        Log.e("photo:",list.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                int k=response.length()-1;
                ArrayList<String> imgList=new ArrayList<String>();
                for (int i=0;i<response.length() && k>=0;i++,k--){
                    imgList.add(i,list.get(k));
                }
                myAdapter.replaceAll(imgList);
                rvList.setAdapter(myAdapter);
            }
        });
    }

    public void backOnClick(View view){
        finish();
    }
}

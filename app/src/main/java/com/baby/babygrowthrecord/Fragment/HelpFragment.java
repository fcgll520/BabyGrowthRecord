package com.baby.babygrowthrecord.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Mother.CardMessage;
import com.baby.babygrowthrecord.Mother.GoogleCard;
import com.baby.babygrowthrecord.Mother.GoogleCardAdapter;
import com.baby.babygrowthrecord.PullToRefresh.RefreshableView;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

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
    private GoogleCardAdapter mAdapter;
    private RefreshableView refreshableView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_mother_main, container, false);
        //下拉刷新
        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view_help);

        getItems();
        //获取listview
        mListView=(ListView) view.findViewById(R.id.ListView);
        //配置适配器
        mAdapter = new GoogleCardAdapter(getActivity(),mCards);
        mListView.setAdapter(mAdapter);
        //给item设置监听

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),CardMessage.class);
                intent.putExtra("essay_id",mCards.get(i).getmId());
                Log.e("id:"+mCards.get(i).getmId(),"i:"+i);
                startActivity(intent);
            }
        });
        //下拉刷新
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                getItems();
                refreshableView.finishRefreshing();
            }
        }, 0);
        return view;
    }

    private void getItems()
    {
        //从服务器获取信息并解析
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(getActivity(),Utils.StrUrl+"essay/test",new JsonHttpResponseHandler(){
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

}

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
import android.widget.TextView;

import com.baby.babygrowthrecord.Growth.Growth_Activity_Bron;
import com.baby.babygrowthrecord.Growth.Growth_Class;
import com.baby.babygrowthrecord.Growth.Growth_MyAdapter;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus on 2016/11/22.
 */
public class GrowthFragment extends Fragment{
    private View view;
    private RefreshableView refreshableView;
    private ArrayList<Growth_Class> growth_classes = new ArrayList<>();
    private ListView growth_listview;
    private Growth_MyAdapter myAdapter;
    private CircleImageView growth_head;
    private TextView growth_name;
    AsyncHttpClient client= new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_growth_listview, container, false);
        //下拉刷新
        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view_growth);

        growth_head=(CircleImageView)view.findViewById(R.id.growth_head);
        growth_name=(TextView) view.findViewById(R.id.growth_name);
        getUserInfo(growth_head,growth_name);
        getData();
        myAdapter = new Growth_MyAdapter(getActivity(),growth_classes);
        //3.定义item布局，使用Android内置ListView的item布局
        growth_listview = (ListView)view.findViewById(R.id.growth_listview);
        //4.根据数据源与item布局定义adapter
        //5.得到、ListView对象并设置adapter
        growth_listview.setAdapter(myAdapter);
        //6.点击事件
        growth_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),Growth_Activity_Bron.class);
                intent.putExtra("grow_id",growth_classes.get(position).getId());
                intent.putExtra("grow_year",growth_classes.get(position).getYear());
                intent.putExtra("grow_week",growth_classes.get(position).getWeek());
                intent.putExtra("grow_time",growth_classes.get(position).getDuration());
                intent.putExtra("grow_content",growth_classes.get(position).getContent());
                intent.putExtra("grow_pic1",growth_classes.get(position).getImg_first());
                intent.putExtra("grow_pic2",growth_classes.get(position).getImg_second());
                startActivityForResult(intent,1);
            }
        });
        //下拉刷新
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refreshableView.finishRefreshing();
            }
        }, 0);
        return view;
    }

    public void getUserInfo(final CircleImageView head,final TextView name){
        //获取用户名和用户头像
        client.get(getActivity(),Utils.StrUrl+"user/getUserInfoById/"+Utils.userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Log.e(response.getString("set_id"),response.getString("set_name"));
                    name.setText(response.getString("set_name"));
                    ImageLoader.getInstance().displayImage(Utils.StrUrl+response.getString("set_dp"),head);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getData(){
        //获取成长记录列表
        client.get(getActivity(),Utils.StrUrl+"grow/test/"+Utils.userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject object;
                growth_classes.clear();
                for (int i=0;i<response.length();i++){
                    try {
                        object=response.getJSONObject(i);
                        growth_classes.add(new Growth_Class(object.getLong("grow_id"),object.getString("grow_year"),
                                object.getString("grow_week"),object.getString("grow_time"),
                                object.getString("grow_content"), Utils.StrUrl+object.getString("grow_picture")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                growth_listview.setAdapter(myAdapter);
            }
        });
    }
}

package com.baby.babygrowthrecord.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baby.babygrowthrecord.Circle.FridListAdapter;
import com.baby.babygrowthrecord.Circle.MessageModle;
import com.baby.babygrowthrecord.Growth.Growth_Activity_Bron;
import com.baby.babygrowthrecord.Growth.Growth_Class;
import com.baby.babygrowthrecord.Growth.Growth_MyAdapter;
import com.baby.babygrowthrecord.PullToRefresh.RefreshableView;
import com.baby.babygrowthrecord.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by asus on 2016/11/22.
 */
public class GrowthFragment extends Fragment{
    private View view;
    private ArrayList<Growth_Class> growth_classes = new ArrayList<>();
    private ListView growth_listview;
    private Growth_MyAdapter myAdapter;
    private RefreshableView refreshableView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.growth_listview, container, false);
        //        下拉刷新
        //refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view_growth);
        getDate();
        myAdapter = new Growth_MyAdapter(getActivity(),growth_classes);
        //3.定义item布局，使用Android内置ListView的item布局
        growth_listview = (ListView)view.findViewById(R.id.growth_listview);
        //4.根据数据源与item布局定义adapter
        //5.得到、ListView对象并设置adapter
        growth_listview.setAdapter(myAdapter);
//     下拉刷新
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                getDate();
                refreshableView.finishRefreshing();
            }
        }, 0);
        //6.点击事件
        growth_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),Growth_Activity_Bron.class);
                intent.putExtra("index",position);
                startActivityForResult(intent,1);
            }
        });
        return view;
    }

    private void getDate(){
        growth_classes.add(new Growth_Class(0L,"14.08.18","周一","出生","宝宝今天出生，顺产8斤2两，非常可爱的小胖妞，爸爸妈妈一定好好记录你的成长",R.drawable.hundred_first,R.drawable.hundred_second));
        growth_classes.add(new Growth_Class(1L,"14.09.18","周四","1月（31天）","宝宝今天满月了，爸爸带宝贝去打了乙肝疫苗第二针，有点儿疼，宝贝哭了一小会儿",R.drawable.born,R.drawable.head_sculpture));
        growth_classes.add(new Growth_Class(2L,"14.11.25","周三","3月+8天","宝宝今天100天了，爸爸妈妈带宝宝去拍了百天照。",R.drawable.hundred_first,R.drawable.hundred_second));
        growth_classes.add(new Growth_Class(3L,"14.08.18","周一","出生","宝宝今天出生，顺产8斤2两，非常可爱的小胖妞，爸爸妈妈一定好好记录你的成长",R.drawable.hundred_first,R.drawable.hundred_second));
        growth_classes.add(new Growth_Class(4L,"14.09.18","周四","1月（31天）","宝宝今天满月了，爸爸带宝贝去打了乙肝疫苗第二针，有点儿疼，宝贝哭了一小会儿",R.drawable.born,R.drawable.head_sculpture));
        growth_classes.add(new Growth_Class(5L,"14.11.25","周三","3月+8天","宝宝今天100天了，爸爸妈妈带宝宝去拍了百天照。",R.drawable.hundred_first,R.drawable.hundred_second));
    }
}

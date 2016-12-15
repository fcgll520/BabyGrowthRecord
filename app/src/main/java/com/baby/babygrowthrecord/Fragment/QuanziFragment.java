package com.baby.babygrowthrecord.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baby.babygrowthrecord.Circle.Circle;
import com.baby.babygrowthrecord.Circle.FridListAdapter;
import com.baby.babygrowthrecord.PullToRefresh.RefreshableView;
import com.baby.babygrowthrecord.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus on 2016/11/22.
 */
public class  QuanziFragment extends Fragment {
    private View view;
    public static final String TAG = "MainActivity";
    private FridListAdapter mAdapter;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
//    private RefreshableView refreshableView;
    private ArrayList<Circle> circleList=new ArrayList<>();
    private ArrayList<String> headPicList=new ArrayList<>();  //头像动态数组

    private RefreshableView refreshableView;
    private PullToRefreshListView refreshLv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_circle_main, container, false);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        setColor(getActivity(), Color.parseColor("#63b68b") );
        //下拉刷新
//        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view_circle);
        getData();
        mAdapter = new FridListAdapter(getActivity(), circleList,headPicList);
        refreshLv = (PullToRefreshListView)view.findViewById(R.id.list);
        refreshLv.setAdapter(mAdapter);
        //下拉刷新
//        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getData();
//                refreshableView.finishRefreshing();
//            }
//        }, 0);
        refreshLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getData();
            }
        });
        return view;
    }

    //获取服务器端数据
    public void getData(){
        AsyncHttpClient client=new AsyncHttpClient();
        //获取圈子中用户的头像
        client.get(getActivity(),Utils.StrUrl+"circle/getFriendAvator",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                //解析Json串
                JSONObject object;
                ArrayList<String> tempList=new ArrayList<String>();
                for (int i=0;i<response.length();i++){
                    try {
                        object=response.getJSONObject(i);
                        tempList.add(Utils.StrUrl+object.getString("user_photo"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                int k=response.length()-1;
                if (headPicList!=null){
                    headPicList.clear();
                }
                for (int i=0;i<tempList.size()&&k>=0;i++,k--){
                    headPicList.add(i,tempList.get(k));
                }
                refreshLv.setAdapter(mAdapter);
                refreshLv.onRefreshComplete();
            }
        });
        //获取动态内容
        client.get(getActivity(),Utils.StrUrl+"circle/test",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                //解析Json串
                JSONObject object;
                ArrayList<Circle> list=new ArrayList<Circle>();
                for (int i=0;i<response.length();i++){
                    try {
                        object=response.getJSONObject(i);
                        if (object.getString("cir_photo").equals("null")){      //没有照片的动态
                            list.add(i,new Circle(object.getInt("cir_id"),"",object.getString("cir_name"), object.getString("cir_content")));
                        }else {
                            list.add(i,new Circle(object.getInt("cir_id"),"",object.getString("cir_name"),
                                    object.getString("cir_content"),new String[]{Utils.StrUrl+object.getString("cir_photo")}));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                int k=response.length()-1;
                if (circleList!=null){
                    circleList.clear();
                }
                for (int i=0;i<list.size()&&k>=0;i++){
                    circleList.add(i,list.get(k));
                    k--;
                }
                refreshLv.setAdapter(mAdapter);
                refreshLv.onRefreshComplete();
            }
        });
    }

    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

}

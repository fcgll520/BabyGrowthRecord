package com.baby.babygrowthrecord.Fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baby.babygrowthrecord.Circle.Circle;
import com.baby.babygrowthrecord.Circle.FridListAdapter;
import com.baby.babygrowthrecord.Circle.MessageModle;
import com.baby.babygrowthrecord.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by asus on 2016/11/22.
 */
public class  QuanziFragment extends ListFragment {
    private View view;
    public static final String TAG = "MainActivity";
    private FridListAdapter mAdapter;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private MessageModle result=new MessageModle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_circle_main, container, false);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        setColor(getActivity(), Color.parseColor("#63b68b") );
        getData();
        mAdapter = new FridListAdapter(getActivity(), result.list);
        setListAdapter(mAdapter);
        return view;
    }

    public void getData(){
        result.list=new ArrayList<>();
        result.code=200;
        result.msg="ok";
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(getActivity(),Utils.urlStr+"circle/test",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject object;
                String temp;
                for (int i=0;i<response.length();i++){
                    try {
                        object=response.getJSONObject(i);
                        if (object.getString("friend_photo").equals("null")){
                            result.list.add(new Circle(object.getInt("cir_id"),"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",
                                    object.getString("friend_name"), object.getString("friend_content")));
                        }else {
                            result.list.add(new Circle(object.getInt("cir_id"),"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",
                                    object.getString("friend_name"), object.getString("friend_content"),new String[]{Utils.urlStr+object.getString("friend_photo")}));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setListAdapter(mAdapter);
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

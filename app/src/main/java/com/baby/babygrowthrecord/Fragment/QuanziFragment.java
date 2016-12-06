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

/**
 * Created by asus on 2016/11/22.
 */
public class  QuanziFragment extends ListFragment {
    private View view;
    public static final String TAG = "MainActivity";
    private FridListAdapter mAdapter;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    final String urlStr = "http://192.168.1.111:8080";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_circle_main, container, false);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        setColor(getActivity(), Color.parseColor("#63b68b") );
        new LoderDataTask().execute();
        return view;
    }
    class LoderDataTask extends AsyncTask<Void, Void, MessageModle> {

        @Override
        protected MessageModle doInBackground(Void... params) {

            Gson gson = new Gson();
            try {
                URL url=new URL(urlStr+"/circle/test");
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                //获取JSon串
                InputStream is=connection.getInputStream();
                byte[]b=new byte[2048];
                String s="";
                int a;
                while ((a=is.read(b))!=-1){
                    String temp=new String(b);
                    s=s+temp;
                }
                Log.e("s:",s);
                //解析JSon串
                JSONArray array=new JSONArray(s);
                int []id=new int[array.length()];
                String []avator=new String[array.length()];
                String []name=new String[array.length()];
                String []content=new String[array.length()];
                String []urls=new String[array.length()];
                JSONObject object;
                for (int i=0;i<array.length();i++){
                    try {
                        object=array.getJSONObject(i);
                        id[i]=object.optInt("cir_id");
                        avator[i]="https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
                        name[i]=object.optString("friend_name");
                        content[i]=object.optString("friend_content");
                        urls[i]=urlStr+object.optString("friend_photo");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String circle="";
                for (int i=0;i<id.length;i++){
                    String split="";
                    if (i!=0){
                       split=",";
                    }
                    circle=circle+split+"{\"id\":"+id[i]+",\"avator\":\""+avator[i]+"\",\"name\":\""+name[i]+"\",\"content\":\""
                            +content[i]+"\",\"urls\":[\""+urls[i]+"\"]}";
                }
                String str = "{\"code\":200,\"msg\":\"ok\",list:["+circle+"]}";
                Log.e("str:",str);
                MessageModle msg = gson.fromJson(str, MessageModle.class);
                return msg;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MessageModle result) {
            mAdapter = new FridListAdapter(getActivity(), result.list);
            setListAdapter(mAdapter);
        }
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

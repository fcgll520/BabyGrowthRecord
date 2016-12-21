package com.baby.babygrowthrecord.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baby.babygrowthrecord.Mother.CardMessage;
import com.baby.babygrowthrecord.Mother.GoogleCard;
import com.baby.babygrowthrecord.Mother.GoogleCardAdapter;
import com.baby.babygrowthrecord.R;
import com.baby.babygrowthrecord.util.Util;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/11/22.
 */
public class HelpFragment extends Fragment{
    private View view;
    private PullToRefreshListView mListView;
    private List<GoogleCard> mCards=new ArrayList<GoogleCard>();
    private GoogleCardAdapter mAdapter;
//    private RefreshableView refreshableView;
    public static PopupWindow pop;
    private Button btn;
    public int essay_Id;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_mother_main, container, false);


        //显示收藏按钮
        LayoutInflater inflater1=LayoutInflater.from(getActivity());
        View popview=inflater1.inflate(R.layout.activity_mother_itme_collection,null);
        //创建popupwindow对象
        pop=new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,false);
        //设置参数实现点击外面消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边消失
        pop.setOutsideTouchable(true);
        //设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);

        getItems();
        //获取listview
        mListView=(PullToRefreshListView) view.findViewById(R.id.ListView);
        //配置适配器
        mAdapter = new GoogleCardAdapter(getActivity(),mCards);
        mListView.setAdapter(mAdapter);
        //给item设置监听

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),CardMessage.class);
                intent.putExtra("essay_id",mCards.get(i-1).getmId());
                Log.e("id:"+mCards.get(i-1).getmId(),"i:"+i);
                startActivity(intent);
            }
        });

        mListView.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                pop.showAtLocation(view, Gravity.NO_GRAVITY,800,150);
                pop.showAsDropDown(view);
                essay_Id=mCards.get(i-1).getmId();
                Log.e("essay_id", String.valueOf(essay_Id));
                return true;
            }
        });

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getItems();
            }
        });

         btn =(Button)popview.findViewById(R.id.collection_btn);
         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(getActivity(),"点击了popupwindow",Toast.LENGTH_SHORT).show();
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         sendDatatoServer();
                     }
                 }).start();

             }
         });
        return view;
    }

    private void sendDatatoServer() {
        try {
            String Url= Utils.StrUrl+"collection/add?essay_id="+essay_Id+"&user_id="+Utils.userId;
            Log.e("url",Url);
            URL url=new URL(Url);
            HttpURLConnection coon= (HttpURLConnection) url.openConnection();
            coon.setRequestMethod("GET");
            coon.setConnectTimeout(3000);
            coon.connect();
            Log.e("发送成功", String.valueOf(coon.getResponseCode()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                for (int i=response.length();i>=0;i--){
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
                mListView.onRefreshComplete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("GET_GROWTH_INFO_ERROR",throwable.toString());
                Toast.makeText(getActivity(),"网络连接错误，请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        });
    }

}

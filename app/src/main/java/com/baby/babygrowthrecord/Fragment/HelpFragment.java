package com.baby.babygrowthrecord.Fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baby.babygrowthrecord.Mother.CardMessage;
import com.baby.babygrowthrecord.Mother.GoogleCard;
import com.baby.babygrowthrecord.Mother.GoogleCardAdapter;
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
    private Context mContext=null;
    public static PopupWindow pop;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_mother_main, container, false);

      //实现长按出现收藏按钮
        // 引入窗口配置文件
        LayoutInflater inflater1 = LayoutInflater.from(getActivity());
        View popview = inflater1.inflate(R.layout.activity_mother_itme_collection, null);
        // 创建PopupWindow对象
       pop = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);


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

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view1, int i, long l) {
               pop.showAtLocation(view1,Gravity.HORIZONTAL_GRAVITY_MASK,0,0);
               pop.showAsDropDown(view1);
                return true;
            }
        });
        return view;
    }

   /* private void showPopupWindow(View view) {
        View contentView=LayoutInflater.from(mContext).inflate(R.layout.activity_mother_itme_collection,null);
        final PopupWindow popupWindow=new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("mengdd","onTouch:");
                return false;
            }
        });
        popupWindow.showAsDropDown(view);
    }*/

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

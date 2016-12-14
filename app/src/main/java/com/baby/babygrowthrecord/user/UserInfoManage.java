package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by think on 2016/11/22.
 */
public class UserInfoManage extends Activity {
    private RelativeLayout rlName;
    private RelativeLayout rlAge;
    private RelativeLayout rlHeight;
    private RelativeLayout rlWeight;
    private TextView tvName;
    private TextView tvAge;
    private TextView tvHeight;
    private TextView tvWeight;

    private View.OnClickListener myListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String editHint="";
            String url=Utils.StrUrl+"baby/editBaby";
            String title="";
            String msgName="";
            Intent i=new Intent(UserInfoManage.this,UserInfoManageEdit.class);
            switch (v.getId()){
                case R.id.rv_userInfo_babyName:
                    editHint="昵称";
                    url=url+"Name";
                    title="昵称";
                    break;
                case R.id.rv_userInfo_babyAge:
                    editHint="年龄，单位为周岁";
                    url=url+"Age";
                    title="年龄";
                    break;
                case R.id.rv_userInfo_height:
                    editHint="身高，单位为cm";
                    url=url+"Height";
                    title="身高";
                    break;
                case R.id.rv_userInfo_weight:
                    editHint="体重，单位为kg";
                    url=url+"Weight";
                    title="体重";
                    break;
                case R.id.tv_userInfo_name:
                    editHint="昵称";
                    url=url+"Name";
                    title="昵称";
                    break;
                case R.id.tv_userInfo_age:
                    editHint="年龄，单位为周岁";
                    url=url+"Age";
                    title="年龄";
                    break;
                case R.id.tv_userInfo_height:
                    editHint="身高，单位为cm";
                    url=url+"Height";
                    title="身高";
                    break;
                case R.id.tv_userInfo_weight:
                    editHint="体重，单位为kg";
                    url=url+"Weight";
                    title="体重";
                    break;
                default:
                    break;
            }
            i.putExtra("editHint",editHint);
            i.putExtra("url",url+"?user_id="+Utils.userId);
            i.putExtra("title",title);
            startActivity(i);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (tvName!=null){
            Log.e("userInfo-onCreate","1");
            setData();
        }
        super.onCreate(savedInstanceState);
        if (tvName!=null){
            Log.e("userInfo-onCreate","1");
            setData();
        }
        setContentView(R.layout.activity_user_infomanage);
        if (tvName!=null){
            Log.e("userInfo-onCreate","1");
            setData();
        }
        init();
    }

    private void init() {
        rlName=(RelativeLayout)findViewById(R.id.rv_userInfo_babyName);
        rlAge=(RelativeLayout)findViewById(R.id.rv_userInfo_babyAge);
        rlHeight=(RelativeLayout)findViewById(R.id.rv_userInfo_height);
        rlWeight=(RelativeLayout)findViewById(R.id.rv_userInfo_weight);
        tvName=(TextView) findViewById(R.id.tv_userInfo_name);
        tvAge=(TextView) findViewById(R.id.tv_userInfo_age);
        tvHeight=(TextView) findViewById(R.id.tv_userInfo_height);
        tvWeight=(TextView) findViewById(R.id.tv_userInfo_weight);

        setData();

        rlName.setOnClickListener(myListener);
        rlAge.setOnClickListener(myListener);
        rlHeight.setOnClickListener(myListener);
        rlWeight.setOnClickListener(myListener);
        tvName.setOnClickListener(myListener);
        tvAge.setOnClickListener(myListener);
        tvHeight.setOnClickListener(myListener);
        tvWeight.setOnClickListener(myListener);
    }

    //获取用户宝宝的信息
    private void setData() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(UserInfoManage.this, Utils.StrUrl+"user/getBabyInfoById/"+Utils.userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    tvName.setText(response.getString("baby_name"));
                    tvAge.setText(response.getString("baby_years")+"周岁");
                    tvHeight.setText(response.getString("baby_height")+"cm");
                    tvWeight.setText(response.getString("baby_weight")+"kg");
                    Log.e(response.getString("baby_name"),response.getString("baby_weight"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("setData-statusCode",statusCode+"");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    public void backOnClick(View view){
        finish();
    }
}

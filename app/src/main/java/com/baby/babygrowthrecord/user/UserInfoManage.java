package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by think on 2016/11/22.
 */
public class UserInfoManage extends Activity {
    private EditText etName;
    private EditText etAge;
    private EditText etHeight;
    private EditText etWeight;
    private TextView tvSave;
    private String name;
    private String age;
    private String height;
    private String weight;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (responseCode==200){
                Toast.makeText(UserInfoManage.this,"宝宝信息保存成功！",Toast.LENGTH_SHORT).show();
            setHintData();
            }else {
                Toast.makeText(UserInfoManage.this,"宝宝信息保存失败！",Toast.LENGTH_SHORT).show();
            }
            Log.e("handler","handleMessage");
        }
    };
    private int responseCode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infomanage);
        init();
    }

    private void init() {
        etName=(EditText)findViewById(R.id.et_userInfo_name);
        etAge=(EditText)findViewById(R.id.et_userInfo_age);
        etHeight=(EditText)findViewById(R.id.et_userInfo_height);
        etWeight=(EditText)findViewById(R.id.et_userInfo_weight);
        tvSave=(TextView)findViewById(R.id.tv_userInfo_save);
        setHintData();
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                age = etAge.getText().toString();
                height = etHeight.getText().toString();
                weight = etWeight.getText().toString();
                if (name.equals("") || age.equals("") || height.equals("") || weight.equals("")){
                    Toast.makeText(UserInfoManage.this,"请输入完整的宝宝信息!",Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendBabyInfo();
                            handler.sendMessage(new Message());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    //发送修改后宝宝信息给服务器端
    private int sendBabyInfo() throws IOException {
        //http://192.168.56.1:8080/baby/add?user_id=1&baby_name=new%E5%AE%9D%E5%AE%9D&baby_age=9&baby_height=90&baby_weight=80
        String urlStr=Utils.StrUrl+"baby/add?user_id="+Utils.userId+"&baby_name="+name+"&baby_age="
                +age+"&baby_height="+height+"&baby_weight="+weight;
        HttpURLConnection connection=(HttpURLConnection) (new URL(urlStr)).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        responseCode = connection.getResponseCode();
        Log.e("sendBabyInfo~code", responseCode +"");
        return connection.getResponseCode();
    }

    //获取用户宝宝的信息
    private void setHintData() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(UserInfoManage.this, Utils.StrUrl+"user/getBabyInfoById/"+Utils.userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    etName.setHint(response.getString("baby_name"));
                    etAge.setHint(response.getString("baby_years")+"周岁");
                    etHeight.setHint(response.getString("baby_height")+"cm");
                    etWeight.setHint(response.getString("baby_weight")+"kg");
                    Log.e(response.getString("baby_name"),response.getString("baby_weight"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("setHintData-statusCode",statusCode+"");
            }
        });
    }
    public void backOnClick(View view){
        finish();
    }
}

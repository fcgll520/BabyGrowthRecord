package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by think on 2016/11/22.
 */
public class
UserInfoManageEdit extends Activity {
    private String urlStr="";   //发送请求的url
    private EditText editText;
    private TextView tvSave;
    private TextView tvTitle;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==1){
                Toast.makeText(UserInfoManageEdit.this,"宝宝信息修改成功！",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(UserInfoManageEdit.this,"宝宝信息修改失败，请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infomanage_item);
        editText =(EditText)findViewById(R.id.et_userInfo_edit);
        tvSave=(TextView)findViewById(R.id.tv_userInfo_save);
        tvTitle=(TextView)findViewById(R.id.tv_userInfoItem_title);

        Intent i=getIntent();
        urlStr=i.getStringExtra("url");
        editText.setHint(editText.getHint().toString()+i.getStringExtra("editHint"));
        tvTitle.setText("更改宝宝"+i.getStringExtra("title"));

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String babyMsg= editText.getText().toString();
                if (babyMsg.equals("")){
                    Toast.makeText(UserInfoManageEdit.this,"信息不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Message msg=new Message();
                            msg.arg1=changeMsg(babyMsg);
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private int changeMsg(String babyMsg) throws IOException {
        String url= urlStr+"&baby_msg="+babyMsg;
        HttpURLConnection connection=(HttpURLConnection)(new URL(url)).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        Log.e("changeMsg",connection.getResponseCode()+"");
        InputStream is=connection.getInputStream();
        byte[]b=new byte[1];
        is.read(b);
        return Integer.parseInt(new String(b));
    }



    public void backOnClick(View view){
        finish();
    }
}

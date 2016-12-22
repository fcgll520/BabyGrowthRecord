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
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by think on 2016/11/22.
 */
public class
 UserSettingName extends Activity {
    private EditText etName;
    private TextView tvSave;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==1){
                Toast.makeText(UserSettingName.this,"昵称修改成功！",Toast.LENGTH_SHORT).show();
                finish();
            }else if (msg.arg1==2){
                Toast.makeText(UserSettingName.this,"用户昵称已存在！",Toast.LENGTH_SHORT).show();
            }else if (msg.arg1==3){
                Toast.makeText(UserSettingName.this,"这是您原来的用户名！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(UserSettingName.this,"昵称修改失败，请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_name);
        etName=(EditText)findViewById(R.id.et_userSetting_name);
        tvSave=(TextView)findViewById(R.id.tv_userSetName_save);
        Intent i=getIntent();
        etName.setHint(i.getStringExtra("name"));
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=etName.getText().toString();
                if (name.equals("")){
                    Toast.makeText(UserSettingName.this,"昵称不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Message msg=new Message();
                            msg.arg1=0;
                            msg.arg1=changeName(name);
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private int changeName(String name) throws IOException {
        String urlStr= Utils.StrUrl+"user/editUserName?user_id="+Utils.userId+"&name="+name;
        HttpURLConnection connection=(HttpURLConnection)(new URL(urlStr)).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        Log.e("changeName",connection.getResponseCode()+"");
        InputStream is=connection.getInputStream();
        byte[]b=new byte[1];
        is.read(b);
        Log.e("changeName",new String(b));
        return Integer.parseInt(new String(b));
    }

    public void backOnClick(View view){
        finish();
    }
}

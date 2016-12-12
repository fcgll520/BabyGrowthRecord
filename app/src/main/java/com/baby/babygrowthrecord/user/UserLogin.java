package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.SharedPreferences;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baby.babygrowthrecord.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by think on 2016/11/22.
 */
public class UserLogin extends Activity {
    private EditText etName;
    private EditText etPwd;
    private Button btnLogin;
    private SharedPreferences sp;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    private void init() {
        etName=(EditText)findViewById(R.id.login_Uname_text);
        etPwd=(EditText)findViewById(R.id.login_Pwd_text);
        btnLogin=(Button)findViewById(R.id.login_login_btn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendConfirmInfo();
                        handler.sendMessage(new Message());
                    }
                }).start();
            }
        });
    }


    public void backOnClick(View view) {
        finish();
    }

    public boolean sendConfirmInfo(){
        String url="http://10.7.88.67:8788/user/confirm";
        sp=getSharedPreferences("uerInfo",MODE_WORLD_READABLE+MODE_WORLD_WRITEABLE);
        String name=null;
        String pwd=null;
//        if (sp==null){
            name=etName.getText().toString();
            pwd=etPwd.getText().toString();
//        }else {
//            name=sp.getString("name",null);
//            pwd=sp.getString("pwd",null);
//            etName.setText(name);
//            etPwd.setText(pwd);
//        }
        if (name==null||pwd==null){
            Toast.makeText(UserLogin.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            HttpURLConnection connection=(HttpURLConnection)new URL(url+"?name="+name+"&pwd="+pwd).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(100000);
            connection.connect();
            if (connection.getResponseCode()==200){
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("name",name);
                editor.putString("pwd",pwd);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void  backOnClick(){
        finish();
    }
}

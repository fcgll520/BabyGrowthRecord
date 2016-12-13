package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.os.Bundle;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by think on 2016/11/22.
 */
public class UserSettingPwd extends Activity {
    private EditText etOriginPwd;
    private EditText etPwd;
    private EditText etConfirmPwd;
    private TextView tvSave;
    private int responseCode=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_pwd);
        init();
    }

    private void init() {
        etOriginPwd=(EditText)findViewById(R.id.et_userSetting_originPwd);
        etPwd=(EditText)findViewById(R.id.et_userSetting_pwd);
        etConfirmPwd=(EditText)findViewById(R.id.et_userSetting_confirmPwd);
        tvSave=(TextView)findViewById(R.id.tv_userSetPwd_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String originPwd=etOriginPwd.getText().toString();
                String pwd=etPwd.getText().toString();
                String confirmPwd=etConfirmPwd.getText().toString();
                if (originPwd.equals("") || pwd.equals("") || confirmPwd.equals("")){
                    Toast.makeText(UserSettingPwd.this,"请补全信息！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pwd.equals(confirmPwd)){
                    Toast.makeText(UserSettingPwd.this,"密码与确认密码不同！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //修改密码
                changePwd(originPwd,pwd);
            }
        });
    }

    private void changePwd(final String origin, final String pwd) {
        //从服务器获取用户密码与原密码比较
        //发送更改用户密码消息
        //接收响应
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(UserSettingPwd.this, Utils.StrUrl+"user/getUserPwd/"+Utils.userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Log.e("SetPwd-changePwd-string",responseString);
                if (origin.equals(responseString)){
                    try {
                        HttpURLConnection connection=(HttpURLConnection)(new URL(Utils.StrUrl+"/user/editUserPwd?user_id="
                                +Utils.userId+"&pwd="+pwd)).openConnection();
                        connection.setRequestMethod("GET");
                        responseCode=connection.getResponseCode();
                        if (responseCode==200){
                            Toast.makeText(UserSettingPwd.this,"密码修改成功！",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(UserSettingPwd.this,"密码修改失败！",Toast.LENGTH_SHORT).show();
                        }
                        Log.e("SetPwd-changePwd-code",responseCode+"");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void backOnClick(View view){
        finish();
    }
}

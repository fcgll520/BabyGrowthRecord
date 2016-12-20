package com.baby.babygrowthrecord.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by think on 2016/12/15.
 */
public class RegisterConfirm extends Activity {

    private String phone;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();
        phone = i.getStringExtra("userPhone");
        String confirmCode = i.getStringExtra("confirmCode");
        pwd = i.getStringExtra("userPwd");
        String confirmPwd = i.getStringExtra("userConfirmPwd");
        if (phone.equals("")){
            Toast.makeText(RegisterConfirm.this,"手机号不能为空，请输入手机号！",Toast.LENGTH_SHORT).show();
            finish();
        } else if (pwd.equals("")){
            Toast.makeText(RegisterConfirm.this,"密码不能为空，请输入密码！",Toast.LENGTH_SHORT).show();
            finish();
        } else if (confirmPwd.equals("")){
            Toast.makeText(RegisterConfirm.this,"确认密码不能为空，请输入确认密码！",Toast.LENGTH_SHORT).show();
            finish();
        }
        //匹配验证码
        //匹配密码与确认密码
        if (!confirmPwd.equals(pwd)){
            Toast.makeText(RegisterConfirm.this,"确认密码与密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
            finish();
        }
        //发送注册用户的请求
        sendRegisterRequest();
    }

    private void sendRegisterRequest() {
        AsyncHttpClient client=new AsyncHttpClient();
        phone="58459923085";
        pwd="0978";
        client.get(RegisterConfirm.this,Utils.StrUrl+"user/register?user_phone="+phone+"&user_pwd="+pwd,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response!=null){
                    Toast.makeText(RegisterConfirm.this,"注册成功！欢迎使用成长树~",Toast.LENGTH_SHORT).show();
                    try {
                        Utils.userId=response.getInt("user_id");
                        Utils.userName=response.getString("user_name");
                        Utils.userPhoto=response.getString("user_photo");
                        Utils.userPwd=response.getString("user_pwd");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(RegisterConfirm.this, BabyMainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(RegisterConfirm.this,"注册失败，请稍后再试！",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(RegisterConfirm.this,"网络连接错误，请稍后再试！",Toast.LENGTH_SHORT).show();
                Log.e("REGISTER_ERROR",throwable.toString());
            }
        });
    }
}

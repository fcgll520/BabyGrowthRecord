package com.baby.babygrowthrecord.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/12/8.
 */
public class Register_Activity extends Activity {
    private Button register_register_btn;
    private EditText phone;
    private Button btn;
    private Context context;
    private EditText message;
    private boolean flag;
    private EditText pas;
    private EditText repas;
    public TimeCount time;
    private Button register_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        message =(EditText)findViewById(R.id.message);
        register_back=(Button)findViewById(R.id.rejister_back);
        btn = (Button)findViewById(R.id.btn);
        phone = (EditText)findViewById(R.id.phone);
        pas = (EditText)findViewById(R.id.password);
        repas = (EditText)findViewById(R.id.repassword);

        time = new TimeCount(60000, 1000);
        flag = true;

        /*点击“返回”到登陆界面*/
        register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this,Login_Activity.class);
                startActivity(intent);
            }
        });
        /*密码隐藏*/
        pas.setTransformationMethod(PasswordTransformationMethod.getInstance());
        repas.setTransformationMethod(PasswordTransformationMethod.getInstance());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 1)
                            Toast.makeText(context, "回调完成", Toast.LENGTH_SHORT).show();
                        else if (msg.what == 2)
                            Toast.makeText(context, "提交验证码成功", Toast.LENGTH_SHORT).show();
                        else if (msg.what == 3)
                            Toast.makeText(context, "获取验证码成功", Toast.LENGTH_SHORT).show();
                        else if (msg.what == 4)
                            Toast.makeText(context, "返回支持发送国家验证码", Toast.LENGTH_SHORT).show();
                    }
                };
                EventHandler eh = new EventHandler() {
                    @Override
                    public void afterEvent(int event, int result, Object data) {

                        if (result == SMSSDK.RESULT_COMPLETE) {
                            //回调完成
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                //提交验证码成功
                            }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                                //获取验证码成功
                            }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                                //返回支持发送验证码的国家列表
                            }
                        }else{
                            ((Throwable)data).printStackTrace();
                        }
                    }
                };
                SMSSDK.initSDK(Register_Activity.this,"19b8735a28d76","4451d3257d069c4cd342b225a7b8f2fe");
                SMSSDK.registerEventHandler(eh);
                SMSSDK.getVerificationCode("86", phone.getText().toString());//请求获取短信验证码
                time.start();
            }
        });

        register_register_btn=(Button)findViewById(R.id.register_register_btn);
        register_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerConfirm();
            }
        });


    }
    /*内部类穿件timacount类*/
    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn.setBackgroundColor(Color.parseColor("#B6B6D8"));
            btn.setClickable(false);
            btn.setText("("+millisUntilFinished / 1000 +") 秒后可重新发送");

        }

        @Override
        public void onFinish() {
            btn.setText("重新获取验证码");
            btn.setClickable(true);
            btn.setBackgroundColor(Color.parseColor("#4EB84A"));

        }
    }

    public void registerConfirm(){
        String phoneStr=phone.getText().toString();
        String pwd=pas.getText().toString();
        String confirmPwd=repas.getText().toString();
        if (phoneStr.equals("")){
            Toast.makeText(Register_Activity.this,"手机号不能为空，请输入手机号！",Toast.LENGTH_SHORT).show();
            finish();
        } else if (pwd.equals("")){
            Toast.makeText(Register_Activity.this,"密码不能为空，请输入密码！",Toast.LENGTH_SHORT).show();
            finish();
        } else if (confirmPwd.equals("")){
            Toast.makeText(Register_Activity.this,"确认密码不能为空，请输入确认密码！",Toast.LENGTH_SHORT).show();
            finish();
        }
        //匹配验证码
        SMSSDK.submitVerificationCode("86", phone.getText().toString(), message.getText().toString());
        //匹配密码与确认密码
        if (!confirmPwd.equals(pwd)){
            Toast.makeText(Register_Activity.this,"确认密码与密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
            finish();
        }
        //发送注册用户的请求
        sendRegisterRequest(phoneStr,pwd);
    }

    private void sendRegisterRequest(String phoneStr,String pwd) {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(Register_Activity.this, Utils.StrUrl+"user/register?user_phone="+phoneStr+"&user_pwd="+pwd,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response!=null){
                    Toast.makeText(Register_Activity.this,"注册成功！欢迎使用成长树~",Toast.LENGTH_SHORT).show();
                    try {
                        Utils.userId=response.getInt("user_id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(Register_Activity.this, BabyMainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Register_Activity.this,"注册失败，请稍后再试！",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Register_Activity.this,"网络连接错误，请稍后再试！",Toast.LENGTH_SHORT).show();
                Log.e("REGISTER_ERROR",throwable.toString());
            }
        });
    }
}



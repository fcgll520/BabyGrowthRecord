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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.baby.babygrowthrecord.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        message =(EditText)findViewById(R.id.message);
        btn = (Button)findViewById(R.id.btn);
        phone = (EditText)findViewById(R.id.phone);
        pas = (EditText)findViewById(R.id.password);
        repas = (EditText)findViewById(R.id.repassword);
        time = new TimeCount(60000, 1000);
        flag = true;
      /*  密码隐藏*/
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
                SMSSDK.submitVerificationCode("86", phone.getText().toString(), message.getText().toString());
                Intent intent = new Intent(Register_Activity.this,BabyMainActivity.class);
                startActivity(intent);
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
}



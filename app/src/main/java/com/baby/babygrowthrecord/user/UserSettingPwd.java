package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * Created by think on 2016/11/22.
 */
public class UserSettingPwd extends Activity {
    private EditText etOriginPwd;
    private EditText etPwd;
    private EditText etConfirmPwd;
    private TextView tvSave;
    private Button btnSave;
    private Button btnCancel;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==1){
                Toast.makeText(UserSettingPwd.this,"密码修改成功！",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(UserSettingPwd.this,"密码修改失败,请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private View.OnClickListener myListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_userSetting_pwdSave:
                    String originPwd=etOriginPwd.getText().toString();
                    String pwd=etPwd.getText().toString();
                    String confirmPwd=etConfirmPwd.getText().toString();
                    if (originPwd.equals("") || pwd.equals("") || confirmPwd.equals("")){
                        Toast.makeText(UserSettingPwd.this,"请补全信息！",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!confirmPwd.equals(pwd)){
                        Toast.makeText(UserSettingPwd.this,"确认密码与密码不同！",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //修改密码
                    confirmPwd(originPwd,pwd);
                    break;
                case R.id.btn_userSetting_pwdCancel:
                    Toast.makeText(UserSettingPwd.this,"取消修改密码！",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
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
//        tvSave=(TextView)findViewById(R.id.tv_userSetPwd_save);
        btnSave=(Button) findViewById(R.id.btn_userSetting_pwdSave);
        btnCancel=(Button)findViewById(R.id.btn_userSetting_pwdCancel);
//        tvSave.setOnClickListener(myListener);
        btnSave.setOnClickListener(myListener);
        btnCancel.setOnClickListener(myListener);

        //设置隐藏密码框
        etOriginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etConfirmPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
       // etConfirmPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    //确认原密码是否正确
    private void confirmPwd(final String origin, final String pwd) {
        //从服务器获取用户密码与原密码比较
        //发送更改用户密码消息
        //接收响应
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(UserSettingPwd.this, Utils.StrUrl+"user/getUserPwd/"+Utils.userId, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(UserSettingPwd.this,"网络连接失败，密码修改失败！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.e("SetPwd-changePwd-Text",s);
                if (origin.equals(s)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg=new Message();
                            msg.arg1=changePwd(pwd);
                            handler.sendMessage(msg);
                        }
                    }).start();
                }else {
                    Toast.makeText(UserSettingPwd.this,"原密码输入错误，请重新输入！",Toast.LENGTH_SHORT).show();
                    etOriginPwd.setText("");
                }
            }
        });
    }

    //修改密码
    public int changePwd(String pwd){
        try {
            Log.e("changePwd",pwd);
            HttpURLConnection connection=(HttpURLConnection)(new URL(Utils.StrUrl+"user/editUserPwd?user_id="
                    +Utils.userId+"&pwd="+pwd)).openConnection();
            connection.setRequestMethod("GET");
            InputStream is=connection.getInputStream();
            byte[]b=new byte[1];
            is.read(b);
            int i=Integer.parseInt(new String(b));
            return i;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void backOnClick(View view){
        finish();
    }
}

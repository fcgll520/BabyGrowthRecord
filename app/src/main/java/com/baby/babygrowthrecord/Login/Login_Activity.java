package com.baby.babygrowthrecord.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.baby.babygrowthrecord.R;
import com.baby.babygrowthrecord.util.Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login_Activity extends Activity {
    private static final String TAG = Login_Activity.class.getName();
    public static String mAppid;
    private Button mNewLoginButton;
    private TextView mUserInfo;
    private de.hdodenhof.circleimageview.CircleImageView mUserLogo;
    public static QQAuth mQQAuth;
    private UserInfo mInfo;
    private Tencent mTencent;
    private final String APP_ID = "1105869088";// 测试时使用，真正发布的时候要换成自己的APP_ID
    private Button login_btn;
    private Button login_login_register;
    private EditText login_Pwd_text;
    private EditText login_Uname_text;
    public  String loginUname;
    public  String loginPwd;
    public int userID;
    /*
    * 自动登陆
    */
    SharedPreferences sharedPreferences=null;
    SharedPreferences.Editor editor=null;
    Context context=null;

    private String PREFERENCES_PACKAGE="com.baby.babygrowthrecord";
    private String PREFERENCES_NAME="UserInfo";
    private int MODE=Context.MODE_WORLD_READABLE+ Context.MODE_WORLD_WRITEABLE;

    public void initSharedPreferences(){
        if (context==null){
            try {
                context=this.createPackageContext(PREFERENCES_PACKAGE,Context.CONTEXT_IGNORE_SECURITY);
                sharedPreferences=context.getSharedPreferences(PREFERENCES_NAME,MODE);
                editor=sharedPreferences.edit();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //自动登陆
    public void autoLogin(){
        if (sharedPreferences==null)
            initSharedPreferences();
        String name="";
        String pwd="";
        if (sharedPreferences!=null){
            if (sharedPreferences.getInt("user_id",0)==-2){
                Toast.makeText(Login_Activity.this,"自动登录出错，请再次登录！",Toast.LENGTH_SHORT).show();
                return;
            }
            name=sharedPreferences.getString("user_name","");
            pwd=sharedPreferences.getString("user_pwd","");
            Login_Activity login=new Login_Activity();
            login.getLoginMessage(name,pwd);
        }else {
            Toast.makeText(Login_Activity.this,"自动登录出错，请再次登录！",Toast.LENGTH_SHORT).show();
        }
        if (name.equals("") || pwd.equals("")){
            Toast.makeText(Login_Activity.this,"自动登录出错，请再次登录！",Toast.LENGTH_SHORT).show();
        }
    }

    //记住用户名和密码
    public void rememberUserInfo(String name, String pwd){
        if (sharedPreferences==null)
            initSharedPreferences();

        if (editor!=null){
            editor.putString("user_name",name);
            editor.putString("user_pwd",pwd);
            Log.e("REMEMBER_SUCCESS",name+"_"+pwd);
        }else {
            Log.e("REMEMBER_ERROR","editor=null");
        }
    }
    //退出登录时设置editor中的user_id
    public void setUserId(int id){
        editor.putInt("user_id",id);
    }
    public int getShareUserId(){
        return sharedPreferences.getInt("user_id",-1);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPreferences();
        //自动登陆
        if (getShareUserId()==0){
            autoLogin();
        }

        Log.d(TAG, "-->onCreate");
        // 固定竖屏
        setContentView(R.layout.activity_login);
        initViews();

        /* 控件获取*/
        login_Pwd_text = (EditText) findViewById(R.id.login_Pwd_text);
        login_btn = (Button) findViewById(R.id.login_login_btn);
        login_Uname_text = (EditText) findViewById(R.id.login_Uname_text);

        /*设置密码隐藏*/
        login_Pwd_text.setTransformationMethod(PasswordTransformationMethod.getInstance());

        /*设置焦点改变时设置hint为空*/
        //login_Uname_text.setOnFocusChangeListener(mOnFocusChangeListener);
       // login_Pwd_text.setOnFocusChangeListener(mOnFocusChangeListener);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*验证用户和密码是否存储在数据库中*/

                //获取用户输入的用户名和密码
                loginUname=login_Uname_text.getText().toString();
                loginPwd=login_Pwd_text.getText().toString();
                Log.e("用户名：",loginUname);
                Log.e("密码：",loginPwd);

                //网络请求验证用户名和密码
                getLoginMessage(loginUname,loginPwd);

//                Utils.flag = 5;
//                Intent intent = new Intent(Login_Activity.this, BabyMainActivity.class);
//                startActivity(intent);
//                Toast.makeText(Login_Activity.this,"登录成功",Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

        login_login_register = (Button) findViewById(R.id.login_login_register);
        login_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);
            }
        });
    }

    //网络请求验证用户名和密码
    public  void getLoginMessage(final String loginUname, final String loginPwd) {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(Login_Activity.this,Utils.StrUrl+"user/confirmLogin?userName="+loginUname+"&userPwd="+loginPwd,new TextHttpResponseHandler(){
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.e("网络有问题","请检查网络");
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.e("返回的s值",s);
                if (Integer.parseInt(s)==-1){
                    Log.e("服务器返回数字-1",s);
                    Toast.makeText(Login_Activity.this,"用户不存在,请重新输入",Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(s)==0){
                    Log.e("服务器返回数字0",s);
                    Toast.makeText(Login_Activity.this,"密码输入错误,请重新输入",Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(s)==1){
                    Log.e("服务器返回数字1",s);
                    Toast.makeText(Login_Activity.this,"登录成功",Toast.LENGTH_SHORT).show();

                    rememberUserInfo(loginUname,loginPwd);

                    //得到用户ID
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getUserId();
                        }
                    }).start();
                }
            }
        });
    }

    private void getUserId() {
        Log.e("调用了此函数","调用了此函数");
        try {
            String url=Utils.StrUrl+"user/getuserid";
            URL Url=new URL(url);
            HttpURLConnection coon= (HttpURLConnection) Url.openConnection();
            InputStream is=coon.getInputStream();
            byte []b=new byte[1];
            is.read(b);
//            String str=String.valueOf(b);
//            int id=Integer.parseInt(str);
//            Log.e("服务器返回的用户ID", String.valueOf(id));
            Utils.userId=Integer.parseInt(new String(b));
            Log.e("服务器返回的用户ID", String.valueOf(Utils.userId));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //登录成功，跳转到主页面
        Utils.flag=1;
        Intent intent = new Intent(Login_Activity.this, BabyMainActivity.class);
        startActivity(intent);

        finish();
    }



    @Override
    protected void onStart() {
        Log.d(TAG, "-->onStart");
        final Context context = Login_Activity.this;
        final Context ctxContext = context.getApplicationContext();
        mAppid = APP_ID;
        mQQAuth = QQAuth.createInstance(mAppid, ctxContext);
        mTencent = Tencent.createInstance(mAppid, Login_Activity.this);
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "-->onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "-->onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "-->onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "-->onDestroy");
        super.onDestroy();
    }

    private void initViews() {
        mNewLoginButton = (Button) findViewById(R.id.qq);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_container);
        OnClickListener listener = new NewClickListener();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof Button) {
                Log.e("test", "test");
                view.setOnClickListener(listener);
            }
        }
        mUserInfo = (TextView) findViewById(R.id.user_nickname);
        mUserLogo = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.user_logo);
        /*updateLoginButton();*/
    }

  /*  private void updateLoginButton() {
        if (mQQAuth != null && mQQAuth.isSessionValid()) {
            mNewLoginButton.setTextColor(Color.RED);
            mNewLoginButton.setText("1");
        } else {
            mNewLoginButton.setTextColor(Color.BLUE);
            mNewLoginButton.setText("2");
        }
    }*/

    private void updateUserInfo() {
        if (mQQAuth != null && mQQAuth.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread() {

                        @Override
                        public void run() {
                            JSONObject json = (JSONObject) response;
                            if (json.has("figureurl")) {
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Util.getbitmap(json
                                            .getString("figureurl_qq_2"));
                                } catch (JSONException e) {

                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }

                    }.start();
                }

                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mQQAuth.getQQToken());
            mInfo.getUserInfo(listener);

        } else {
            /*mUserInfo.setText("");*/
            mUserInfo.setVisibility(android.view.View.GONE);
            mUserLogo.setVisibility(android.view.View.GONE);
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        mUserInfo.setVisibility(View.GONE);
                        mUserInfo.setText(response.getString("nickname"));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 1) {
                Bitmap bitmap = (Bitmap) msg.obj;
                mUserLogo.setImageBitmap(bitmap);
                mUserLogo.setVisibility(View.GONE);
            }
        }

    };

    private void onClickLogin() {
        if (!mQQAuth.isSessionValid()) {
            IUiListener listener = new BaseUiListener() {
                @Override
                protected void doComplete(JSONObject values) {
                    updateUserInfo();
                    //跳转到个人中心界面
                    Utils.flag = 5;
                    Intent intent = new Intent(Login_Activity.this, BabyMainActivity.class);
                    startActivity(intent);
                  /*  updateLoginButton();*/
                }
            };
            mQQAuth.login(this, "all", listener);
            // mTencent.loginWithOEM(this, "all",
            // listener,"10000144","10000144","xxxx");
            mTencent.login(this, "all", listener);
        } else {
            mQQAuth.logout(this);
            updateUserInfo();
            /*updateLoginButton();*/
        }
    }

    public static boolean ready(Context context) {
        if (mQQAuth == null) {
            return false;
        }
        boolean ready = mQQAuth.isSessionValid()
                && mQQAuth.getQQToken().getOpenId() != null;
        if (!ready)
            Toast.makeText(context, "activity_login and get openId first, please!",
                    Toast.LENGTH_SHORT).show();
        return ready;
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            //返回的json字符
            Util.showResultDialog(Login_Activity.this, response.toString(),
                    "登录成功");
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(Login_Activity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(Login_Activity.this, "onCancel: ");
            Util.dismissDialog();
        }
    }


    class NewClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Class<?> cls = null;
            switch (v.getId()) {
                case R.id.qq:
                    onClickLogin();
                    return;
            }
            if (cls != null) {
                Intent intent = new Intent(context, cls);
                context.startActivity(intent);
            }
        }
    }

    /* 自动获取焦点时隐藏hint代码*/
    private View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText textView = (EditText) v;
            String hint;
            if (hasFocus) {
                hint = textView.getHint().toString();
                textView.setTag(hint);
                textView.setHint(null);
            } else {
                hint = textView.getTag().toString();
                textView.setHint(hint);
            }
        }
    };
}
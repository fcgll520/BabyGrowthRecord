package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Circle.Circle;
import com.baby.babygrowthrecord.Fragment.GrowthFragment;
import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.Login.Login_Activity;
import com.baby.babygrowthrecord.R;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by think on 2016/11/22.
 */
public class UserSetting extends Activity {
    private RelativeLayout rvHeadPic;
    private RelativeLayout rvName;
    private RelativeLayout rvPwd;
    private TextView tvHeadPic;
    private CircleImageView ivHeadPic;
    private TextView tvName;
    private TextView tvUname;
    private TextView tvPwd;
    private Button btnExist;
    private View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent();
            switch (v.getId()){
                case R.id.rv_userSetting_pic:
                    i.setClass(UserSetting.this,UserSettingHeadPic.class);
                    startActivity(i);
                    break;
                case R.id.rv_userSetting_name:
                    i.setClass(UserSetting.this,UserSettingName.class);
                    i.putExtra("name",tvUname.getText());
                    startActivity(i);
                    break;
                case R.id.rv_userSetting_pwd:
                    i.setClass(UserSetting.this,UserSettingPwd.class);
                    startActivity(i);
                    break;
                case R.id.tv_userSetting_pic:
                    i.setClass(UserSetting.this,UserSettingHeadPic.class);
                    startActivity(i);
                    break;
                case R.id.tv_userSetting_name:
                    i.setClass(UserSetting.this,UserSettingName.class);
                    i.putExtra("name",tvUname.getText());
                    startActivity(i);
                    break;
                case R.id.tv_userSetting_pwd:
                    i.setClass(UserSetting.this,UserSettingPwd.class);
                    startActivity(i);
                    break;
                case R.id.btn_userSetting_exist:
                    existLogin();
                    break;
                default:
                    break;
            }
        }
    };

    //退出登录
    private void existLogin() {
        //
        Login_Activity login=new Login_Activity();
        SharedPreferences sharedPreferences=login.initSharedPreferences(UserSetting.this);
        sharedPreferences.edit().putString("isAutoLogin","false").commit();
        //
        Utils.userId=-1;
        Toast.makeText(UserSetting.this,"退出登录成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        init();
    }

    private void init() {
        rvHeadPic=(RelativeLayout)findViewById(R.id.rv_userSetting_pic);
        rvName=(RelativeLayout)findViewById(R.id.rv_userSetting_name);
        rvPwd=(RelativeLayout)findViewById(R.id.rv_userSetting_pwd);
        tvHeadPic=(TextView)findViewById(R.id.tv_userSetting_pic);
        ivHeadPic=(CircleImageView)findViewById(R.id.iv_userSet_headPic);
        tvName=(TextView)findViewById(R.id.tv_userSetting_name);
        tvUname=(TextView)findViewById(R.id.tv_userSetting_uName);
        tvPwd=(TextView)findViewById(R.id.tv_userSetting_pwd);
        btnExist=(Button)findViewById(R.id.btn_userSetting_exist);

        rvHeadPic.setOnClickListener(myClickListener);
        rvName.setOnClickListener(myClickListener);
        rvPwd.setOnClickListener(myClickListener);
        tvHeadPic.setOnClickListener(myClickListener);
        tvName.setOnClickListener(myClickListener);
        tvPwd.setOnClickListener(myClickListener);
        btnExist.setOnClickListener(myClickListener);

        //获取用户名和头像
        getUserInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    public void getUserInfo(){
        GrowthFragment g=new GrowthFragment();
        g.getUserInfo(ivHeadPic,tvUname);
    }

    public void backOnClick(View view){
        finish();
    }
}

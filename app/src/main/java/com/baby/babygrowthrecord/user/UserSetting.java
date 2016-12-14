package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baby.babygrowthrecord.Circle.Circle;
import com.baby.babygrowthrecord.Fragment.GrowthFragment;
import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent();
            switch (v.getId()){
                case R.id.rv_userSetting_pic:
                    i.setClass(UserSetting.this, UserSettingHeadPic.class);
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
                    i.setClass(UserSetting.this, UserSettingHeadPic.class);
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
                default:
                    break;
            }
        }
    };
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

        rvHeadPic.setOnClickListener(myClickListener);
        rvName.setOnClickListener(myClickListener);
        rvPwd.setOnClickListener(myClickListener);
        tvHeadPic.setOnClickListener(myClickListener);
        tvName.setOnClickListener(myClickListener);
        tvPwd.setOnClickListener(myClickListener);

        //获取用户名和头像
        GrowthFragment g=new GrowthFragment();
        g.getUserInfo(ivHeadPic,tvUname);
    }
    public void backOnClick(View view){
        finish();
    }
}

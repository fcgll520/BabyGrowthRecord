package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baby.babygrowthrecord.R;


/**
 * Created by think on 2016/11/22.
 */
public class UserSetting extends Activity {
    private TextView tvHeadPic;
    private TextView tvName;
    private TextView tvUname;
    private TextView tvPwd;
    private View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent();
            switch (v.getId()){
                case R.id.tv_userSetting_pic:
                    i.setClass(UserSetting.this, UserSettingHeadPic.class);
                    startActivity(i);
                    break;
                case R.id.tv_userSetting_name:
                    i.setClass(UserSetting.this,UserSettingName.class);
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
        tvHeadPic=(TextView)findViewById(R.id.tv_userSetting_pic);
        tvName=(TextView)findViewById(R.id.tv_userSetting_name);
        tvUname=(TextView)findViewById(R.id.tv_userSetting_uName);
        tvPwd=(TextView)findViewById(R.id.tv_userSetting_pwd);

        tvHeadPic.setOnClickListener(myClickListener);
        tvName.setOnClickListener(myClickListener);
        tvPwd.setOnClickListener(myClickListener);
    }
}

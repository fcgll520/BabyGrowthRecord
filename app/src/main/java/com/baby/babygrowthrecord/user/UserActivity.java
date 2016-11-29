package com.baby.babygrowthrecord.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baby.babygrowthrecord.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    private CircleImageView ivHeadPic;
    private TextView tvUname;
    private TextView tvBabyAge;

    private RelativeLayout rlAlbum;
    private RelativeLayout rlInfoManage;
    private RelativeLayout rlCollection;
    private RelativeLayout rlSetting;

    private TextView tvAlbum;
    private TextView tvInfoManage;
    private TextView tvCollection;
    private TextView tvSetting;
    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent();
            switch (v.getId()){
                case R.id.img_circlePic:
                    i.setClass(UserActivity.this, UserSettingHeadPic.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_uName:
                    i.setClass(UserActivity.this, UserSettingName.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_babyAge:
                    i.setClass(UserActivity.this, UserInfoManage.class);
                    startActivity(i);
                    break;
                case R.id.rl_user_album:
                    i.setClass(UserActivity.this,UserAlbum.class);
                    startActivity(i);
                    break;
                case R.id.rl_user_infoMange:
                    i.setClass(UserActivity.this,UserInfoManage.class);
                    startActivity(i);
                    break;
                case R.id.rl_user_collect:
                    i.setClass(UserActivity.this, UserCollection.class);
                    startActivity(i);
                    break;
                case R.id.rl_user_setting:
                    i.setClass(UserActivity.this,UserSetting.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_album:
                    i.setClass(UserActivity.this,UserAlbum.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_infoMange:
                    i.setClass(UserActivity.this,UserInfoManage.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_collect:
                    i.setClass(UserActivity.this, UserCollection.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_setting:
                    i.setClass(UserActivity.this,UserSetting.class);
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user);
        init();

    }
    private void init(){
        ivHeadPic = (CircleImageView) findViewById(R.id.img_circlePic);
        tvUname = (TextView) findViewById(R.id.tv_user_uName);
        tvBabyAge = (TextView) findViewById(R.id.tv_user_babyAge);

        rlAlbum=(RelativeLayout)findViewById(R.id.rl_user_album);
        rlInfoManage=(RelativeLayout)findViewById(R.id.rl_user_infoMange);
        rlCollection=(RelativeLayout)findViewById(R.id.rl_user_collect);
        rlSetting=(RelativeLayout)findViewById(R.id.rl_user_setting);

        tvAlbum=(TextView)findViewById(R.id.tv_user_album);
        tvInfoManage =(TextView)findViewById(R.id.tv_user_infoMange);
        tvCollection=(TextView)findViewById(R.id.tv_user_collect);
        tvSetting=(TextView)findViewById(R.id.tv_user_setting);

        //绑定监听器
        ivHeadPic.setOnClickListener(myClickListener);
        tvUname.setOnClickListener(myClickListener);
        tvBabyAge.setOnClickListener(myClickListener);

        rlAlbum.setOnClickListener(myClickListener);
        rlInfoManage.setOnClickListener(myClickListener);
        rlCollection.setOnClickListener(myClickListener);
        rlSetting.setOnClickListener(myClickListener);

        tvAlbum.setOnClickListener(myClickListener);
        tvInfoManage.setOnClickListener(myClickListener);
        tvCollection.setOnClickListener(myClickListener);
        tvSetting.setOnClickListener(myClickListener);
    }
}

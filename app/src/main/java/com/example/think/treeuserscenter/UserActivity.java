package com.example.think.treeuserscenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    private TextView tvAlbum;
    private TextView tvInfoManage;
    private TextView tvCollection;
    private TextView tvSetting;
    private View.OnClickListener myTextClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent();
            switch (v.getId()){
                case R.id.tv_user_album:
                    i.setClass(UserActivity.this,UserAlbum.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_infoMange:
                    i.setClass(UserActivity.this,UserInfoManage.class);
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
        tvAlbum=(TextView)findViewById(R.id.tv_user_album);
        tvInfoManage =(TextView)findViewById(R.id.tv_user_infoMange);
        tvCollection=(TextView)findViewById(R.id.tv_user_collect);
        tvSetting=(TextView)findViewById(R.id.tv_user_setting);

        tvAlbum.setOnClickListener(myTextClickListener);
        tvInfoManage.setOnClickListener(myTextClickListener);
        tvCollection.setOnClickListener(myTextClickListener);
        tvSetting.setOnClickListener(myTextClickListener);
    }
}

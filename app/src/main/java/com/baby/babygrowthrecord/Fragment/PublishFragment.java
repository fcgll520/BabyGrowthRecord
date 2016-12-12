package com.baby.babygrowthrecord.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.baby.babygrowthrecord.R;

/**
 * Created by asus on 2016/11/22.
 */
public class PublishFragment extends AppCompatActivity{
    private ImageView composer_btn,composer_btn1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //1. 获取界面的控件
        getViews();

        //2. 注册事件监听器
        setListener();
    }
    //获取界面的控件
    private void getViews(){
        composer_btn = (ImageView)findViewById(R.id.composer_btn);
        composer_btn1 = (ImageView)findViewById(R.id.composer_btn1);

    }
    //注册事件监听器
    private void setListener(){
        MyListener listener = new MyListener();
        composer_btn.setOnClickListener(listener);
        composer_btn1.setOnClickListener(listener);

    }
    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.composer_btn:
                    Intent intent = new Intent(PublishFragment.this,PublishActivity.class);
                    startActivity(intent);
                    break;
                case R.id.composer_btn1:
                    Intent intent1 = new Intent(PublishFragment.this,PublishActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }
    public void backOnClick(View view){
        finish();
    }
}

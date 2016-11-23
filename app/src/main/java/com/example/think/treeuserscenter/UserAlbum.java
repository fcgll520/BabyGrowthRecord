package com.example.think.treeuserscenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by think on 2016/11/22.
 */
public class UserAlbum extends Activity {
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private int[]imgList={R.raw.raw_1478764276,R.raw.raw_1478765147,R.raw.raw_1478766470,R.raw.raw_1478766479,
            R.raw.raw_1479174901,R.raw.raw_1479174922};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_album);
        init();
    }

    private void init() {
//        ll_1=(LinearLayout)findViewById(R.id.ll_userAlbum_1);
//        ll_2=(LinearLayout)findViewById(R.id.ll_userAlbum_2);
//
//        for (int i=0;i<imgList.length;i++){
//            ImageView pic=new ImageView(this);
//            pic.setImageResource(imgList[i]);
//            if (i%2==0){
//                ll_1.addView(pic);
//            }else {
//                ll_2.addView(pic);
//            }
//        }
        ImageView iv=(ImageView)findViewById(R.id.iv_userAlbum_example);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserAlbum.this,UserAlbumDetail.class);
                startActivity(i);
            }
        });
    }
}

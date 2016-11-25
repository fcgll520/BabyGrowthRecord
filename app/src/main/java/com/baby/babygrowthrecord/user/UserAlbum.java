package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baby.babygrowthrecord.R;

/**
 * Created by think on 2016/11/22.
 */
public class UserAlbum extends Activity {
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private LinearLayout ll_3;
    private int[] imgList = {R.raw.raw_1478764276, R.raw.raw_1478766470, R.raw.raw_1478765147, R.raw.raw_1478766479,
            R.raw.raw_1479174901, R.raw.raw_1479174922, R.raw.raw_1479174957, R.raw.raw_1479177109};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_album);
        init();
    }

    private void init() {
        ll_1 = (LinearLayout) findViewById(R.id.ll_userAlbum_1);
        ll_2 = (LinearLayout) findViewById(R.id.ll_userAlbum_2);
        ll_3 = (LinearLayout)findViewById(R.id.ll_userAlbum_3);
        WindowManager wm=getWindowManager();
        int screenWidth=wm.getDefaultDisplay().getWidth();
        int width=screenWidth/2;
        for (int i = 0; i < imgList.length; i++) {
            ImageView pic = new ImageView(this);
            pic.setImageResource(imgList[i]);
            int height = (int) (200 + Math.random() * 400);
            LinearLayout.LayoutParams imageParam = new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            pic.setLayoutParams(imageParam);
            Log.i("picH:",""+pic.getHeight());
            pic.setPadding(0, 2, 0, 2);
            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(UserAlbum.this, UserAlbumDetail.class);
                    startActivity(i);
                }
            });
            if (i % 3 == 0) {
                ll_1.addView(pic);
            } else if (i%3==1){
                ll_2.addView(pic);
            }else {
                ll_3.addView(pic);
            }
        }
    }
}

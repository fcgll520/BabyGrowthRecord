package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baby.babygrowthrecord.R;

/**
 * Created by think on 2016/11/22.
 */
public class UserAlbumDetail extends Activity {
    private ImageView ivPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_album_detail);
        init();
    }

    private void init() {
        ivPic=(ImageView)findViewById(R.id.iv_userAlbum_detail);
        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserAlbumDetail.this,UserAlbumBigImg.class);
                startActivity(i);
            }
        });
    }

}

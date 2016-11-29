package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baby.babygrowthrecord.R;

/**
 * Created by think on 2016/11/22.
 */
public class UserAlbumBigImg extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_album_bigimg);
        ImageView ivBig=(ImageView)findViewById(R.id.iv_user_bigImg);
        ivBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baby.babygrowthrecord.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by think on 2016/11/22.
 */
public class UserAlbumBigImg extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_album_bigimg);
        ImageView ivBig=(ImageView)findViewById(R.id.iv_user_bigImg);
        String imgUrl = getIntent().getStringExtra("imgUrl");
        if (imgUrl !=null || imgUrl.equals("")){
            ImageLoader.getInstance().displayImage(imgUrl,ivBig);
        };
        ivBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

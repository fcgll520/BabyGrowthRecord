package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by think on 2016/11/22.
 */
public class UserSettingHeadPic extends Activity {
    private ImageView ivHeadPic;
    private Gallery gallery;
    private TextView tvSave;
    private UserSettingHeadPicAdapter adapter;
    private String[]images={Utils.StrUrl+"img/user_photo"+0+".jpg",Utils.StrUrl+"img/user_photo"+1+".jpg",
            Utils.StrUrl+"img/user_photo"+2+".jpg"};
    private int[]imgArray={R.raw.user_photo0,R.raw.user_photo1,R.raw.user_photo2,R.raw.user_photo3,R.raw.user_photo4,
            R.raw.user_photo5,R.raw.user_photo6,R.raw.user_photo7,R.raw.user_photo8,R.raw.user_photo9};
    private int imgId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_headpic);
        init();

    }

    private void init() {
        ivHeadPic=(ImageView)findViewById(R.id.iv_userSetHeadPic);
        tvSave=(TextView)findViewById(R.id.tv_userSetHeadPic_save);

        gallery=(Gallery)findViewById(R.id.gal_userSetHeadPic);
        setBigImg();
      //  adapter=new UserSettingHeadPicAdapter(UserSettingHeadPic.this,images);
        adapter=new UserSettingHeadPicAdapter(UserSettingHeadPic.this,imgArray);
        gallery.setAdapter(adapter);

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imgId=position;
                setBigImg();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHeadPic();
            }
        });

    }

    private void changeHeadPic() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(UserSettingHeadPic.this, Utils.StrUrl + "user/editUserHeadPic?user_id="
                + Utils.userId + "&user_photo=" + imgId, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(UserSettingHeadPic.this,"网络连接失败，请稍后再试！",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (s.equals("success")){
                    Toast.makeText(UserSettingHeadPic.this,"更改头像成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(UserSettingHeadPic.this,"更改头像失败，请稍后再试！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setBigImg() {
//        ImageLoader.getInstance().displayImage(images[imgId],ivHeadPic);
        ivHeadPic.setImageResource(imgArray[imgId]);
    }


    public void backOnClick(View view){
        finish();
    }
}

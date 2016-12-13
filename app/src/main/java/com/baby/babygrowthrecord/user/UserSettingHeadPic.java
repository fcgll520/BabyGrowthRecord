package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baby.babygrowthrecord.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by think on 2016/11/22.
 */
public class UserSettingHeadPic extends Activity {
    private ImageView ivHeadPic;
    private Gallery gallery;
//    private HorizontalScrollView hs;
//    private LinearLayout llHeadPic;

    private UserSettingHeadPicAdapter adapter;
    private int[]images={R.raw.raw_01,R.raw.raw_1478764276,R.raw.raw_1478765147,R.raw.raw_1478766470,R.raw.raw_1478766479,
            R.raw.raw_1479174901,R.raw.raw_1479174922,R.raw.raw_1479174957,R.raw.raw_1479177109};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_headpic);
        init();
    }

    private void init() {
        ivHeadPic=(ImageView)findViewById(R.id.iv_userSetHeadPic);
//        hs=(HorizontalScrollView)findViewById(R.id.hs_userSetHeadPic);
//        llHeadPic=(LinearLayout)findViewById(R.id.ll_userSetHeadPic);
//        for (int i=0;i<7;i++){
//            ImageView img=new ImageView(UserSettingHeadPic.this);
//            img.setImageResource(images[i]);
//            llHeadPic.addView(img,i,new HorizontalScrollView.LayoutParams(80,
//                    ViewGroup.LayoutParams.MATCH_PARENT));
//            img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ivHeadPic.setImageResource(v.getId());
//                }
//            });
//        }

        gallery=(Gallery)findViewById(R.id.gal_userSetHeadPic);
        ivHeadPic.setImageResource(images[0]);
        adapter=new UserSettingHeadPicAdapter(UserSettingHeadPic.this,images);
        gallery.setAdapter(adapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ivHeadPic.setImageResource(images[position]);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_setting_change_headpic,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_headPic_photograph:
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                long time = Calendar.getInstance().getTimeInMillis();
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/tucue" + time + ".jpg")));
                startActivityForResult(i, 0);
                break;
            case R.id.item_headPic_camera:
                Intent getImage=new Intent(Intent.ACTION_GET_CONTENT);
                getImage.addCategory(Intent.CATEGORY_OPENABLE);
                getImage.setType("image/*");
                startActivityForResult(getImage, 0);
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    public void backOnClick(View view){
        finish();
    }
}

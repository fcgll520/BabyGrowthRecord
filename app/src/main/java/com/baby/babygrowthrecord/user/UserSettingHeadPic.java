package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baby.babygrowthrecord.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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

    private String imgFileName="";
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_headpic);
        init();
    }

    private void init() {
        ivHeadPic=(ImageView)findViewById(R.id.iv_userSetHeadPic);
        popupWindow=new PopupWindow(LayoutInflater.from(UserSettingHeadPic.this)
                .inflate(R.menu.user_setting_change_headpic,null));
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

//        gallery=(Gallery)findViewById(R.id.gal_userSetHeadPic);
//        ivHeadPic.setImageResource(images[0]);
//        adapter=new UserSettingHeadPicAdapter(UserSettingHeadPic.this,images);
//        gallery.setAdapter(adapter);
//        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ivHeadPic.setImageResource(images[position]);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_setting_change_headpic,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            //拍照
            case R.id.item_headPic_photograph:
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                long time = Calendar.getInstance().getTimeInMillis();
//                if (Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED){  //sd卡是否可用
                    imgFileName=Environment.getExternalStorageDirectory().getAbsolutePath() + "/baby_record" + time + ".jpg";
                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgFileName)));
                    startActivityForResult(i, 0);
//                }else {
//                    Toast.makeText(UserSettingHeadPic.this,"无可用sd卡,更改头像失败！",Toast.LENGTH_SHORT).show();
//                }
                break;
            //相册
            case R.id.item_headPic_camera:
                Intent getImage=new Intent(Intent.ACTION_GET_CONTENT);
                getImage.addCategory(Intent.CATEGORY_OPENABLE);
                getImage.setType("image/*");
                startActivityForResult(getImage, 1);
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            //拍照
            if (requestCode==0){
                Bitmap b= BitmapFactory.decodeFile(imgFileName);
                ivHeadPic.setImageBitmap(b);
            }
            //相册
            if (requestCode==1){

            }
        }
    }

    public void sendPic(){

    }

    public void backOnClick(View view){
        finish();
    }
}

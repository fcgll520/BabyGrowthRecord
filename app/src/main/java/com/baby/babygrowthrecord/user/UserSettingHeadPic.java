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

import com.baby.babygrowthrecord.R;

import java.io.File;
import java.util.Calendar;

/**
 * Created by think on 2016/11/22.
 */
public class UserSettingHeadPic extends Activity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_headpic);
        init();
    }

    private void init() {
        toolbar=(Toolbar)findViewById(R.id.toolBar_userSetting_headPic);

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

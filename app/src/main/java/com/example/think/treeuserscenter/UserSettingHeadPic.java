package com.example.think.treeuserscenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by think on 2016/11/22.
 */
public class UserSettingHeadPic extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_headpic);
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
                Intent getImage=new Intent(Intent.ACTION_GET_CONTENT);
                getImage.addCategory(Intent.CATEGORY_OPENABLE);
                break;
            case R.id.item_headPic_camera:
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /*
    * public static final String MIME_TYPE_IMAGE_JPEG = "image/*";

public static final int ACTIVITY_GET_IMAGE = 0;

Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);

getImage.addCategory(Intent.CATEGORY_OPENABLE);

getImage.setType(MIME_TYPE_IMAGE_JPEG);

startActivityForResult(getImage, ACTIVITY_GET_IMAGE); */
}

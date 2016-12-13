package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baby.babygrowthrecord.R;

import java.io.File;
import java.util.Calendar;

/**
 * Created by think on 2016/11/22.
 */
public class UserSettingHeadPicAdapter extends BaseAdapter {
    private int[]imgs;
    private Context context;

    public UserSettingHeadPicAdapter(Context context, int[] imgs) {
        this.context = context;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            ImageView imageView=new ImageView(context);
            imageView.setImageResource(imgs[position]);
            Gallery.LayoutParams layoutParams=new Gallery.LayoutParams(240,320);
           // layoutParams.setMargins(5,0,5,0);
            imageView.setLayoutParams(layoutParams);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            convertView=imageView;
        }
        return convertView;
    }
}

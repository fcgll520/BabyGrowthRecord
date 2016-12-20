package com.baby.babygrowthrecord.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.baby.babygrowthrecord.Fragment.PublishActivity;
import com.baby.babygrowthrecord.adapter.FolderAdapter;
import com.baby.babygrowthrecord.util.Bimp;
import com.baby.babygrowthrecord.util.PublicWay;
import com.baby.babygrowthrecord.util.Res;

/**
 * Created by asus on 2016/12/20.
 */
public class ImageFile extends AppCompatActivity {
    private FolderAdapter folderAdapter;
    private Button bt_cancel;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Res.getLayoutID("plugin_camera_image_file"));
        PublicWay.activityList.add(this);
        mContext = this;
        bt_cancel = (Button) findViewById(Res.getWidgetID("cancel"));
        bt_cancel.setOnClickListener(new CancelListener());
        GridView gridView = (GridView) findViewById(Res.getWidgetID("fileGridView"));
        TextView textView = (TextView) findViewById(Res.getWidgetID("headerTitle"));
        textView.setText(Res.getString("photo"));
        folderAdapter = new FolderAdapter(this);
        gridView.setAdapter(folderAdapter);
    }

    private class CancelListener implements View.OnClickListener {// 取消按钮的监听
        public void onClick(View v) {
            //清空选择的图片
            Bimp.tempSelectBitmap.clear();
            Intent intent = new Intent();
            intent.setClass(mContext, PublishActivity.class);
            startActivity(intent);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(mContext, PublishActivity.class);
            startActivity(intent);
        }

        return true;
    }
}

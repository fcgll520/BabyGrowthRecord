package com.baby.babygrowthrecord.Growth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.R;
import com.nostra13.universalimageloader.utils.L;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Growth_SendBabyMessage extends AppCompatActivity {

    private EditText et1;
    private EditText et2;
    private ImageView img;
    String time;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth_send);

        //获取ID
        findById();

        sendBabyMessageToServer();
    }

    private void findById() {
        et1=(EditText)findViewById(R.id.et_grow_time);
        et2=(EditText)findViewById(R.id.et_grow_content);
        img=(ImageView) findViewById(R.id.grow_img);

        time=et1.getText().toString();
        content=et2.getText().toString();
    }

    private void sendBabyMessageToServer() {
        Log.e("执行了此函数","执行了此函数");
        String url= Utils.StrUrl+"grow/InsertBabyMessage?grow_time"+time+"&grow_content="+content;
        try {
            URL Url=new URL(url);
            HttpURLConnection coon= (HttpURLConnection) Url.openConnection();
            coon.setRequestMethod("GET");
            coon.setConnectTimeout(2000);
            coon.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

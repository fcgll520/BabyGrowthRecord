package com.baby.babygrowthrecord.Mother;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baby.babygrowthrecord.Fragment.HelpFragment;
import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardMessage extends AppCompatActivity {
    private ImageView img;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private ImageView img1;
    String []str=new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_message);

        img=(ImageView)findViewById(R.id.back);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        img1=(ImageView)findViewById(R.id.img1);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.flag = 3;
                Intent intent=new Intent(CardMessage.this,BabyMainActivity.class);
                startActivity(intent);
            }
        });

        //Intent intent=getIntent();
        final int intent=getIntent().getIntExtra("essay_id",0);

        //网络请求
        //从服务器获取信息并解析
        AsyncHttpClient client = new AsyncHttpClient();
        String url =Utils.StrUrl+"essay/test";

        client.get(getApplicationContext(), url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
               // System.out.println(response.toString());
                try {
                    for (int i=0;i<response.length();i++){
                        JSONObject data=response.getJSONObject(i);
                        int id=data.getInt("essay_id");
                        if (id==intent){
                            tv1.setText(data.getString("essay_title"));
                            tv2.setText(data.getString("essay_time"));
                            tv3.setText(data.getString("essay_author"));
                            tv4.setText(data.getString("essay_contents"));

                            //从服务器获取图片
                            ImageLoader imageLoader=ImageLoader.getInstance();
                            imageLoader.displayImage(Utils.StrUrl+data.getString("essay_photo"),img1);

                            Log.e("essay","true");
                        }

                    }
                } catch (JSONException e) {
                    Log.e("essay","cuowu");
                    e.printStackTrace();
                }

            }
        });
    }
}

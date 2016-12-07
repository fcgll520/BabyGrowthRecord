package com.baby.babygrowthrecord.Mother;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Fragment.HelpFragment;
import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_message);
        Intent i=getIntent();
        int k=i.getIntExtra("essay_id",-1);
        if (k==-1){
            Toast.makeText(getApplicationContext(),"加载失败",Toast.LENGTH_SHORT).show();
            finish();
        }
        Toast.makeText(getApplicationContext(),"id:"+k,Toast.LENGTH_SHORT).show();

        img=(ImageView)findViewById(R.id.back);
//        tv1=(TextView)findViewById(R.id.tv1);
//        tv2=(TextView)findViewById(R.id.tv2);
//        tv3=(TextView)findViewById(R.id.tv3);
//        tv4=(TextView)findViewById(R.id.tv4);
//        img1=(ImageView)findViewById(R.id.img1);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.flag = 4;
                Intent intent=new Intent(CardMessage.this,BabyMainActivity.class);
                startActivity(intent);
            }
        });

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://10.7.88.52:8080/essay/test";

        client.get(getApplicationContext(), url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println(response.toString());
                try {
                    for (int i=0;i<response.length();i++){
                        JSONObject data=response.getJSONObject(i);
                        int id=data.getInt("essay_id");
                        tv1.setText(data.getString("essay_title"));
                        tv2.setText(data.getString("essay_time"));
                        tv3.setText(data.getString("essay_author"));
                        tv4.setText(data.getString("essay_contents"));
                        Log.e("essay","true");
                    }
                } catch (JSONException e) {
                    Log.e("essay","cuowu");
                    e.printStackTrace();
                }
            }
        });

    }
}

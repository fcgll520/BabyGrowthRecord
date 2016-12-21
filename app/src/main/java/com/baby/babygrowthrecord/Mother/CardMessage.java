package com.baby.babygrowthrecord.Mother;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.baby.babygrowthrecord.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class CardMessage extends AppCompatActivity {
    private ImageView img;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private ImageView img1;
    private ImageView shares;
    String[] str = new String[20];
    public  ImageLoader imageLoader=ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_message);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        shares = (ImageView) findViewById(R.id.share);
        img = (ImageView) findViewById(R.id.back);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        img1 = (ImageView) findViewById(R.id.img1);

        //分享功能的点击事件
        shares.setOnClickListener(mOnclicklistener);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.flag = 3;
                Intent intent = new Intent(CardMessage.this, BabyMainActivity.class);
                startActivity(intent);
            }
        });

        //Intent intent=getIntent();
        final int intent = getIntent().getIntExtra("essay_id", 0);

        //网络请求
        //从服务器获取信息并解析
        AsyncHttpClient client = new AsyncHttpClient();
        String url = Utils.StrUrl + "essay/test";

        client.get(getApplicationContext(), url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                // System.out.println(response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject data = response.getJSONObject(i);
                        int id = data.getInt("essay_id");
                        if (id == intent) {
                            tv1.setText(data.getString("essay_title"));
                            tv2.setText(data.getString("essay_time"));
                            tv3.setText(data.getString("essay_author"));
                            tv4.setText(data.getString("essay_contents"));

                            //从服务器获取图片
                            ImageLoader imageLoader = ImageLoader.getInstance();
                            imageLoader.displayImage(Utils.StrUrl + data.getString("essay_photo"), img1);

                            Log.e("essay", "true");
                        }

                    }
                } catch (JSONException e) {
                    Log.e("essay", "cuowu");
                    e.printStackTrace();
                }

            }
        });
    }
    //分享的功能
    private View.OnClickListener mOnclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showShare();
        }
    };

    public void share(View v) {
        showShare();
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用


        /*  **注意**  *这里需要自己定义一个title**/
        oks.setTitle("分享");

        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}

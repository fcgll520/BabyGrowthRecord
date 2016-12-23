package com.baby.babygrowthrecord.Growth;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.R;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.utils.L;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class Growth_SendBabyMessage extends AppCompatActivity {

    private EditText et1;
    private EditText et2;
    private ImageView img;

    private Button btnCancel;
    private Button btnSave;

    String time;
    String content;
    String imgFileName="";

    private View popupView;
    private PopupWindow popupWindow;

    private View.OnClickListener mainClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_growthSend_cancel:
                    Log.e("mainClickListener","cancel");
                    finish();
                    break;
                case R.id.btn_growthSend_send:
                    Log.e("mainClickListener","send");
                    sendInfo();
                    break;
                case R.id.grow_img:
                    Log.e("mainClickListener","img");
                    popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,0,0);
                    popupWindow.showAsDropDown(v);
                    break;
                default:
                    break;
            }
        }
    };
    private View.OnClickListener imgClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //拍照
                case R.id.item_popupwindows_camera:
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    long time = Calendar.getInstance().getTimeInMillis();
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  //sd卡是否可用
                        imgFileName=Environment.getExternalStorageDirectory().getAbsolutePath() + "/baby_record" + time + ".jpg";
                        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgFileName)));
                        startActivityForResult(i, 0);
                    }else {
                        Toast.makeText(Growth_SendBabyMessage.this,"无可用sd卡,选择图片失败！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                //相册
                case R.id.item_popupwindows_Photo:
                    Intent getImage=new Intent(Intent.ACTION_GET_CONTENT);
                    getImage.addCategory(Intent.CATEGORY_OPENABLE);
                    getImage.setType("image/*");
                    startActivityForResult(getImage, 1);
                    break;
                case R.id.item_popupwindows_cancel:
                    popupWindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    private TextView tvCamera;
    private TextView tvPhoto;
    private TextView tvCancel;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==-1){
                Toast.makeText(Growth_SendBabyMessage.this,"文字消息发送失败！图片未能发送~",Toast.LENGTH_SHORT).show();
                Log.e("sendGrowMessageToServer","发生异常");
            }else {
                if (imgFileName.equals("")){
                    Log.e("handleMessage","用户未选择图片~");
                    Toast.makeText(Growth_SendBabyMessage.this,"成长记录发布成功~",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                Toast.makeText(Growth_SendBabyMessage.this,"文字消息发送成功~",Toast.LENGTH_SHORT).show();
                Log.e("handleMessage","开始发送图片");
                sendGrowPicToServer(msg.arg1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth_send);

        //获取ID
        findById();
    }

    private void findById() {
        et1=(EditText)findViewById(R.id.et_grow_time);
        et2=(EditText)findViewById(R.id.et_grow_content);
        img=(ImageView) findViewById(R.id.grow_img);

        btnCancel=(Button)findViewById(R.id.btn_growthSend_cancel);
        btnSave=(Button)findViewById(R.id.btn_growthSend_send);

        time=et1.getText().toString();
        content=et2.getText().toString();

        initPopupWindow();

        tvCamera=(TextView)popupView.findViewById(R.id.item_popupwindows_camera);
        tvPhoto=(TextView)popupView.findViewById(R.id.item_popupwindows_Photo);
        tvCancel=(TextView)popupView.findViewById(R.id.item_popupwindows_cancel);

        tvCamera.setOnClickListener(imgClickListener);
        tvPhoto.setOnClickListener(imgClickListener);
        tvCancel.setOnClickListener(imgClickListener);

        img.setOnClickListener(mainClickListener);
        btnSave.setOnClickListener(mainClickListener);
        btnCancel.setOnClickListener(mainClickListener);
    }

    private void initPopupWindow() {
        popupView = LayoutInflater.from(Growth_SendBabyMessage.this)
                .inflate(R.layout.item_growth_popupwindow,null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,false);
        //设置参数实现点击外面消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边消失
        popupWindow.setOutsideTouchable(true);
        //设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
    }

    private void sendInfo(){
        //先发送文字信息，再发送图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=new Message();
                msg.arg1=sendGrowMessageToServer();
                handler.sendMessage(msg);
            }
        }).start();
        //发送图片
//        sendGrowPicToServer();
    }

    private int sendGrowMessageToServer() {
        Log.e("执行了此函数","执行了此函数");
        time=et1.getText().toString();
        content=et2.getText().toString();
        String url= Utils.StrUrl+"grow/InsertGrowMessage?user_id="+Utils.userId+"&grow_time="+time+"&grow_content="+content;
        try {
            URL Url=new URL(url);
            HttpURLConnection coon= (HttpURLConnection) Url.openConnection();
            coon.setRequestMethod("GET");
            coon.setConnectTimeout(2000);
            coon.connect();
            //接收反馈
            InputStream is=coon.getInputStream();
            byte[]b=new byte[1024];
            is.read(b);
            String str=new String(b);
            JSONObject object=new JSONObject(str);
            Log.e("sendText",str);
            Log.e("sendText-id",object.getInt("grow_id")+"");
            return object.getInt("grow_id");//返回grow_id
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("sendText","end");
        return -1;  //发生异常
    }

    private void sendGrowPicToServer(int grow_id) {
        File file = new File(imgFileName);
        if (file.exists()) {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = Utils.StrUrl + "grow/getPicture";
            RequestParams para = new RequestParams();
            try {
                para.put("grow_photo", file,"multipart/form-data");
                para.put("grow_id",grow_id);
                para.setContentEncoding("utf-8");
                client.post(Growth_SendBabyMessage.this, url, para, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Log.e("sendGrowPic'onSuccess", "start");

                        if (i == 200) {
                            Log.e("grow_photo", "success");
                            Toast.makeText(Growth_SendBabyMessage.this,"成长记录发布成功~",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(Growth_SendBabyMessage.this,"图片上传出错！",Toast.LENGTH_SHORT).show();
                        }
                        Log.e("sendGrowPic'onFailure", new String(bytes));
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(Growth_SendBabyMessage.this,"网络连接错误,图片上传失败，请稍后再试！",Toast.LENGTH_SHORT).show();
                        Log.e("sendPic-onFailure",throwable.toString());

                        Log.e("sendGrowPic'onFailure", "start");
                        Log.e("", throwable.toString());
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(Growth_SendBabyMessage.this,"图片文件出错，请重新选取！",Toast.LENGTH_SHORT).show();
            Log.e("imgFileName",imgFileName);
        }
    }

    /**/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult","start");
        if (resultCode==RESULT_OK){
            popupWindow.dismiss();
            if (requestCode==0){
                //拍照
                showImage();
            }else if (requestCode==1){
                //相册
                Uri uri=data.getData();
                if (uri!=null){
                    Log.e("onActivityResult-uri",uri.toString());
                    //代码可用有效
                    String []filePaths={MediaStore.Images.Media.DATA};
                    Cursor cursor=getContentResolver().query(uri,filePaths,null,null,null);
                    cursor.moveToFirst();
                    imgFileName=cursor.getString(cursor.getColumnIndex(filePaths[0]));
                    showImage();
                }else {
                    Log.e("onActivityResult","uri is null");
                }
            }
        }
        Log.e("onActivityResult","end");
    }
    /*imageView显示图片*/
    private void showImage(){
        if ((new File(imgFileName).exists())){
                img.setImageBitmap(BitmapFactory.decodeFile(imgFileName));
            }
    }
}

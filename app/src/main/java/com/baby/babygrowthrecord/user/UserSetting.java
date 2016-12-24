package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Circle.Circle;
import com.baby.babygrowthrecord.Fragment.GrowthFragment;
import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.Growth.Growth_SendBabyMessage;
import com.baby.babygrowthrecord.Login.Login_Activity;
import com.baby.babygrowthrecord.R;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by think on 2016/11/22.
 */
public class UserSetting extends Activity {
    private RelativeLayout rvHeadPic;
    private RelativeLayout rvName;
    private RelativeLayout rvPwd;
    private TextView tvHeadPic;
    private CircleImageView ivHeadPic;
    private TextView tvName;
    private TextView tvUname;
    private TextView tvPwd;
    private Button btnExist;

    private PopupWindow popupWindow;
    private String imgFileName="";

    private View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent();
            switch (v.getId()){
                case R.id.rv_userSetting_pic:
                    popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,0,0);
                    popupWindow.showAsDropDown(v);
                    break;
                case R.id.rv_userSetting_name:
                    i.setClass(UserSetting.this,UserSettingName.class);
                    i.putExtra("name",tvUname.getText());
                    startActivity(i);
                    break;
                case R.id.rv_userSetting_pwd:
                    i.setClass(UserSetting.this,UserSettingPwd.class);
                    startActivity(i);
                    break;
                case R.id.tv_userSetting_pic:
                    popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,0,0);
                    popupWindow.showAsDropDown(v);
                    break;
                case R.id.tv_userSetting_name:
                    i.setClass(UserSetting.this,UserSettingName.class);
                    i.putExtra("name",tvUname.getText());
                    startActivity(i);
                    break;
                case R.id.tv_userSetting_pwd:
                    i.setClass(UserSetting.this,UserSettingPwd.class);
                    startActivity(i);
                    break;
                case R.id.btn_userSetting_exist:
                    existLogin();
                    break;
                default:
                    break;
            }
        }
    };

    View.OnClickListener picClickLisener=new View.OnClickListener() {
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
                        Toast.makeText(UserSetting.this,"无可用sd卡,更改头像失败！",Toast.LENGTH_SHORT).show();
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
    private View popview;

    //退出登录
    private void existLogin() {
        //
        Login_Activity login=new Login_Activity();
        SharedPreferences sharedPreferences=login.initSharedPreferences(UserSetting.this);
        sharedPreferences.edit().putString("isAutoLogin","false").commit();
        //
        Utils.userId=-1;
        Toast.makeText(UserSetting.this,"退出登录成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        init();
    }

    private void init() {
        rvHeadPic=(RelativeLayout)findViewById(R.id.rv_userSetting_pic);
        rvName=(RelativeLayout)findViewById(R.id.rv_userSetting_name);
        rvPwd=(RelativeLayout)findViewById(R.id.rv_userSetting_pwd);
        tvHeadPic=(TextView)findViewById(R.id.tv_userSetting_pic);
        ivHeadPic=(CircleImageView)findViewById(R.id.iv_userSet_headPic);
        tvName=(TextView)findViewById(R.id.tv_userSetting_name);
        tvUname=(TextView)findViewById(R.id.tv_userSetting_uName);
        tvPwd=(TextView)findViewById(R.id.tv_userSetting_pwd);
        btnExist=(Button)findViewById(R.id.btn_userSetting_exist);

        //设置监听器
        rvHeadPic.setOnClickListener(myClickListener);
        rvName.setOnClickListener(myClickListener);
        rvPwd.setOnClickListener(myClickListener);
        tvHeadPic.setOnClickListener(myClickListener);
        tvName.setOnClickListener(myClickListener);
        tvPwd.setOnClickListener(myClickListener);
        btnExist.setOnClickListener(myClickListener);

        //获取用户名和头像
        getUserInfo();

        //初始化popupWindow
        initPopupWindow();
    }

    private void initPopupWindow() {
        //初始化popupWindow
        popview = LayoutInflater.from(UserSetting.this).inflate(R.layout.item_user_popupwindow,null);
        //创建popupwindow对象
        popupWindow=new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,false);
        //设置参数实现点击外面消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边消失
        popupWindow.setOutsideTouchable(true);
        //设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);

        TextView tvPhotos=(TextView) popview.findViewById(R.id.item_popupwindows_Photo);
        TextView tvCamera=(TextView) popview.findViewById(R.id.item_popupwindows_camera);
        TextView tvCancel=(TextView) popview.findViewById(R.id.item_popupwindows_cancel);

        tvCamera.setOnClickListener(picClickLisener);
        tvPhotos.setOnClickListener(picClickLisener);
        tvCancel.setOnClickListener(picClickLisener);
    }

    //更改头像
    private void sendHeadPic(){
        if (imgFileName.equals("")){
            Log.e("sendHeadPic","用户没有选择图片文件");
            return;
        }
        File file=new File(imgFileName);
        if (file.exists()){
            AsyncHttpClient client=new AsyncHttpClient();
            String url=Utils.StrUrl+"user/editHeadPic";
            RequestParams para=new RequestParams();
            try {
                para.put("user_photo",file,"multipart/form-data");
                para.put("user_id",Utils.userId);
                para.setContentEncoding("utf-8");
                client.post(UserSetting.this,url,para,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Log.e("user_photo'onSuccess","start");
                        if (i==200){
                            Log.e("user_photo","success");
                            Glide.with(UserSetting.this)
                                    .load(Utils.StrUrl+new String(bytes))
                                    .into(ivHeadPic);
                            Toast.makeText(UserSetting.this,"更改头像成功！",Toast.LENGTH_SHORT).show();
                        }
                        Log.e("sendPic-onSuccess",new String(bytes));
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Log.e("user_photo'onFailure","start");
                        Toast.makeText(UserSetting.this,"网络连接错误，更改头像失败，请稍后再试！",Toast.LENGTH_SHORT).show();
                        Log.e("",throwable.toString());
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(UserSetting.this,"图片文件出错，请重新选取！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
        popupWindow.dismiss();
    }

    //获取用户信息
    public void getUserInfo(){
        GrowthFragment g=new GrowthFragment();
        g.getUserInfo(ivHeadPic,tvUname);
    }

    public void backOnClick(View view){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK){
            return;
        }
        if (requestCode==0){
            //拍照
            sendHeadPic();
        }else if (requestCode==1){
            //相册
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            //此处为了解决android4.4 相册选择时的 选择最近图片 获取不到path问题
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){//4.4及以上
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String[] column = { MediaStore.Images.Media.DATA };
                String sel = MediaStore.Images.Media._ID + "=?";
                Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                        sel, new String[] { id }, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    imgFileName = cursor.getString(columnIndex);
                    Log.e("onActivityResult","imgFileName is"+imgFileName);
                    sendHeadPic();
                }else {
                    Toast.makeText(UserSetting.this,"未从相册获取到图片!",Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }else{//4.4以下，即4.4以上获取路径的方法
                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                imgFileName = cursor.getString(column_index);
                Log.e("onActivityResult","imgFileName is"+imgFileName);
                sendHeadPic();
            }
        }
    }
}

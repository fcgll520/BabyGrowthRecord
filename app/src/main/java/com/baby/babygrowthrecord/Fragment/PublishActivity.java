package com.baby.babygrowthrecord.Fragment;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.babygrowthrecord.Login.Register_Activity;
import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.baby.babygrowthrecord.R;
import com.baby.babygrowthrecord.activity.AlbumActivity;
import com.baby.babygrowthrecord.util.Bimp;
import com.baby.babygrowthrecord.util.FileUtils;
import com.baby.babygrowthrecord.util.ImageItem;
import com.baby.babygrowthrecord.util.PublicWay;
import com.baby.babygrowthrecord.util.Res;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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

@SuppressLint("HandlerLeak")
public class PublishActivity extends AppCompatActivity {


    private Button button1;
    private Button button2;
    private EditText editText;

    private GridView noScrollgridview;
    private GridAdapter adapter;
    private LinearLayout ll_popup;
    public static Bitmap bimap ;
    private PopupWindow pop = null;
    private View parentView;
    private Uri photoUri ; //得到清晰的图片

    String SDPATH="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Res.init(this);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
        PublicWay.activityList.add(this);
        parentView = getLayoutInflater().inflate(R.layout.activity_third, null);
        setContentView(parentView);
        Init();


        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishActivity.this.finish();
            }
        });


        editText = (EditText)findViewById(R.id.edit_text);
        button2 = (Button)findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfo();
            }
        });
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
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==-1){
                Toast.makeText(PublishActivity.this,"文字消息发送失败！图片未能发送~",Toast.LENGTH_SHORT).show();
                Log.e("sendGrowMessageToServer","发生异常");
            }else {
                if (SDPATH.equals("")){
                    Log.e("handleMessage","用户未选择图片~");
                    Toast.makeText(PublishActivity.this,"圈子动态发布成功~",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                Toast.makeText(PublishActivity.this,"文字消息发送成功~",Toast.LENGTH_SHORT).show();
                Log.e("handleMessage","开始发送图片");
                sendGrowPicToServer(msg.arg1);
            }
        }
    };
    private void sendGrowPicToServer(int cir_id) {
        File file = new File(SDPATH);
        if (file.exists()) {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = Utils.StrUrl + "circle/getPicture";
            RequestParams para = new RequestParams();
            try {
                para.put("cir_photo", file,"multipart/form-data");
                para.put("cir_id",cir_id);
                para.setContentEncoding("utf-8");
                client.post(PublishActivity.this, url, para, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Log.e("sendGrowPic'onSuccess", "start");

                        if (i == 200) {
                            Log.e("grow_photo", "success");
                            Toast.makeText(PublishActivity.this,"成长记录发布成功~",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(PublishActivity.this,"图片上传出错！",Toast.LENGTH_SHORT).show();
                        }
                        Log.e("sendGrowPic'onFailure", new String(bytes));
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(PublishActivity.this,"网络连接错误,图片上传失败，请稍后再试！",Toast.LENGTH_SHORT).show();
                        Log.e("sendPic-onFailure",throwable.toString());

                        Log.e("sendGrowPic'onFailure", "start");
                        Log.e("", throwable.toString());
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(PublishActivity.this,"图片文件出错，请重新选取！",Toast.LENGTH_SHORT).show();
            Log.e("SDPATH",SDPATH);
        }
    }
    private int sendGrowMessageToServer() {
        Log.e("执行了此函数","执行了此函数");
        String content=editText.getText().toString();
        String url= Utils.StrUrl+"circle/fabucontent?cir_id="+Utils.cir_id+"&cir_content="+content;
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
            Log.e("sendText-id",object.getInt("cir_id")+"");
            return object.getInt("cir_id");//返回cir_id
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
    /**
     * 判断sdcard卡是否可用
     *
     * @return 布尔类型 true 可用 false 不可用
     */

    private boolean isSDCardCanUser() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public void Init(){
        pop = new PopupWindow(PublishActivity.this);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PublishActivity.this, AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    Log.i("ddddddd", "----------");
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(PublishActivity.this,R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                        Toast.makeText(PublishActivity.this, Bimp.tempSelectBitmap.get(arg2).imagePath, Toast.LENGTH_LONG).show();
                    /*Intent intent = new Intent(PublishActivity.this, GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);*/
                }
            }
        });
    }
    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if(Bimp.tempSelectBitmap.size() == 9){
                return 9;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position ==Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }
    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            //Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            ContentValues values = new ContentValues();
            photoUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            //准备intent，并 指定 新 照片 的文件名（photoUri）
            System.out.println("-------------------------"+photoUri.toString());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            //启动拍照的窗体。并注册 回调处理。
            startActivityForResult(intent, TAKE_PICTURE);
            // startActivityForResult(openCameraIntent, TAKE_PICTURE);
        }else{
            Toast.makeText(PublishActivity.this, "没有内存卡不能拍照", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                ContentResolver cr = this.getContentResolver();
                Cursor cursor = cr.query(photoUri, null, null, null, null);
                Bitmap bm = null;
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        String path = cursor.getString(1);
                        //String path = photoUri.toString();
                        //System.out.println("+++++++++++++++++++++++++="+path);
					/*BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					bm = BitmapFactory.decodeFile(path, options);
					int xScale ;
					if(options.outWidth>options.outHeight){
						 xScale = options.outHeight / 160;
					}else{
						xScale = options.outWidth / 160;
					}
					options.inSampleSize = xScale;
					options.inJustDecodeBounds = false;*/
                        bm = BitmapFactory.decodeFile(path);
                    }
                }
                cursor.close();
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
                    System.out.println("添加照片");
                    String fileName = String.valueOf(System.currentTimeMillis());
                    //Bitmap bm = (Bitmap) data.getExtras().get("data");
                    //FileUtils.saveBitmap(bm, fileName);

                    FileUtils.saveBitmap(bm, fileName);
                    //为了得到照片的路径
                    String SDPATH = Environment.getExternalStorageDirectory()
                            + "/Photo_LJ/";
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    takePhoto.setImagePath(SDPATH+fileName+".JPEG");
                    takePhoto.setImageName(fileName+".JPEG");
                    Bimp.tempSelectBitmap.add(takePhoto);
                    System.out.println("里面有多少张图片"+Bimp.tempSelectBitmap.size());
                }
                bm = null;
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for(int i=0;i<PublicWay.activityList.size();i++){
                if (null != PublicWay.activityList.get(i)) {
                    PublicWay.activityList.get(i).finish();
                }
            }
            System.exit(0);
        }
        return true;
    }
}


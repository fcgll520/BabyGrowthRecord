package com.baby.babygrowthrecord.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baby.babygrowthrecord.R;
import com.baby.babygrowthrecord.user.UserAlbum;
import com.baby.babygrowthrecord.user.UserCollection;
import com.baby.babygrowthrecord.user.UserInfoManage;
import com.baby.babygrowthrecord.user.UserSetting;
import com.baby.babygrowthrecord.user.UserSettingHeadPic;
import com.baby.babygrowthrecord.user.UserSettingName;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus on 2016/11/22.
 */
public class PeopleFragment extends Fragment{
    private View view;
    private CircleImageView ivHeadPic;
    private TextView tvUname;
    private TextView tvBabyName;

    private RelativeLayout rlAlbum;
    private RelativeLayout rlInfoManage;
    private RelativeLayout rlCollection;
    private RelativeLayout rlSetting;

    private TextView tvAlbum;
    private TextView tvInfoManage;
    private TextView tvCollection;
    private TextView tvSetting;
    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent();
            switch (v.getId()){
                case R.id.img_circlePic:
                    i.setClass(getActivity(), UserSettingHeadPic.class);
                    startActivity(i);
                    break;
               /* case R.id.tv_user_uName:
                    i.setClass(getActivity(), UserSettingName.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_babyAge:
                    i.setClass(getActivity(), UserInfoManage.class);
                    startActivity(i);
                    break;*/
                case R.id.rl_user_album:
                    i.setClass(getActivity(),UserAlbum.class);
                case R.id.tv_user_collect:
                    i.setClass(getActivity(), UserCollection.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_setting:
                    startActivity(i);
                    break;
                case R.id.rl_user_infoMange:
                    i.setClass(getActivity(),UserInfoManage.class);
                    startActivity(i);
                    break;
                case R.id.rl_user_collect:
                    i.setClass(getActivity(), UserCollection.class);
                    startActivity(i);
                    break;
                case R.id.rl_user_setting:
                    i.setClass(getActivity(),UserSetting.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_album:
                    i.setClass(getActivity(),UserAlbum.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_infoMange:
                    i.setClass(getActivity(),UserInfoManage.class);
                    startActivity(i);
                    break;

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_user, container, false);
        init();

        AsyncHttpClient client=new AsyncHttpClient();
        String url=Utils.StrUrl+"user/text";
        client.get(getActivity(),url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println(response.toString());
                    try {
                        for (int i=0;i<response.length();i++) {
                            JSONObject data = response.getJSONObject(i);
                            tvUname.setText(data.getString("user_name"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        });

        AsyncHttpClient client1=new AsyncHttpClient();
        String url1=Utils.StrUrl+"baby/text";

        client.get(getActivity(),url1,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println(response.toString());
                try {
                    for (int j=0;j<response.length();j++) {
                        JSONObject data1 = response.getJSONObject(j);
                        tvBabyName.setText(data1.getString("baby_name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }
    private void init(){
        ivHeadPic = (CircleImageView) view.findViewById(R.id.img_circlePic);
        tvUname = (TextView) view.findViewById(R.id.tv_user_uName);
        tvBabyName = (TextView) view.findViewById(R.id.tv_user_babyName);

        rlAlbum=(RelativeLayout)view.findViewById(R.id.rl_user_album);
        rlInfoManage=(RelativeLayout)view.findViewById(R.id.rl_user_infoMange);
        rlCollection=(RelativeLayout)view.findViewById(R.id.rl_user_collect);
        rlSetting=(RelativeLayout)view.findViewById(R.id.rl_user_setting);

        tvAlbum=(TextView)view.findViewById(R.id.tv_user_album);
        tvInfoManage =(TextView)view.findViewById(R.id.tv_user_infoMange);
        tvCollection=(TextView)view.findViewById(R.id.tv_user_collect);
        tvSetting=(TextView)view.findViewById(R.id.tv_user_setting);

        //绑定监听器
        ivHeadPic.setOnClickListener(myClickListener);
        tvUname.setOnClickListener(myClickListener);
        tvBabyName.setOnClickListener(myClickListener);

        rlAlbum.setOnClickListener(myClickListener);
        rlInfoManage.setOnClickListener(myClickListener);
        rlCollection.setOnClickListener(myClickListener);
        rlSetting.setOnClickListener(myClickListener);

        tvAlbum.setOnClickListener(myClickListener);
        tvInfoManage.setOnClickListener(myClickListener);
        tvCollection.setOnClickListener(myClickListener);
        tvSetting.setOnClickListener(myClickListener);
    }
}

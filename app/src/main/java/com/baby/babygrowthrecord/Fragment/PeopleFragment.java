package com.baby.babygrowthrecord.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
                case R.id.tv_user_uName:
                    i.setClass(getActivity(), UserSettingName.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_babyName:
                    i.setClass(getActivity(), UserInfoManage.class);
                    startActivity(i);
                    break;
                case R.id.rl_user_album:
                    i.setClass(getActivity(),UserAlbum.class);
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
                case R.id.tv_user_collect:
                    i.setClass(getActivity(), UserCollection.class);
                    startActivity(i);
                    break;
                case R.id.tv_user_setting:
                    i.setClass(getActivity(),UserSetting.class);
                    startActivity(i);
                    break;
                default:
                    break;
            }
        }
    };
    private Boolean isLogin=false;
    private LinearLayout llLogin;
    private LinearLayout llUnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_user, container, false);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

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

        llLogin=(LinearLayout)view.findViewById(R.id.ll_user_hasLogin);

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

        //获取用户名、头像
        GrowthFragment g=new GrowthFragment();
        g.getUserInfo(ivHeadPic,tvUname);
        //获取宝宝昵称
       getBabyName(tvBabyName);
    }
    private void autoLogin(){

    }

    @Override
    public void onResume() {
        super.onResume();
        getBabyName(tvBabyName);
    }

    public void  getBabyName(final TextView babyName) {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(getActivity(),Utils.StrUrl+"baby/getBabyInfoById/"+Utils.userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    babyName.setText(response.getString("baby_name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

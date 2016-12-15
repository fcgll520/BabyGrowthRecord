package com.baby.babygrowthrecord.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by think on 2016/12/15.
 */
public class RegisterConfirm extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();
        i.getStringExtra("userPhone");
        i.getStringExtra("confirmCOde");
        i.getStringExtra("userPwd");
        i.getStringExtra("userConfirmPwd");
        //匹配验证码
        //匹配密码与确认密码
        //发送注册用户的请求

    }
}

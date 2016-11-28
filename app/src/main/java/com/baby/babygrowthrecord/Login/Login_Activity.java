package com.baby.babygrowthrecord.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baby.babygrowthrecord.Growth.Growth_Activity;
import com.baby.babygrowthrecord.R;


/**
 * Created by Administrator on 2016/11/24.
 */
public class Login_Activity extends AppCompatActivity {

    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login_btn = (Button)findViewById(R.id.login_login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Growth_Activity.class);
                startActivity(intent);
            }
        });

    }
}

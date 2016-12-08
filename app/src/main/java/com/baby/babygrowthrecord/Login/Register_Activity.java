package com.baby.babygrowthrecord.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baby.babygrowthrecord.MainActivity.BabyMainActivity;
import com.baby.babygrowthrecord.R;

/**
 * Created by Administrator on 2016/12/8.
 */
public class Register_Activity extends Activity {
    private Button register_register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_register_btn=(Button)findViewById(R.id.register_register_btn);
        register_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this, BabyMainActivity.class);
                startActivity(intent);
            }
        });
    }
}

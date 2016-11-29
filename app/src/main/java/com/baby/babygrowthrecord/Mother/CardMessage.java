package com.baby.babygrowthrecord.Mother;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.baby.babygrowthrecord.R;

public class CardMessage extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_message);

        img=(ImageView)findViewById(R.id.back);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CardMessage.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

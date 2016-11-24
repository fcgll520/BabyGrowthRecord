package com.baby.babygrowthrecord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baby.babygrowthrecord.com.baby.babycircle.CircleMainActivity;

public class BabyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_main);
        Intent intent = new Intent(BabyMainActivity.this, com.baby.babygrowthrecord.com.baby.babycircle.CircleMainActivity.class);
    }
}

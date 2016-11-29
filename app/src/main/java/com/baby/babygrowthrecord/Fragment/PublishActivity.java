package com.baby.babygrowthrecord.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baby.babygrowthrecord.R;

public class PublishActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private EditText edit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        edit_button = (EditText)findViewById(R.id.edit_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PublishActivity.this);
                builder.setTitle("你确定要取消发布并保存为草稿？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                 //这里添加点击确定后的逻辑
                                Toast.makeText(PublishActivity.this, "以保存为草稿", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PublishActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(PublishActivity.this, " ", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.create().show();
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }
}

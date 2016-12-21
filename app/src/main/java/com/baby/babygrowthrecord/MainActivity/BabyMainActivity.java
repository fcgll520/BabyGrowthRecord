package com.baby.babygrowthrecord.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.baby.babygrowthrecord.Fragment.GrowthFragment;
import com.baby.babygrowthrecord.Fragment.HelpFragment;
import com.baby.babygrowthrecord.Fragment.PeopleFragment;
import com.baby.babygrowthrecord.Fragment.QuanziFragment;
import com.baby.babygrowthrecord.Fragment.Utils;
import com.baby.babygrowthrecord.Login.Login_Activity;
import com.baby.babygrowthrecord.R;


public class BabyMainActivity extends AppCompatActivity {

    private LinearLayout ll;
    private LinearLayout quanzi,growth,help,people;

    private ImageButton income_button;
    private QuanziFragment mQuanzi;
    private GrowthFragment mGrowth;
    private HelpFragment mHelp;
    private PeopleFragment mPeople;
    private MoreWindow mMoreWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_baby_main);

        //1. 获取界面的控件
        getViews();

        //2. 注册事件监听器
        setListener();

        switch (Utils.flag){
            case 1://显示圈子默认页面
                //3.设置默认的页面（fragment页面）
                setDefaultPage();
                break;
            case 2://成长记录页面
                setGrowthPage();
                break;
            case 3://妈妈帮页面
                setHelpPage();
                break;
            case 4://个人中心页面
                setPeoplePage();
                break;
        }

        income_button = (ImageButton) findViewById(R.id.income);
        income_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreWindow(v);
            }
        });
    }
    //获取界面的控件
    private void getViews(){
        ll = (LinearLayout) findViewById(R.id.ll);
        quanzi = (LinearLayout) findViewById(R.id.quanzi);
        growth = (LinearLayout) findViewById(R.id.growth);
        help = (LinearLayout) findViewById(R.id.help);
        people = (LinearLayout) findViewById(R.id.people);
    }

    //注册事件监听器
    private void setListener(){
        MyListener listener = new MyListener();
        quanzi.setOnClickListener(listener);
        growth.setOnClickListener(listener);
        help.setOnClickListener(listener);
        people.setOnClickListener(listener);
    }

    //设置默认的页面（fragment页面）
    private void setDefaultPage(){
        //1. 获取一个FragmentManager对象
        android.app.FragmentManager fm = getFragmentManager();
        //2. 获取FragmentTransaction对象
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        if(mQuanzi == null) {
            mQuanzi = new QuanziFragment();
        }
        //3. 设置页面
        transaction.replace(R.id.contaner, mQuanzi);
        //4. 执行更改
        transaction.commit();
        ll.invalidate();
    }

    //设置默认的页面（fragment页面）
    private void setGrowthPage(){
        //1. 获取一个FragmentManager对象
        android.app.FragmentManager fm = getFragmentManager();
        //2. 获取FragmentTransaction对象
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        if(mGrowth == null) {
            mGrowth = new GrowthFragment();
        }
        //3. 设置页面
        transaction.replace(R.id.contaner, mGrowth);
        //4. 执行更改
        transaction.commit();
        ll.invalidate();
    }

    //设置默认的页面（fragment页面）
    private void setHelpPage(){
        //1. 获取一个FragmentManager对象
        android.app.FragmentManager fm = getFragmentManager();
        //2. 获取FragmentTransaction对象
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        if(mHelp == null) {
            mHelp = new HelpFragment();
        }
        //3. 设置页面
        transaction.replace(R.id.contaner, mHelp);
        //4. 执行更改
        transaction.commit();
        ll.invalidate();
    }
    //设置默认的页面（fragment页面）
    private void setPeoplePage(){
        //1. 获取一个FragmentManager对象
        android.app.FragmentManager fm = getFragmentManager();
        //2. 获取FragmentTransaction对象
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        if(mPeople == null) {
            mPeople= new PeopleFragment();
        }
        //3. 设置页面
        transaction.replace(R.id.contaner, mPeople);
        //4. 执行更改
        transaction.commit();
        ll.invalidate();
    }

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //1. 获取一个FragmentManager对象
            android.app.FragmentManager fm = getFragmentManager();
            //2. 获取FragmentTransaction对象
            android.app.FragmentTransaction transaction = fm.beginTransaction();
            switch (v.getId()){
                case R.id.quanzi:
                    if(mQuanzi == null){
                        mQuanzi = new QuanziFragment();
                    }
                    //1. 设置页面
                    transaction.replace(R.id.contaner, mQuanzi);
                    break;
                case R.id.growth:
                    if(mGrowth == null){
                        mGrowth = new GrowthFragment();
                    }
                    //2. 设置页面
                    transaction.replace(R.id.contaner, mGrowth);
                    break;
                case R.id.help:
                    if(mHelp == null){
                        mHelp = new HelpFragment();
                    }
                    //3. 设置页面
                    transaction.replace(R.id.contaner, mHelp);
                    break;
                case R.id.people:
                    if(mPeople == null){
                        mPeople = new PeopleFragment();
                    }
                    //4. 设置页面
                    transaction.replace(R.id.contaner, mPeople);
                    break;
            }
            transaction.commit();
            ll.invalidate();
        }
    }
    private void showMoreWindow(View view) {
        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow(this);
            mMoreWindow.init();
        }
        mMoreWindow.showMoreWindow(view,100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_jump, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void jumpToLogin(View view){
        Log.e("jumpToLogin","start");
        Intent i=new Intent(BabyMainActivity.this, Login_Activity.class);
        startActivity(i);
    }
}

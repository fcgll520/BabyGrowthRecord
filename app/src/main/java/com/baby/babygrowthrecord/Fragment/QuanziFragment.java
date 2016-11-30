package com.baby.babygrowthrecord.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baby.babygrowthrecord.Circle.FridListAdapter;
import com.baby.babygrowthrecord.Circle.MessageModle;
import com.baby.babygrowthrecord.R;
import com.google.gson.Gson;

/**
 * Created by asus on 2016/11/22.
 */
public class QuanziFragment extends Fragment{
    private View view;
    public static final String TAG = "MainActivity";
    private FridListAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_circle_main, container, false);
        new LoderDataTask().execute();
        return view;
    }
    class LoderDataTask extends AsyncTask<Void, Void, MessageModle> {

        @Override
        protected MessageModle doInBackground(Void... params) {

            Gson gson = new Gson();

            MessageModle msg = gson.fromJson(getData(), MessageModle.class);
            return msg;
        }

        @Override
        protected void onPostExecute(MessageModle result) {
            mAdapter = new FridListAdapter(getActivity(), result.list);
        }
    }
    private String getData() {
        // 模拟网络获取数据
        String json = "{\"code\":200,\"msg\":\"ok\",list:["
                + "{\"id\":110,\"avator\":\"http://www.poluoluo.com/qq/UploadFiles_7828/201611/2016110420035637.jpg\",\"name\":\"Asum\",\"content\":\"今天带宝宝出去了!\",\"urls\":[]},"
                + "{\"id\":111,\"avator\":\"http://img5.duitang.com/uploads/item/201510/22/20151022092355_Mdz8A.jpeg\",\"name\":\"Smile\",\"content\":\"孩子今天笑的很开心\","
                + "  \"urls\":[\"http://img4.imgtn.bdimg.com/it/u=749491705,2277709209&fm=11&gp=0.jpg\"]},"

                + "{\"id\":114,\"avator\":\"http://tupian.enterdesk.com/2014/mxy/11/2/1/12.jpg\",\"name\":\"Sunny\",\"content\":\"宋民国很淘气\",\"urls\":["
                + "\"http://img.qhdxw.com/hot/uploads/allimg/20161102/031238_29056.gif\","

                + "\"http://img2.imgtn.bdimg.com/it/u=3973598389,3836082953&fm=21&gp=0.jpg\","
                + "\"http://cdn.duitang.com/uploads/item/201511/08/20151108194654_8XhEC.thumb.700_0.jpeg\","
                + "\"http://img1.imgtn.bdimg.com/it/u=133092168,2955757591&fm=21&gp=0.jpg\"]},"

                + "{\"id\":112,\"avator\":\"http://p3.gexing.com/G1/M00/C1/1B/rBACE1PoOomTu2CtAAAQsALNkOA499_200x200_3.jpg?recache=20131108\",\"name\":\"lesi\",\"content\":\"宝宝可爱不可爱\",\"urls\":[\"http://cdn.duitang.com/uploads/item/201506/25/20150625212107_F8mzs.thumb.700_0.jpeg\","
                + "\"http://i2.hdslb.com/bfs/archive/72a7a26f4615907e89deb7b3705601378eff36f7.jpg\"]},"

                + "{\"id\":113,\"avator\":\"http://img2.imgtn.bdimg.com/it/u=3301021828,1789093994&fm=11&gp=0.jpg\",\"name\":\"tracy\",\"content\":\"开心\",\"urls\":[\"http://img3.duitang.com/uploads/item/201505/22/20150522191353_QKRMx.thumb.700_0.jpeg\",\"http://img2.imgtn.bdimg.com/it/u=2725582282,1665252206&fm=21&gp=0.jpg\",\"http://img3.duitang.com/uploads/item/201607/05/20160705101741_NjMXK.jpeg\"]}]}";

        return json;
    }
}

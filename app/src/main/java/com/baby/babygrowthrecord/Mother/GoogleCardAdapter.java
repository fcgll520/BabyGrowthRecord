package com.baby.babygrowthrecord.Mother;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baby.babygrowthrecord.Fragment.HelpFragment;
import com.baby.babygrowthrecord.Mother.GoogleCard;
import com.baby.babygrowthrecord.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by apple on 2016/11/22.
 */
public class GoogleCardAdapter extends BaseAdapter {
    private List<GoogleCard> mCards;
    private Context mContext;

    public GoogleCardAdapter(Context mContext,List<GoogleCard> mCards)
    {
        this.mContext=mContext;
        this.mCards=mCards;
    }

    @Override
    public int getCount()
    {
        return mCards.size();
    }

    @Override
    public Object getItem(int Index)
    {
        return mCards.get(Index);
    }

    @Override
    public long getItemId(int Index)
    {
        return Index;
    }

    @Override
    public View getView(int Index, View mView, ViewGroup mParent)
    {
        ViewHolder mHolder=new ViewHolder();
        mView= LayoutInflater.from(mContext).inflate(R.layout.activity_mother_item, null);

        mHolder.Card_Title=(TextView)mView.findViewById(R.id.Card_Title);
        mHolder.Card_Title.setText(mCards.get(Index).getDescription());

        mHolder.Card_Pic=(ImageView)mView.findViewById(R.id.Card_Pic);
        //从服务器获取图片
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage("http://169.254.76.180:8080/"+mCards.get(Index).getDrawable(),mHolder.Card_Pic);

       /* mHolder.Card_Pic.setImageResource(mCards.get(Index).getDrawable());*/
        return mView;
    }

    private static class ViewHolder
    {
        TextView Card_Title;
        ImageView Card_Pic;
    }

}

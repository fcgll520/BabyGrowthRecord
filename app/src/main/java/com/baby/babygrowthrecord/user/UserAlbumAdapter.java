package com.baby.babygrowthrecord.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baby.babygrowthrecord.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by think on 2016/11/22.
 */
public class UserAlbumAdapter extends RecyclerView.Adapter<UserAlbumAdapter.BaseViewHolder> {
    private ArrayList<String> urlList=new ArrayList<>();
    public void replaceAll(ArrayList<String> list){
        urlList.clear();
        if (list!=null && list.size()>0){
            urlList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneViewHolder
                (LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_album_item,parent,false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(urlList.get(position));
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
        void setData(Object data){
        }
    }
    private class OneViewHolder extends BaseViewHolder{
        private ImageView imageView;
        public OneViewHolder(final View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.iv_userAlbum_item);
            int screenWidth=((Activity)imageView.getContext()).getWindowManager().getDefaultDisplay().getWidth();
            int width=screenWidth/2;
            int height=(int)(200+Math.random()*400);
            CardView.LayoutParams params=new CardView.LayoutParams(width,height);
            imageView.setLayoutParams(params);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(imageView.getContext(),UserAlbumBigImg.class);
                    i.putExtra("imgUrl",urlList.get(getAdapterPosition()));
                    Log.e("OneViewHolder:",getAdapterPosition()+"");
                    imageView.getContext().startActivity(i);
                }
            });
        }

        @Override
        void setData(Object data) {
            if (data !=null){
                String url=(String)data;
                ImageLoader.getInstance().displayImage(url,imageView);
//                Glide.with(itemView.getContext())
//                        .load(url)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.drawable.empty_photo)
//                        .crossFade()
//                        .into(imageView);
            }
        }
    }
}

package com.baby.babygrowthrecord.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baby.babygrowthrecord.R;

/**
 * Created by asus on 2016/11/22.
 */
public class PeopleFragment extends Fragment{
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_user, container, false);
        return view;
    }
}

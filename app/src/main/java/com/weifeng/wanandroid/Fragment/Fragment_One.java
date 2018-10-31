package com.weifeng.wanandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weifeng.wanandroid.R;

/**
 * Created by WZJSB-01 on 2017/12/5.
 */

public class Fragment_One extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_viewpager_layout, null);
        return view;
    }
}

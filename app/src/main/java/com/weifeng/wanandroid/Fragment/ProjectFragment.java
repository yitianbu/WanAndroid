package com.weifeng.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weifeng.wanandroid.R;


public class ProjectFragment extends Fragment {
    private View rootView;
    private RecyclerView navRv,contentRv;
    private RecyclerView.Adapter navAdapter,contentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return this.rootView != null ? this.rootView : inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.rootView == null){
            initView(view);
        }else {

        }

    }

    private void initView(View view) {
        this.rootView = view;
        navRv = view.findViewById(R.id.rv_nav);
        contentRv = view.findViewById(R.id.rv_content);
    }
}

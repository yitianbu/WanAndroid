package com.weifeng.wanandroid.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.model.ArticleBean;
import com.weifeng.wanandroid.utils.AppUtils;

import java.util.List;

/**
 * @anthor weifeng
 * @time 2018/10/29 下午5:22
 */
public class ProjectNavAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private int currentPosition =0;
    private List<String> navListData;
    private RecyclerView recyclerView;

    public ProjectNavAdapter(FragmentActivity activity, RecyclerView navRv) {
        mContext =activity;
        recyclerView = navRv;
    }

    public void setNavListData(List<String> navListData) {
        this.navListData = navListData;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NavViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_nav_nav_adapter,parent,false));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final NavViewHolder viewHolder = (NavViewHolder) holder;
        if(position == currentPosition){
           changeItemByState(true,viewHolder.navTitleTv,viewHolder.navRedBarTv);
        }else {
           changeItemByState(false,viewHolder.navTitleTv,viewHolder.navRedBarTv);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeItemByState(true,viewHolder.navTitleTv,viewHolder.navRedBarTv);
                if(onItemClickListener !=null){
                    onItemClickListener.onItemClick(position);
                }
                currentPosition = position;
                notifyDataSetChanged();
                int firstVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                recyclerView.scrollBy(0,-(AppUtils.getScreenHeight(mContext)/2 -recyclerView.getChildAt(currentPosition-firstVisibleItemPosition).getTop()));
            }
        });
        viewHolder.navTitleTv.setText((navListData.get(position)));
    }


    @Override
    public int getItemCount() {
        if(navListData == null){
            return 0;
        }
        return navListData.size();
    }

    public static class NavViewHolder extends RecyclerView.ViewHolder{
        public TextView navTitleTv;
        public TextView navRedBarTv;
        public View itemView;

        public NavViewHolder(View itemView) {
            super(itemView);
            navTitleTv = itemView.findViewById(R.id.tv_nav_title);
            navRedBarTv = itemView.findViewById(R.id.tv_red_bar);
            this.itemView = itemView;
        }
    }

    public void changeItemByState(boolean isClicked,TextView title, TextView bar){
        if(isClicked){
            title.setTextSize(18);
            title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            bar.setVisibility(View.VISIBLE);
        }else {
            title.setTextSize(16);
            title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            bar.setVisibility(View.GONE);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }
}

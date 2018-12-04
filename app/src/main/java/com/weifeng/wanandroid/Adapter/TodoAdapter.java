package com.weifeng.wanandroid.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.activity.WebViewActivity;
import com.weifeng.wanandroid.model.ArticleContentItem;
import com.weifeng.wanandroid.model.TodoBean;

import java.util.ArrayList;
import java.util.List;

import static com.weifeng.wanandroid.activity.WebViewActivity.ARTICLE_COLLECT;
import static com.weifeng.wanandroid.activity.WebViewActivity.ARTICLE_ID;
import static com.weifeng.wanandroid.model.TodoBean.DONEITEM;
import static com.weifeng.wanandroid.model.TodoBean.DONETOP;
import static com.weifeng.wanandroid.model.TodoBean.TODOITEM;
import static com.weifeng.wanandroid.model.TodoBean.TODOTOP;


/**
 * @anthor weifeng
 * @time 2018/9/19 下午3:10
 */
public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static List<TodoBean> todoItems = new ArrayList<>();
    private Context context;

    public TodoAdapter(Context context) {
        this.context = context;
    }


//    @Override
//    public RecyclerView.ViewHolder getViewHolder(View view) {
//        return null;
//    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
//        RecyclerView.ViewHolder viewHolder = null;
//        switch (viewType) {
//            case TODOTOP:
//                viewHolder = new TodoTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_top, parent, false), true);
//                break;
//            case TODOITEM:
//                viewHolder = new TodoTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_item, parent, false), true);
//                break;
//            case DONETOP:
//                viewHolder = new TodoTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_top, parent, false), true);
//                break;
//            case DONEITEM:
//                viewHolder = new TodoTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_item, parent, false), true);
//                break;
//        }
//        return viewHolder;
//    }

//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position, boolean isItem) {
//        switch (todoItems.get(position).viewType) {
//            case TODOTOP:
//                ((TodoTopViewHolder) holder).bindData(todoItems.get(position));
//                break;
//            case TODOITEM:
//                ((TodoItemViewHolder) holder).bindData(todoItems.get(position), position);
//                break;
//            case DONETOP:
//                ((DoneTopViewHolder) holder).bindData(todoItems.get(position));
//                break;
//            case DONEITEM:
//                ((DoneItemViewHolder) holder).bindData(todoItems.get(position), position);
//                break;
//        }
//    }


//    @Override
//    public int getAdapterItemCount() {
//        return todoItems.size();
//    }



    @Override
    public int getItemViewType(int position) {
        return todoItems.get(position).viewType;
    }

    public void setTodoItems(List<TodoBean> todoItems) {
        this.todoItems.addAll(todoItems);
        notifyDataSetChanged();
    }

    public void clearTodoItems() {
        this.todoItems.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TODOTOP:
                viewHolder = new TodoTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_top, parent, false), true);
                break;
            case TODOITEM:
                viewHolder = new TodoItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_item, parent, false), true);
                break;
            case DONETOP:
                viewHolder = new DoneTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_top, parent, false), true);
                break;
            case DONEITEM:
                viewHolder = new DoneItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_item, parent, false), true);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (todoItems.get(position).viewType) {
            case TODOTOP:
                ((TodoTopViewHolder) holder).bindData(todoItems.get(position));
                break;
            case TODOITEM:
                ((TodoItemViewHolder) holder).bindData(todoItems.get(position), position);
                break;
            case DONETOP:
                ((DoneTopViewHolder) holder).bindData(todoItems.get(position));
                break;
            case DONEITEM:
                ((DoneItemViewHolder) holder).bindData(todoItems.get(position), position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    public static final class TodoTopViewHolder extends RecyclerView.ViewHolder {
        public View totalContentView;
        public TextView topTitleTv;
        public ImageView addImg;


        public TodoTopViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                topTitleTv = itemView.findViewById(R.id.tv_top_title);
                addImg = itemView.findViewById(R.id.img_top_icon);
                totalContentView = itemView;
            }
        }

        public void bindData(TodoBean todoBean) {
            totalContentView.setBackgroundColor(Color.parseColor("#00FF00"));
            topTitleTv.setText("待办清单");
            addImg.setVisibility(View.VISIBLE);
            addImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public static final class TodoItemViewHolder extends RecyclerView.ViewHolder {
        public TextView todoTimeTv;
        public TextView todoTitleTv;
        public TextView todoContentTv;
        public ImageView deleteIconImg;
        public ImageView doneIconImg;


        public TodoItemViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                todoTimeTv = itemView.findViewById(R.id.tv_done_date);
                todoTitleTv = itemView.findViewById(R.id.tv_todo_title);
                todoContentTv = itemView.findViewById(R.id.tv_todo_content);
                deleteIconImg = itemView.findViewById(R.id.img_delete_icon);
                doneIconImg = itemView.findViewById(R.id.img_done_icon);
            }
        }

        public void bindData(TodoBean todoBean, int position) {
            if (position != 0) {
                if (TextUtils.isEmpty(todoItems.get(position - 1).dateStr) || (!todoItems.get(position - 1).dateStr.equals(todoBean.dateStr))) {
                    todoTimeTv.setVisibility(View.VISIBLE);
                    todoTimeTv.setText(todoBean.dateStr);
                }
                todoTitleTv.setText(todoBean.title);
                todoContentTv.setText(todoBean.content);
                deleteIconImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                doneIconImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }

    public static final class DoneTopViewHolder extends RecyclerView.ViewHolder {
        public View totalContentView;
        public TextView topTitleTv;
        public ImageView addImg;


        public DoneTopViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                topTitleTv = itemView.findViewById(R.id.tv_top_title);
                addImg = itemView.findViewById(R.id.img_top_icon);
                totalContentView = itemView;
            }
        }

        public void bindData(TodoBean todoBean) {
            totalContentView.setBackgroundColor(Color.parseColor("#FFFF00"));
            topTitleTv.setText("已完成清单");
            addImg.setVisibility(View.GONE);
        }

    }

    public static final class DoneItemViewHolder extends RecyclerView.ViewHolder {
        public TextView todoTimeTv;
        public TextView todoTitleTv;
        public TextView todoContentTv;
        public ImageView deleteIconImg;
        public ImageView redoIconImg;
        public TextView doneDataTv;


        public DoneItemViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                todoTimeTv = itemView.findViewById(R.id.tv_done_date);
                todoTitleTv = itemView.findViewById(R.id.tv_todo_title);
                todoContentTv = itemView.findViewById(R.id.tv_todo_content);
                deleteIconImg = itemView.findViewById(R.id.img_delete_icon);
                redoIconImg = itemView.findViewById(R.id.img_done_icon);
                doneDataTv = itemView.findViewById(R.id.tv_done_date);
            }
        }

        public void bindData(TodoBean todoBean, int position) {
            doneDataTv.setVisibility(View.VISIBLE);
            doneDataTv.setText("完成:" + todoBean.completeDateStr);
            if (TextUtils.isEmpty(todoItems.get(position - 1).dateStr) || (!todoItems.get(position - 1).dateStr.equals(todoBean.dateStr))) {
                todoTimeTv.setVisibility(View.VISIBLE);
                todoTimeTv.setText(todoBean.dateStr);
            }
            todoTitleTv.setText(todoBean.title);
            todoContentTv.setText(todoBean.content);
            deleteIconImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            redoIconImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}

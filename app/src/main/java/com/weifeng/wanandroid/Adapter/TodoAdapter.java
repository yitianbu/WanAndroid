package com.weifeng.wanandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.dialog.AddToDoDialog;
import com.weifeng.wanandroid.model.TodoBean;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.response.AddToDoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private static Context context;

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

    public final class TodoTopViewHolder extends RecyclerView.ViewHolder {
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
                    AddToDoDialog addToDoDialog = new AddToDoDialog();
                    addToDoDialog.setOnDataAddSuccessListener(new AddToDoDialog.OnDataAddSuccessListener() {
                        @Override
                        public void onDataAddSuccess(TodoBean todoBean) {
                            if (todoItems.get(0).viewType != TODOTOP) {
                                TodoBean todoBean1 = new TodoBean();
                                todoBean1.viewType = TODOTOP;
                                todoItems.add(0, todoBean1);
                                todoBean.viewType = TODOITEM;
                                todoItems.add(1, todoBean);
                            } else {
                                todoBean.viewType = TODOITEM;
                                todoItems.add(findLastTodoDataPos() + 1, todoBean);
                            }
                            notifyDataSetChanged();
                        }
                    });
                    addToDoDialog.show(((Activity) context).getFragmentManager(), "AddToDODialog");
                }
            });
        }
    }

    public final class TodoItemViewHolder extends RecyclerView.ViewHolder {
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

        public void bindData(TodoBean todoBean, final int position) {
//            if (position != 0) {
            if (TextUtils.isEmpty(todoItems.get(position - 1).dateStr) || (!todoItems.get(position - 1).dateStr.equals(todoBean.dateStr))) {
                todoTimeTv.setVisibility(View.VISIBLE);
                todoTimeTv.setText(todoBean.dateStr);
            }
            todoTitleTv.setText(todoBean.title);
            todoContentTv.setText(todoBean.content);
            deleteIconImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteData(position);
                }
            });
            doneIconImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doneData(position);
                }
            });
//            }
        }

        private void doneData(final int position) {
            RetrofitClient.getInstance().getService(APIService.class).updateToDoStatus(todoItems.get(position).id, 1).enqueue(new Callback<AddToDoResponse>() {
                @Override
                public void onResponse(Call<AddToDoResponse> call, Response<AddToDoResponse> response) {
                    if (hasDoneItemInData()) {
                        todoItems.get(position).viewType = DONEITEM;
                        todoItems.add(todoItems.get(position));
                    } else {
                        TodoBean todoBean = new TodoBean();
                        todoBean.viewType = DONETOP;
                        todoItems.add(todoBean);
                        todoItems.get(position).viewType = DONEITEM;
                        todoItems.add(todoItems.get(position));
                    }
                    todoItems.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "更新完成", Toast.LENGTH_SHORT).show();
                }

                private boolean hasDoneItemInData() {
                    return todoItems.get(todoItems.size() - 1).viewType == DONEITEM;
                }

                @Override
                public void onFailure(Call<AddToDoResponse> call, Throwable t) {
                    Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public final class DoneTopViewHolder extends RecyclerView.ViewHolder {
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

    public final class DoneItemViewHolder extends RecyclerView.ViewHolder {
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

        public void bindData(TodoBean todoBean, final int position) {
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
                    deleteData(position);
                }
            });
            redoIconImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reDoData(position);
                }
            });
        }

        private void reDoData(final int position) {
            RetrofitClient.getInstance().getService(APIService.class).updateToDoStatus(todoItems.get(position).id, 0).enqueue(new Callback<AddToDoResponse>() {
                @Override
                public void onResponse(Call<AddToDoResponse> call, Response<AddToDoResponse> response) {
                    int lastTodoPos = findLastTodoDataPos();
                    todoItems.get(position).viewType = TODOITEM;
                    todoItems.add(lastTodoPos + 1, todoItems.get(position));
                    todoItems.remove(position + 1);
                    if (todoItems.get(todoItems.size() - 1).viewType == DONETOP) {
                        todoItems.remove(todoItems.get(todoItems.size() - 1));
                    }
                    TodoAdapter.this.notifyDataSetChanged();
                    Toast.makeText(context, "更新完成", Toast.LENGTH_SHORT).show();
                }

                private boolean isTodoBeanInData() {
                    if (todoItems.size() > 2 && todoItems.get(1).viewType == TODOITEM) {
                        return true;
                    } else {
                        return false;
                    }
                }

                @Override
                public void onFailure(Call<AddToDoResponse> call, Throwable t) {
                    Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private int findLastTodoDataPos() {
        for (int i = 0; i < todoItems.size(); i++) {
            if (todoItems.get(i).viewType == TODOITEM && todoItems.get(i + 1).viewType != TODOITEM) {
                return i;
            }
        }
        return 0;
    }

    private void deleteData(final int position) {
        RetrofitClient.getInstance().getService(APIService.class).deleteToDo(todoItems.get(position).id).enqueue(new Callback<AddToDoResponse>() {
            @Override
            public void onResponse(Call<AddToDoResponse> call, Response<AddToDoResponse> response) {
                todoItems.remove(position);
                TodoAdapter.this.notifyDataSetChanged();
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddToDoResponse> call, Throwable t) {
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

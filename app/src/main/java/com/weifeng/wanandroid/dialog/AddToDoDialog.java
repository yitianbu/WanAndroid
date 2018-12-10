package com.weifeng.wanandroid.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.model.TodoBean;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.response.AddToDoResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @anthor weifeng
 * @time 2018/12/5 下午5:21
 */
public class AddToDoDialog extends DialogFragment {
    private EditText editTitle,editContent,editTime;
    private TextView tvCancel,tvSave;
    private OnDataAddSuccessListener onDataAddSuccessListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_add_todo,container);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.8), (int) (dm.heightPixels * 0.6));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTitle = view.findViewById(R.id.edit_title);
        editContent = view.findViewById(R.id.edit_content);
        editTime = view.findViewById(R.id.edit_time);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvSave = view.findViewById(R.id.tv_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title="";
                String content="";
                String time="";
                if(editTitle.getText()!=null){
                    title =editTitle.getText().toString();
                }
                if(editContent.getText()!=null){
                    content = editContent.getText().toString();
                }
                if(editTime.getText()!=null){
                    time = editTime.getText().toString();
                }
                RetrofitClient.getInstance().getService(APIService.class).addToDo(title,content,time,0,0).enqueue(new Callback<AddToDoResponse>() {
                    @Override
                    public void onResponse(Call<AddToDoResponse> call, Response<AddToDoResponse> response) {
                        Toast.makeText(AddToDoDialog.this.getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
                        AddToDoDialog.this.dismiss();
                        if(onDataAddSuccessListener!=null){
                            onDataAddSuccessListener.onDataAddSuccess(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<AddToDoResponse> call, Throwable t) {
                        Toast.makeText(AddToDoDialog.this.getActivity(),"添加失败，请稍后重试",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToDoDialog.this.dismiss();
            }
        });
        editTime.setText(getDataString());
    }

    private String getDataString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public void setOnDataAddSuccessListener(OnDataAddSuccessListener onDataAddSuccessListener) {
        this.onDataAddSuccessListener = onDataAddSuccessListener;
    }

    public interface OnDataAddSuccessListener{
         void onDataAddSuccess(TodoBean todoBean);
    }
}

package com.weifeng.wanandroid.activity;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends Activity {
    private static final int REGISTER_SUCCESS= 0;

    private EditText passwordEdit, userNameEdit;
    private Button registerBtn;
    private ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBtn = findViewById(R.id.btn_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(userNameEdit.getText())){
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordEdit.getText())) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }


                RetrofitClient.getInstance().getService(APIService.class).postRegister(userNameEdit.getText().toString(), passwordEdit.getText().toString(), passwordEdit.getText().toString()).enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if(REGISTER_SUCCESS == response.body().errorCode) {
                            Toast.makeText(RegisterActivity.this, "success"+response.toString(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this, response.body().errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
        passwordEdit = findViewById(R.id.et_password);
        userNameEdit = findViewById(R.id.et_user_name);
        backBtn = findViewById(R.id.img_back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
    }


}

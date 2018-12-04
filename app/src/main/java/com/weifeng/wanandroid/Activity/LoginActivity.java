package com.weifeng.wanandroid.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.response.LoginResponse;
import com.weifeng.wanandroid.utils.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    private static final int LOGIN_SUCCESS = 0;


    private Button loginBtn, registerBtn;
    private EditText passwordEdit, userNameEdit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initStatus();

    }

    private void initStatus() {
        if(!TextUtils.isEmpty(Preferences.getInstance().getUserName()) && Preferences.getInstance().getCookies()!=null && Preferences.getInstance().getCookies().size()>0){
            userNameEdit.setText(Preferences.getInstance().getUserName());
            passwordEdit.setVisibility(View.GONE);
            loginBtn.setText("退出登陆");
        }else {
            passwordEdit.setVisibility(View.VISIBLE);
            loginBtn.setText("登陆");
        }
    }

    private void initView() {
        loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userNameEdit.getText())) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordEdit.getText())) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }


                RetrofitClient.getInstance().getService(APIService.class).postLogin(userNameEdit.getText().toString(), passwordEdit.getText().toString()).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (LOGIN_SUCCESS == response.body().errorCode) {
                            Toast.makeText(LoginActivity.this, "success" + response.toString(), Toast.LENGTH_SHORT).show();
                            Preferences.getInstance().setUserName(userNameEdit.getText().toString());
                            Preferences.getInstance().setUserPassword(passwordEdit.getText().toString());
                        } else {
                            Toast.makeText(LoginActivity.this, response.body().errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        registerBtn = findViewById(R.id.btn_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        passwordEdit = findViewById(R.id.et_password);
        userNameEdit = findViewById(R.id.et_user_name);
    }
}


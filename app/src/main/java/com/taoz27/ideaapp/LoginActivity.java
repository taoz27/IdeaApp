package com.taoz27.ideaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.models.MyInfo;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

/**
 * Created by taoz27 on 2017/11/16.
 */

public class LoginActivity extends BaseActivity{
    String title="登录";

    TextView email,password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupToolBar(title,false,null);
        setupViews();
    }

    void setupViews(){
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailS=email.getText().toString(),passwordS=password.getText().toString();
                if (emailS.equals("")||passwordS.equals("")){
                    Toast.makeText(LoginActivity.this,"请输入邮箱或密码",Toast.LENGTH_SHORT).show();
                }else {
                    login(emailS,passwordS);
                }
            }
        });
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.forget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,FindPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

//    Handler handler=new Handler();

    private void login(String email, String passwordS){
        MyHttp.Post(Urls.UserLogin)
                .params("email",email)
                .params("password",passwordS)
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<String> res= JsonUtils.fromJsonObject(response,String.class);
                        if (res.getStatus()==1){//登录成功
                            MyApplication.login=true;
                            MyHttp.Get(Urls.UserGetUserInfo)
                                    .execute(new MyHttpRequestListener() {
                                        @Override
                                        public void onSuccess(String response) {
                                            BaseResponse<MyInfo> res=JsonUtils.fromJsonObject(response,MyInfo.class);
                                            if (res.getStatus()==1){
                                                MyApplication.myInfo=res.getData();
                                            }
                                        }
                                    });
                            MainActivity.showMsg(LoginActivity.this,res.getMsg());
                            finish();
                        }else {
                            MainActivity.showMsg(LoginActivity.this,res.getMsg());
                            MainActivity.doCallBack(new MainActivity.HandleCallBack() {
                                @Override
                                public void doThings() {
                                    password.setText("");
                                }
                            });
                        }
                    }
                });
    }

//    private Runnable loginError=new Runnable() {
//        @Override
//        public void run() {
//            Toast.makeText(LoginActivity.this,"邮箱或密码错误",Toast.LENGTH_SHORT).show();
//            password.setText("");
//        }
//    };
//    private Runnable loginSuccess=new Runnable() {
//        @Override
//        public void run() {
//            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
//            finish();
//        }
//    };
}

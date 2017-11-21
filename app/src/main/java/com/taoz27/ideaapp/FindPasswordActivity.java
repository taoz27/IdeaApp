package com.taoz27.ideaapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

/**
 * Created by taoz27 on 2017/11/16.
 */

public class FindPasswordActivity extends BaseActivity{
    String title="找回密码";

    TextView email,question,answer,password,rePassword;
    String quesS="",emailS="",token="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        setupToolBar(title,false,null);
        setupViews();
    }

    private void setupViews() {
        email=findViewById(R.id.email);
        question=findViewById(R.id.question);
        answer=findViewById(R.id.answer);
        password=findViewById(R.id.password);
        rePassword=findViewById(R.id.password_r);
        findViewById(R.id.get_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailS=email.getText().toString();
                if (emailS.equals("")){
                    Toast.makeText(FindPasswordActivity.this,"请输入邮箱",Toast.LENGTH_SHORT).show();
                }else {
                    MyHttp.Post(Urls.UserForgetGetQuestion)
                            .params("email", emailS)
                            .execute(new MyHttpRequestListener() {
                                @Override
                                public void onSuccess(String response) {
                                    BaseResponse<String> res = JsonUtils.fromJsonObject(response, String.class);
                                    if (res.getStatus() == 1) {
                                        quesS = res.getData();
                                        MainActivity.doCallBack(new MainActivity.HandleCallBack() {
                                            @Override
                                            public void doThings() {
                                                question.setText(quesS);
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });
        findViewById(R.id.find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ansS=answer.getText().toString();
                final String passwordS=password.getText().toString();
                if (ansS.equals("")||passwordS.equals("")||!rePassword.getText().toString().equals(passwordS)){
                    Toast.makeText(FindPasswordActivity.this,"请输入全部内容",Toast.LENGTH_SHORT).show();
                }else {
                    MyHttp.Post(Urls.UserForgetCheckAnswer)
                            .params("email", emailS)
                            .params("question",quesS)
                            .params("answer",ansS)
                            .execute(new MyHttpRequestListener() {
                                @Override
                                public void onSuccess(String response) {
                                    BaseResponse<String> res = JsonUtils.fromJsonObject(response, String.class);
                                    if (res.getStatus() == 1) {
                                        token=res.getData();
                                        MyHttp.Post(Urls.UserForgetResetPassword)
                                                .params("email",emailS)
                                                .params("newpassword",passwordS)
                                                .params("token",token)
                                                .execute(new MyHttpRequestListener() {
                                                    @Override
                                                    public void onSuccess(String response) {
                                                        BaseResponse<String> res=JsonUtils.fromJsonObject(response,String.class);
                                                        MainActivity.showMsg(FindPasswordActivity.this,res.getMsg());
                                                    }
                                                });
                                    }else {
                                        MainActivity.showMsg(FindPasswordActivity.this,res.getMsg());
                                    }
                                }
                            });
                }
            }
        });
    }

//    Handler handler=new Handler();
//
//    Runnable setQuesText=new Runnable() {
//        @Override
//        public void run() {
//            question.setText(quesS);
//        }
//    };
//    Runnable findToast=new Runnable() {
//        @Override
//        public void run() {
//            Toast.makeText(FindPasswordActivity.this,findMsg,Toast.LENGTH_SHORT).show();
//        }
//    };
}

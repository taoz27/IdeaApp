package com.taoz27.ideaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.models.MyInfo;
import com.taoz27.ideaapp.models.RegisterInfo;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

/**
 * Created by taoz27 on 2017/11/16.
 */

public class RegisterActivity extends BaseActivity{
    String title="注册";

    EditText email,pass,passR,ins,question,answer;
    ImageView emailIv;
    String et,pt,prt,it,qt,at;

    boolean emailValid=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupToolBar(title,false,null);
        setupViews();
    }

    void setupViews(){
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        passR=findViewById(R.id.re_password);
        ins=findViewById(R.id.institution);
        question=findViewById(R.id.question);
        answer=findViewById(R.id.answer);
        emailIv=findViewById(R.id.valid);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    checkEmail();
                }
            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et=email.getText().toString();
                pt=pass.getText().toString();
                prt=passR.getText().toString();
                it=ins.getText().toString();
                qt=question.getText().toString();
                at=answer.getText().toString();
                if (et.equals("")||pt.equals("")||prt.equals("")||
                        it.equals("")||qt.equals("")||at.equals("")){
                    Toast.makeText(RegisterActivity.this,"请补全信息",Toast.LENGTH_SHORT).show();
                }else if (!emailValid) {
                    Toast.makeText(RegisterActivity.this,"邮箱不合法或已存在",Toast.LENGTH_SHORT).show();
                }else if (!prt.equals(pt)) {
                    Toast.makeText(RegisterActivity.this,"密码输入不一致",Toast.LENGTH_SHORT).show();
                }else {
                    register();
                }
            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkEmail() {
        if (email.getText().toString().contains("@")){
            MyHttp.Post(Urls.UserCheckValid)
                    .params("email",email.getText().toString())
                    .execute(new MyHttpRequestListener() {
                        @Override
                        public void onSuccess(String response) {
                            BaseResponse res= JsonUtils.fromJsonObject(response, MyInfo.class);
                            if (res.getStatus()==1) {
                                emailValid = true;
                                MainActivity.doCallBack(new MainActivity.HandleCallBack() {
                                    @Override
                                    public void doThings() {
                                        emailIv.setImageResource(R.drawable.icon_ok_b);
                                    }
                                });
                            }else {
                                emailValid = false;
                                MainActivity.doCallBack(new MainActivity.HandleCallBack() {
                                    @Override
                                    public void doThings() {
                                        emailIv.setImageResource(R.drawable.icon_wrong_r);
                                    }
                                });
                            }
                        }
                    });
        }else {
            emailValid = false;
            MainActivity.doCallBack(new MainActivity.HandleCallBack() {
                @Override
                public void doThings() {
                    emailIv.setImageResource(R.drawable.icon_wrong_r);
                }
            });
        }
    }

    private void register() {
        MyHttp.Post(Urls.UserRegister)
                .params("email",et)
                .params("password",pt)
                .params("institution",it)
                .params("question",qt)
                .params("answer",at)
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<RegisterInfo> res=JsonUtils.fromJsonObject(response,RegisterInfo.class);
                        if (res.getStatus()==1){
                            MainActivity.showMsg(RegisterActivity.this,res.getMsg());
                        }else {
                            MainActivity.showMsg(RegisterActivity.this,res.getMsg());
                        }
                    }
                });
    }

//    Handler handler=new Handler();
//    Runnable emV=new Runnable() {
//        @Override
//        public void run() {
//            emailIv.setImageResource(R.drawable.icon_ok_b);
//        }
//    };
//    Runnable emIv=new Runnable() {
//        @Override
//        public void run() {
//            emailIv.setImageResource(R.drawable.icon_wrong_r);
//        }
//    };
//    Runnable regS=new Runnable() {
//        @Override
//        public void run() {
//            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
//        }
//    };
//    Runnable regE=new Runnable() {
//        @Override
//        public void run() {
//            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
//        }
//    };
}

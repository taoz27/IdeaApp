package com.taoz27.ideaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.taoz27.ideaapp.models.MyInfo;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

/**
 * Created by taoz27 on 2017/11/16.
 */

public class MyInfoActivity extends BaseActivity{
    String title="我的信息";

    TextView insT,email,ins,question,answer;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        setupToolBar(title,false,null);
        setupViews();
    }

    private void setupViews() {
        insT=findViewById(R.id.institution_t);
        email=findViewById(R.id.email);
        ins=findViewById(R.id.institution);
        question=findViewById(R.id.question);
        answer=findViewById(R.id.answer);
        btn=findViewById(R.id.modify_info);

        if (MyApplication.myInfo==null)return;
        MyInfo info=MyApplication.myInfo;
        insT.setText(info.getInstitution());
        email.setText(info.getEmail());
        ins.setText(info.getInstitution());
        question.setText(info.getQuestion());
        answer.setText(info.getAnswer());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MyInfoActivity.this,ModifyInfoActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyHttp.Post(Urls.UserLogout)
                        .execute(new MyHttpRequestListener() {
                            @Override
                            public void onSuccess(String response) {
                                MyApplication.login=false;
                                finish();
                            }
                        });

            }
        });
    }
}

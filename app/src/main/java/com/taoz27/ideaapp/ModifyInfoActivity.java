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
 * Created by taoz27 on 2017/11/20.
 */

public class ModifyInfoActivity extends BaseActivity{
    String title="修改信息";

    TextView ins,psw,pswR;
    String msg="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);

        setupToolBar(title, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setupViews();
    }

    private void setupViews() {
        ins=findViewById(R.id.institution);
        psw=findViewById(R.id.password);
        pswR=findViewById(R.id.password_r);
        ins.setText(MyApplication.myInfo.getInstitution());
        findViewById(R.id.modify_ins).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String insS=ins.getText().toString();
                String pswRS=pswR.getText().toString();
                if (insS.equals("")||pswRS.equals("")){
                    Toast.makeText(ModifyInfoActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                }else {
                    MyHttp.Post(Urls.UserUpdateInfo)
                            .params("password",pswRS)
                            .params("institution",insS)
                            .params("answer",MyApplication.myInfo.getAnswer())
                            .params("question",MyApplication.myInfo.getQuestion())
                            .execute(new MyHttpRequestListener() {
                                @Override
                                public void onSuccess(String response) {
                                    BaseResponse<String> res= JsonUtils.fromJsonObject(response,String.class);
                                    msg=res.getMsg();
                                    handler.post(showToast);
                                }
                            });
                }
            }
        });
        findViewById(R.id.modify_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPsw=psw.getText().toString();
                if (newPsw.equals("")){
                    Toast.makeText(ModifyInfoActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                }else {
                    MyHttp.Post(Urls.UserResetPassword)
                            .params("newpassword",newPsw)
                            .execute(new MyHttpRequestListener() {
                                @Override
                                public void onSuccess(String response) {
                                    BaseResponse<String> res= JsonUtils.fromJsonObject(response,String.class);
                                    msg=res.getMsg();
                                    handler.post(showToast);
                                }
                            });
                }
            }
        });
    }

    Handler handler=new Handler();
    Runnable showToast=new Runnable() {
        @Override
        public void run() {
            Toast.makeText(ModifyInfoActivity.this,msg,Toast.LENGTH_SHORT).show();
        }
    };
}

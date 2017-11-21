package com.taoz27.ideaapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.models.MyActivity;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

import java.util.List;

/**
 * Created by taoz27 on 2017/11/20.
 */

public class MySpaceActivity extends BaseActivity{
    String title="我的空间";

    MainRecycler recycler;
    List<MyActivity> activities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_space);

        setupToolBar(title,false,null);
        loadData();
    }

    private void loadData() {
        MyHttp.Post(Urls.ManageList)
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<List<MyActivity>> res= JsonUtils.fromJsonArray(response,MyActivity.class);
                        if (res.getStatus()==1){
                            activities=res.getData();
                            handler.post(setupViews);
                        }
                    }
                });
    }

    Handler handler=new Handler();
    Runnable setupViews=new Runnable() {
        @Override
        public void run() {
            recycler=findViewById(R.id.main_recycler);
            recycler.setData(activities);
            recycler.setRemovable(true);
            recycler.notifyDataChanged();
        }
    };
}

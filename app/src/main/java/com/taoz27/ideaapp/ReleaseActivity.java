package com.taoz27.ideaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoz27 on 2017/11/16.
 */

public class ReleaseActivity extends BaseActivity {
    String title="发布活动";
    String[] lefts,rights;
    boolean[] oks;
    Long start=0L,end=0L;

    Recy recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        setupToolBar(title, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        setupViews();
    }

    private void add(){
        for (boolean b:oks)
            if (!b)
                return;
        MyHttp.Post(Urls.ManageAdd)
                .params("name",rights[0])
                .params("theme",rights[1])
                .params("content",rights[2])
                .params("lable",rights[3])
                .params("starttime",start)
                .params("endtime",end)
                .params("place",rights[6])
                .params("contact",rights[7])
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<Integer> res= JsonUtils.fromJsonObject(response,Integer.class);
                        if (res.getStatus()==1){
                            Log.e(this.toString(),"add success");
                        }else {
                            Log.e(this.toString(),"need login");
                        }
                    }
                });
    }

    private void setupViews() {
        recyclerView=findViewById(R.id.recycler);
        lefts=new String[8];rights=new String[8];oks=new boolean[8];
        lefts[0]="活动名称";
        lefts[1]="活动主题";
        lefts[2]="活动内容";
        lefts[3]="活动标签";
        lefts[4]="开始时间";
        lefts[5]="结束时间";
        lefts[6]="活动地点";
        lefts[7]="联系方式";
        for (int i=0;i<lefts.length;i++){
            rights[i]=("未填写");
            oks[i]=false;
        }
        recyclerView.setData(lefts,rights,oks,start,end);
    }
}

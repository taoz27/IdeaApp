package com.taoz27.ideaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.labels.LabelsView;
import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.models.MyActivityDetail;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

import java.util.ArrayList;

/**
 * Created by taoz27 on 2017/11/16.
 */

public class DetailActivity extends BaseActivity{
    String title="活动详情";
    int id;
    MyActivityDetail detail;
//    ArrayList<String> labelNames;

    TextView name,institution,day,time,theme,content,contact, place;
//    LabelsView labelsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_detail);

        setupToolBar(title,false,null);
        loadData();
    }

    private void loadData() {
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        MyHttp.Post(Urls.ManageSearch)
                .params("id",id)
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<MyActivityDetail> res= JsonUtils.fromJsonObject(response,MyActivityDetail.class);
                        detail=res.getData();
                        MainActivity.doCallBack(new MainActivity.HandleCallBack() {
                            @Override
                            public void doThings() {
                                setupViews();
                            }
                        });
                    }
                });
    }

    private void setupViews(){
        name=findViewById(R.id.name);
        institution=findViewById(R.id.institution);
        day=findViewById(R.id.day);
        time=findViewById(R.id.time);
        place =findViewById(R.id.place);
//        labelsView=findViewById(R.id.labels);
        theme=findViewById(R.id.theme);
        content=findViewById(R.id.content);
        contact=findViewById(R.id.contact);

        if (detail.getName()==null||detail.getName().equals("")) {
            Toast.makeText(DetailActivity.this, "无法获取数据", Toast.LENGTH_SHORT).show();
            return;
        }

        name.setText(detail.getName());
        institution.setText(detail.getInstitution());
        place.setText(detail.getPlace());
        theme.setText(detail.getTheme());
        String[] times=MyTimeUtils.getStartAndEndTime(detail.getStarttime(),detail.getEndtime());
        day.setText(times[0]);
        time.setText(times[1]);
        contact.setText(detail.getContact());
        content.setText(detail.getContent());
//        labelsView.setLabels(labelNames);
    }
}

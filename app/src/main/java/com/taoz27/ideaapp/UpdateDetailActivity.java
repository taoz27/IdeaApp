package com.taoz27.ideaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.models.Category2;
import com.taoz27.ideaapp.models.CategoryResponse;
import com.taoz27.ideaapp.models.MyActivityDetail;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoz27 on 2017/11/21.
 */

public class UpdateDetailActivity extends BaseActivity{
    String title="修改活动";

    int id;
    String name,theme,place,contact,content,lable;
    long startTime,endTime;
    List<CategoryResponse> categories;
    MyActivityDetail detail;

    EditText nameE,themeE,contactE,placeE,contentE;
    List<View> pages=new ArrayList<>();
    NoScrollViewPager mainLayout;
    List<LabelsView> labelViews;
    int selectLableId=0,curPage=0;
    boolean hasSelectTime=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        setupToolBar(title, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
        loadData();
    }

    public static void IntentToUpdate(Context context,int id){
        Intent intent=new Intent();
        intent.setClass(context,UpdateDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    void loadData(){
        MyHttp.Post(Urls.ManageSearch)
                .params("id",id)
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<MyActivityDetail> res=JsonUtils.fromJsonObject(response,MyActivityDetail.class);
                        if (res.getStatus()==1){
                            detail=res.getData();
                            MainActivity.doCallBack(new MainActivity.HandleCallBack() {
                                @Override
                                public void doThings() {
                                    setupViews();
                                }
                            });
                        }
                    }
                });
    }

    void next(){
        if (curPage==0){
            name=nameE.getText().toString();theme=themeE.getText().toString();
            place=placeE.getText().toString();contact=contactE.getText().toString();
            content=contentE.getText().toString();
            if (name==null||name.equals("")||theme==null||theme.equals("")||place==null||place.equals("")||
                    contact==null||contact.equals("")||content==null||content.equals(""))
                return;
            mainLayout.setCurrentItem(++curPage);
        }else if (curPage==1){
            if (selectLableId==0)return;
            if (hasSelectTime){
                add();
                return;
            }
            new SingleDateAndTimePickerDialog.Builder(this)
                    .title("请选择开始时间")
                    .defaultDate(new Date(detail.getStarttime()))
                    .listener(new SingleDateAndTimePickerDialog.Listener() {
                        @Override
                        public void onDateSelected(Date date) {
                            startTime=date.getTime();
                            new SingleDateAndTimePickerDialog.Builder(UpdateDetailActivity.this)
                                    .title("请选择结束时间")
                                    .defaultDate(new Date(detail.getEndtime()))
                                    .listener(new SingleDateAndTimePickerDialog.Listener() {
                                        @Override
                                        public void onDateSelected(Date date) {
                                            endTime=date.getTime();
                                            hasSelectTime=true;
                                        }
                                    }).display();
                        }
                    }).display();
        }
    }

    private void add(){
//        for (boolean b:oks)
//            if (!b)
//                return;
        MyHttp.Post(Urls.ManageAlter)
                .params("id",id)
                .params("name",name)
                .params("theme",theme)
                .params("content",content)
                .params("lable",lable)
                .params("starttime",startTime)
                .params("endtime",endTime)
                .params("place",place)
                .params("contact",contact)
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<Integer> res= JsonUtils.fromJsonObject(response,Integer.class);
                        if (res.getStatus()==1){
                            MainActivity.showMsg(UpdateDetailActivity.this,res.getMsg());
                            Log.e(this.toString(),"add success");
                        }else {
                            MainActivity.showMsg(UpdateDetailActivity.this,res.getMsg());
                            Log.e(this.toString(),"need login");
                        }
                    }
                });
    }

    private void setupViews() {
//        recyclerView=findViewById(R.id.recycler);
//        lefts=new String[8];rights=new String[8];oks=new boolean[8];
//        lefts[0]="活动名称";
//        lefts[1]="活动主题";
//        lefts[2]="活动内容";
//        lefts[3]="活动标签";
//        lefts[4]="开始时间";
//        lefts[5]="结束时间";
//        lefts[6]="活动地点";
//        lefts[7]="联系方式";
//        for (int i=0;i<lefts.length;i++){
//            rights[i]=("未填写");
//            oks[i]=false;
//        }
//        recyclerView.setData(lefts,rights,oks,start,end);
        mainLayout=findViewById(R.id.main_layout);
        View page0= LayoutInflater.from(this).inflate(R.layout.test_rel,mainLayout,false);
        nameE=page0.findViewById(R.id.name);
        themeE=page0.findViewById(R.id.theme);
        placeE=page0.findViewById(R.id.place);
        contactE=page0.findViewById(R.id.contact);
        contentE=page0.findViewById(R.id.content);
        nameE.setText(detail.getName());
        themeE.setText(detail.getTheme());
        placeE.setText(detail.getPlace());
        contactE.setText(detail.getContact());
        contentE.setText(detail.getContent());
        pages.add(page0);

        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        if (categories==null){
            categories=MyApplication.categories;
        }
        labelViews=new ArrayList<>();
        for (int i=0;i<categories.size();i++){
            CategoryResponse category=categories.get(i);
            final ArrayList<Integer> ids=new ArrayList<>();
            final int tag=i;
            View labelsL=getLayoutInflater().inflate(R.layout.z_labels_l,layout,false);
            TextView type=labelsL.findViewById(R.id.type);
            LabelsView labelsView=labelsL.findViewById(R.id.labels);
            type.setText(category.getDesc());
            ArrayList<String> labelNames=new ArrayList<>();
            for (Category2 c2:category.getCategoryIdInfo2s()){
                labelNames.add(c2.getDesc());
                ids.add(c2.getId());
            }
            labelsView.setTag(i);
            labelsView.setLabels(labelNames);
            labelsView.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
                @Override
                public void onLabelSelectChange(View label, String labelText, boolean isSelect, int position) {
                    if (isSelect) {
                        lable=labelText;
                        selectLableId=ids.get(position);
                        for (LabelsView v:labelViews){
                            if ((int)(v.getTag())!=tag)
                                v.clearAllSelect();
                        }
                    }
                }
            });
            labelViews.add(labelsView);
            layout.addView(labelsL);
        }
        pages.add(layout);

        mainLayout.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(pages.get(position));
                return pages.get(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (curPage==1)
            mainLayout.setCurrentItem(--curPage);
        else {
            super.onBackPressed();
        }
    }
}
package com.taoz27.ideaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.labels.LabelsView;
import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.models.Category2;
import com.taoz27.ideaapp.models.CategoryResponse;
import com.taoz27.ideaapp.models.MyActivity;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    View login,filter,search;
    NavigationView leftNav, rightNav;
    ViewGroup rightNavHeader,leftNavHeader;
    SwipeRefreshLayout refreshLayout;
    MainRecycler recycler;

    List<CategoryResponse> categories;
    List<MyActivity> activities=new ArrayList<>();
    boolean containLeftNav=false;
    ArrayList<LabelsView> labelViews;
    int selectLableId=0;
    String type="",desc="";

    /**
     * 全局showToast，复用
     * */
    public static String msg="";
    public static Context context;
    public static Handler handler=new Handler();
    public static HandleCallBack callBack;
    public static Runnable showMsg=new Runnable() {
        @Override
        public void run() {
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }
    };
    public static Runnable doCallback=new Runnable() {
        @Override
        public void run() {
            if (callBack!=null)
                callBack.doThings();
        }
    };
    public static void showMsg(Context c,String m){
        context=c;msg=m;
        handler.post(showMsg);
    }
    public static void doCallBack(HandleCallBack hCallBack){
        callBack=hCallBack;
        handler.post(doCallback);
    }

    public void setSearchFilter(String type,String desc){
        this.type=type;this.desc=desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);

        setSearchFilter("content","");
        loadData();
        loadCategories();
        setupViews();
    }

    public void loadData(){
        if (refreshLayout!=null) {
            refreshLayout.setRefreshing(true);
        }
        MyHttp.Post(Urls.Search)
                .params("type",type)
                .params("desc",desc)
                .params("page",0)
                .params("pagesize",20)
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<List<MyActivity>> res= JsonUtils.fromJsonArray(response,MyActivity.class);
                        if (res.getStatus()==1) {
                            activities.clear();
                            activities.addAll(res.getData());
                            if (recycler==null) {
                                setupRecycler();
                            }else {
                                doCallBack(new HandleCallBack() {
                                    @Override
                                    public void doThings() {
                                        recycler.notifyDataChanged();
                                    }
                                });
                            }
                        }
                        if (refreshLayout!=null) {
                            doCallBack(new HandleCallBack() {
                                @Override
                                public void doThings() {
                                    refreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    }
                });
    }

    void loadCategories(){
        categories=MyApplication.categories;
    }

    void setupViews(){
        setupDrawer();
        setupNav();
        setupToolbar();
//        setupRecycler();//数据加载完毕之后才能配置
        setupRefresh();
    }

    void setupToolbar(){
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open_drawer,R.string.close_drawer);
//        drawer.setDrawerListener(toggle);
        getSupportActionBar().setTitle("");

        login=findViewById(R.id.login);
        filter=findViewById(R.id.filter);
//        search=findViewById(R.id.search);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.login){
                    if (!containLeftNav)Toast.makeText(MainActivity.this,"LeftNav Error!",Toast.LENGTH_SHORT).show();
                    else {
                        drawer.openDrawer(Gravity.START);
                    }
                }else {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.END);
            }
        });
    }

    void setupRecycler(){
        recycler=findViewById(R.id.main_recycler);
        recycler.setData(activities);
        recycler.setBanner();
    }

    void setupRefresh(){
        refreshLayout=findViewById(R.id.refresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    void setupLeftNav(){
        leftNav =(NavigationView) LayoutInflater.from(this).inflate(R.layout.left_nav,drawer,false);
        leftNavHeader=(ViewGroup) leftNav.getHeaderView(0);
        leftNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(Gravity.START);
                switch (item.getItemId()){
                    case R.id.menu_nav_release:
                        Intent intent=new Intent();
                        intent.setClass(MainActivity.this,ReleaseActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_nav_mine:
                        Intent intent1=new Intent();
                        intent1.setClass(MainActivity.this,MySpaceActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_nav_setting:
                        break;
                    case R.id.menu_nav_exit:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                }
                return true;
            }
        });
        leftNavHeader.findViewById(R.id.head_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,MyInfoActivity.class);
                startActivity(intent);
            }
        });
        TextView ins=leftNavHeader.findViewById(R.id.institution);
        if (MyApplication.login&&MyApplication.myInfo!=null)
            ins.setText(MyApplication.myInfo.getInstitution());

        if (MyApplication.login){
            drawer.addView(leftNav);
            containLeftNav=true;
        }
    }

    void setupRightNav(){
        rightNav =findViewById(R.id.nav_view_r);
        rightNavHeader=(ViewGroup) rightNav.getHeaderView(0);
        ViewGroup labelsGroup=rightNavHeader.findViewById(R.id.linear);

        TextView clear,ensure;
        clear=rightNavHeader.findViewById(R.id.clear);
        ensure=rightNavHeader.findViewById(R.id.ensure);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (LabelsView l:labelViews) {
                    l.clearAllSelect();
                }
            }
        });
        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSearchFilter("classfiy",""+selectLableId);
                loadData();
                drawer.closeDrawer(Gravity.END);
              }
        });

        if (categories==null)return;
        labelViews=new ArrayList<>();
        for (int i=0;i<categories.size();i++){
            CategoryResponse category=categories.get(i);
            final ArrayList<Integer> ids=new ArrayList<>();
            final int tag=i;
            View labelsL=getLayoutInflater().inflate(R.layout.z_labels_l,rightNavHeader,false);
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
                        selectLableId=ids.get(position);
                        for (LabelsView v:labelViews){
                            if ((int)(v.getTag())!=tag)
                                v.clearAllSelect();
                        }
//                        Toast.makeText(Ma/inActivity.this, "click_id:"+ids.get(position) , Toast.LENGTH_SHORT).show();
                    }
                }
            });
            labelViews.add(labelsView);
            labelsGroup.addView(labelsL);
        }

        WindowManager wm = this.getWindowManager();
        int screenH = wm.getDefaultDisplay().getHeight();
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        labelsGroup.measure(w, h);
        int groutH = labelsGroup.getMeasuredHeight();
        Log.e(this.toString(),"height:"+groutH+"    "+screenH);

        int height;
        if (groutH/2+30>screenH-100)height=groutH/2+30;
        else height=screenH-100;

        View view=rightNavHeader.findViewById(R.id.ensure_linear);
        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams) view.getLayoutParams();
        params.topMargin=height;
        view.setLayoutParams(params);
    }

    void setupNav(){
        setupLeftNav();
        setupRightNav();
    }

    void setupDrawer(){
        drawer=findViewById(R.id.drawer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.login&&!containLeftNav){
            drawer.addView(leftNav);
            TextView ins=leftNavHeader.findViewById(R.id.institution);
            if (MyApplication.myInfo!=null)
                ins.setText(MyApplication.myInfo.getInstitution());
            containLeftNav=true;
        }
        if (!MyApplication.login&&containLeftNav){
            drawer.removeView(leftNav);
            containLeftNav=false;
        }
    }

    public interface HandleCallBack{
        void doThings();
    }
}

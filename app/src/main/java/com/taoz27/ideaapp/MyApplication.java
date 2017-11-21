package com.taoz27.ideaapp;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.models.CategoryResponse;
import com.taoz27.ideaapp.models.MyInfo;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoz27 on 2017/11/17.
 */

public class MyApplication extends Application{
    public static String cookie;
    public static MyInfo myInfo;
    public static boolean login=false;
    public static List<CategoryResponse> categories;
    public static List<Bitmap> backs=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        loadBitmap();
        login();
        getAllCategories();
    }

    private void login(){
        MyHttp.Post(Urls.UserLogin)
                .params("email","aa@163.com")
                .params("password","aaaaaa")
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<String> res= JsonUtils.fromJsonObject(response,String.class);
                        if (res.getStatus()==1){//登录成功
                            login=true;
                            MyHttp.Get(Urls.UserGetUserInfo)
                                    .execute(new MyHttpRequestListener() {
                                        @Override
                                        public void onSuccess(String response) {
                                            BaseResponse<MyInfo> res=JsonUtils.fromJsonObject(response,MyInfo.class);
                                            if (res.getStatus()==1){
                                                myInfo=res.getData();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
    private void loadBitmap(){
        backs.add(BitmapFactory.decodeResource(getResources(),R.drawable.back0));
        backs.add(BitmapFactory.decodeResource(getResources(),R.drawable.back1));
        backs.add(BitmapFactory.decodeResource(getResources(),R.drawable.back2));
        backs.add(BitmapFactory.decodeResource(getResources(),R.drawable.back3));
        backs.add(BitmapFactory.decodeResource(getResources(),R.drawable.back4));
        backs.add(BitmapFactory.decodeResource(getResources(),R.drawable.back5));
    }
    private void getAllCategories(){
        MyHttp.Get(Urls.GetAllCategory)
                .execute(new MyHttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<List<CategoryResponse>> res= JsonUtils.fromJsonArray(response,CategoryResponse.class);
                        if (res.getStatus()==1) {
                            categories = res.getData();
                        }else {
                            Log.e(this.toString(), res.getMsg());
                        }
                    }
                });
    }
}

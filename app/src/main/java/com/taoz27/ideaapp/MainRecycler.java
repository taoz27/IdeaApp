package com.taoz27.ideaapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.taoz27.ideaapp.models.MyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoz27 on 2017/11/16.
 */

public class MainRecycler extends RecyclerView{
    MainAdapter adapter;
    List<MyActivity> activities;

    public void setBanner(){
        adapter.setBanner();
    }

    public void setRemovable(boolean able){
        adapter.setRemovable(able);
    }

    public void setData(List<MyActivity> activities){
        this.activities.clear();
        this.activities.addAll(activities);
    }

    public void notifyDataChanged(){
        adapter.notifyDataSetChanged();
    }

    void init(Context context){
        this.setLayoutManager(new LinearLayoutManager(context));
        this.activities=new ArrayList<>();
        adapter=new MainAdapter(getContext(),this.activities);
        this.setAdapter(adapter);
    }

    public MainRecycler(Context context) {
        super(context);
        init(context);
    }

    public MainRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
}

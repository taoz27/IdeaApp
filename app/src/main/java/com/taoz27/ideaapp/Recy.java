package com.taoz27.ideaapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by taoz27 on 2017/11/18.
 */

public class Recy extends RecyclerView{
    String[] lefts,rights;
    boolean[] oks;
    Long starttime,endtime;

    Date date;
    Context context;
    MyAdapter adapter;

    public void setData(String[] lefts,String[] rights,boolean[] oks,Long starttime,Long endtime){
        this.lefts=lefts;
        this.rights=rights;
        this.oks=oks;
        this.starttime=starttime;
        this.endtime=endtime;
    }

    private void init(Context context){
        this.context=context;
        this.setLayoutManager(new LinearLayoutManager(context));
        adapter=new MyAdapter();
        this.setAdapter(adapter);
    }

    public Recy(Context context) {
        super(context);
        init(context);
    }

    public Recy(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.recy_item,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            MyViewHolder mHolder=(MyViewHolder)holder;
            mHolder.left.setText(lefts[position]);
            mHolder.right.setText(rights[position]);
            mHolder.right.setTextColor(oks[position]? Color.BLACK:Color.RED);
            mHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lefts[position].contains("时间")){
                        final int rp=lefts[position+1].contains("时间")?position:position-1;
                        final SimpleDateFormat format=new SimpleDateFormat("yy年MM月d日hh时mm分");

                        Calendar cal=Calendar.getInstance();
                        date=cal.getTime();
                        final int year=cal.get(Calendar.YEAR),month=cal.get(Calendar.MONTH),day=cal.get(Calendar.DAY_OF_MONTH),
                                hour=cal.get(Calendar.HOUR_OF_DAY),minute=cal.get(Calendar.MINUTE);
                        final TimePickerDialog tDialog2=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                date.setHours(i);date.setMinutes(i1);
                                rights[rp+1]=format.format(date);
                                endtime=date.getTime();
                                oks[rp]=true;oks[rp+1]=true;
                                notifyDataSetChanged();
                            }
                        },hour,minute,true);
                        final DatePickerDialog dDialog2=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                date.setYear(i);date.setMonth(i1);date.setDate(i2);
                                tDialog2.show();
                            }
                        },year, month,day);
                        final TimePickerDialog tDialog1=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                date.setHours(i);date.setMinutes(i1);
                                rights[rp]=format.format(date);
                                starttime=date.getTime();
                                dDialog2.show();
                            }
                        },hour,minute,true);
                        DatePickerDialog dDialog1=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                date.setYear(i);date.setMonth(i1);date.setDate(i2);
                                tDialog1.show();
                            }
                        },year, month,day);
                        dDialog1.show();
                    }else {
                        final EditText editText=new EditText(context);
                        editText.setText(rights[position]);
                        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
                        dialog.setTitle(lefts[position]);
                        dialog.setView(editText);
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                rights[position]=editText.getText().toString();
                                oks[position]=true;
                                notifyDataSetChanged();
                            }
                        }).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (lefts==null)return 0;
            return lefts.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView left,right;

            public MyViewHolder(View itemView) {
                super(itemView);
                left=itemView.findViewById(R.id.left);
                right=itemView.findViewById(R.id.right);
            }
        }
    }
}

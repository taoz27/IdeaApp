package com.taoz27.ideaapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.taoz27.ideaapp.models.BaseResponse;
import com.taoz27.ideaapp.models.MyActivity;
import com.taoz27.ideaapp.net.JsonUtils;
import com.taoz27.ideaapp.net.MyHttp;
import com.taoz27.ideaapp.net.MyHttpRequestListener;
import com.taoz27.ideaapp.net.Urls;
import com.youth.banner.Banner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by taoz27 on 2017/11/15.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int normalType=0;
    private final int headerType=1;

    private boolean hasHead=false;
    private boolean removable=false;

    private Context context;
    private List<MyActivity> activities;

    public void setBanner(){
        hasHead=true;
    }

    public void setRemovable(boolean able){
        removable=able;
    }

    public MainAdapter(Context context,List<MyActivity> activities){
        this.context=context;
        this.activities=activities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        View view;
        if (viewType==headerType){
            view= LayoutInflater.from(context).inflate(R.layout.header_item,parent,false);
            holder=new HeaderItemHolder(view);
        }else if (viewType==normalType) {
            view = LayoutInflater.from(context).inflate(R.layout.normal_item, parent, false);
            holder=new NormalItemHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==headerType){
            final HeaderItemHolder hHolder=(HeaderItemHolder)holder;
            hHolder.search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String desc=hHolder.searchE.getText().toString();
                    if (desc!=null){
                        ((MainActivity)context).setSearchFilter("content",desc);
                        ((MainActivity)context).loadData();
                    }
                }
            });
            hHolder.banner.setImageLoader(new GlideLoader());
            hHolder.banner.setImages(MyApplication.backs);
            hHolder.banner.start();
        }else if (getItemViewType(position)==normalType) {
            NormalItemHolder nHolder = (NormalItemHolder) holder;
            int rp = hasHead?position-1:position;
//            nHolder.headView.setImageResource(R.mipmap.ic_launcher);
            final MyActivity activity=activities.get(rp);
            nHolder.backView.setImageBitmap(MyApplication.backs.get(rp%5));
            nHolder.insText.setText(activity.getInstitution());

            Calendar calendar=Calendar.getInstance();
            Date nowDate=calendar.getTime();
            nHolder.timeText.setText(MyTimeUtils.getTimeString(nowDate,activity.getCreatetime()));

            nHolder.nameText.setText(activity.getName());
            ArrayList<String> labels =new ArrayList<>();
            String[] ss=activity.getLable().split(";");
            labels.add(ss[0]);labels.add(ss[1]);
            nHolder.labelsView.setLabels(labels);
            nHolder.labelsView.setSelectType(LabelsView.SelectType.NONE);
            nHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.setClass(context,DetailActivity.class);
                    intent.putExtra("id",activity.getId());
                    context.startActivity(intent);
                }
            });
            if (removable){
                nHolder.deleteView.setVisibility(View.VISIBLE);
                nHolder.deleteView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyHttp.Post(Urls.ManageDelete)
                                .params("id",activity.getId())
                                .execute(new MyHttpRequestListener() {
                                    @Override
                                    public void onSuccess(String response) {
                                        BaseResponse<String> res=JsonUtils.fromJsonObject(response,String.class);
                                        if (res.getStatus()==1){
                                            MainActivity.showMsg(context,res.getMsg());
                                        }
                                    }
                                });
                    }
                });
                nHolder.editView.setVisibility(View.VISIBLE);
                nHolder.editView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UpdateDetailActivity.IntentToUpdate(context,activity.getId());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (hasHead)
            return activities.size()+1;
        else
            return activities.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHead&&position==0)return headerType;
        return normalType;
    }

    class NormalItemHolder extends RecyclerView.ViewHolder{
        CircleImageView headView;
        ImageView editView,deleteView,backView;
        TextView insText,timeText,nameText;
        LabelsView labelsView;

        public NormalItemHolder(View itemView) {
            super(itemView);
            headView =itemView.findViewById(R.id.image);
            editView=itemView.findViewById(R.id.modify_ac);
            deleteView=itemView.findViewById(R.id.delete);
            insText=itemView.findViewById(R.id.institution);
            timeText=itemView.findViewById(R.id.start_time);
            nameText=itemView.findViewById(R.id.name);
            labelsView=itemView.findViewById(R.id.labels);
            backView=itemView.findViewById(R.id.background);
        }
    }

    class HeaderItemHolder extends RecyclerView.ViewHolder{
        Banner banner;
        EditText searchE;
        ImageView search;

        public HeaderItemHolder(View itemView) {
            super(itemView);
            banner=itemView.findViewById(R.id.banner);
            search=itemView.findViewById(R.id.search);
            searchE=itemView.findViewById(R.id.search_edit);
        }
    }
}

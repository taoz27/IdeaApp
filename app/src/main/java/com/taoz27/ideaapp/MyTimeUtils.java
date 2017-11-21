package com.taoz27.ideaapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Magicwo on 2017/2/23.
 */

public class MyTimeUtils {
    public static String getTimeString(Date nowdate, long ago){
        String s;
        Date olddate=new Date(ago);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(olddate);
        int year1=calendar.get(Calendar.YEAR);
        int day1=calendar.get(Calendar.DAY_OF_YEAR);
        int hour1=calendar.get(Calendar.HOUR_OF_DAY);
        int min1=calendar.get(Calendar.MINUTE);

        calendar.setTime(nowdate);
        int year2=calendar.get(Calendar.YEAR);
        int day2=calendar.get(Calendar.DAY_OF_YEAR);
        int hour2=calendar.get(Calendar.HOUR_OF_DAY);
        int min2=calendar.get(Calendar.MINUTE);
        if (year1==year2){
            if ((day2-day1)==1)
            {
                return "昨天";
            }
            if (day1>=day2){
                if (hour1>=hour2){
                    if ((min2-min1)<5) {
                        return "刚刚";
                    }
                    return String.valueOf(min2-min1)+"分钟前";
                }
                return String.valueOf(hour2-hour1)+"小时前";
            }
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM.dd HH:mm");
            s=simpleDateFormat.format(ago);
            return s;
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd HH:mm");
        s=simpleDateFormat.format(ago);
        return s;
    }

    /**
     * 计算发现页面、活动详情页面所需要的的起止时间
     * taoz27
     * */
    public static String[] getStartAndEndTime(long start, long end){
        String[]strings=new String[2];
        if (start==0&&end==0){
            strings[0]="----";
            strings[1]="----";
            return strings;
        }
        Date startTime=new Date(start);
        Date endTime=new Date(end);
        SimpleDateFormat leftS=new SimpleDateFormat("yy年M月d日");
        SimpleDateFormat rightS=new SimpleDateFormat("HH:mm");
        SimpleDateFormat all=new SimpleDateFormat("yy年M月d日HH:mm");
//        Log.e("------------>",leftS.format(startTime)+"+"+leftS.format(endTime));

        if (start!=0&&end!=0) {
            if (leftS.format(startTime).equals(leftS.format(endTime))) {//活动在同一天内结束
                strings[0] = leftS.format(startTime);
                strings[1] = rightS.format(startTime) + "~" + rightS.format(endTime);
            } else {//活动不在一天内结束
                strings[0] = all.format(startTime);
                strings[1] = all.format(endTime);
            }
        }else {
            if (start==0){
                strings[0]="----";
                strings[1] = all.format(endTime);
            }
            if (end==0){
                strings[0] = all.format(startTime);
                strings[1]="----";
            }
        }
        return strings;
    }

}

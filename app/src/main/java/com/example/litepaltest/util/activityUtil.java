package com.example.litepaltest.util;


import android.app.Activity;

import com.example.litepaltest.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

//这个工具类我准备用来管理活动 用于实现活动的退出
public class activityUtil {

    public static List<Activity> activities = new ArrayList<>();


    //添加活动
    public static void addActivites(Activity activity){
        activities.add(activity);
    }


    public static void removeActivities(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }

    public static void returnLogin(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                if(activity.equals(LoginActivity.class)){
                    activities.add(activity);
                }else{
                    activity.finish();
                }
            }
        }
    }




}

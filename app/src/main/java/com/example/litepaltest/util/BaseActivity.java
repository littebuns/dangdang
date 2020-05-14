package com.example.litepaltest.util;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//这个类继承于AppCompatActivity  用于实现自动往自定义的活动管理器中添加
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityUtil.addActivites(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityUtil.removeActivities(this);
    }
}

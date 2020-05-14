package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.adapter.PhoneAdapter;
import com.example.litepaltest.adapter.ScheduleAdapter;
import com.example.litepaltest.entity.Schedule;
import com.example.litepaltest.entity.User;
import com.example.litepaltest.util.BaseActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.litepal.LitePal;

import java.util.List;

public class ScheduleUserActivity extends BaseActivity {

    List<Schedule> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_user);


        Toolbar toolbar = findViewById(R.id.user_schedule_toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //将toolbar的后退键给显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SharedPreferences preferences = getSharedPreferences("name",MODE_PRIVATE);
        final String name = preferences.getString("name","");

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataDao dataDao = new DataDao();
                scheduleList = dataDao.selectSchedule(name);

            }
        }).start();

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.user_schedule_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(scheduleList,ScheduleUserActivity.this);
        recyclerView.setAdapter(scheduleAdapter);


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.user_schedule_toolbar);
        collapsingToolbar.setTitle("工作安排表");




    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(ScheduleUserActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.entity.Schedule;
import com.example.litepaltest.util.BaseActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

public class ScheduleDetailActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        Toolbar toolbar = findViewById(R.id.schedule_detail_toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //将toolbar的后退键给显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView textView = (TextView) findViewById(R.id.schedule_detail_content);

        //从上一个活动取出任务的详情
        final Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        textView.setText(content);




        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.schedule_detail_toolbar);
        collapsingToolbar.setTitle("任务详情");

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.schedule_detail_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ScheduleDetailActivity.this);
                dialog.setTitle("友情提醒");
                dialog.setMessage("请确认你已完成任务");
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = intent.getIntExtra("id",1);

                        SharedPreferences preferences = getSharedPreferences("name",MODE_PRIVATE);
                        final String name = preferences.getString("name","");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DataDao dataDao = new DataDao();
                                dataDao.updataSchedule(name);
                            }
                        }).start();


                        Intent intent = new Intent(ScheduleDetailActivity.this,ScheduleUserActivity.class);
                        startActivity(intent);
                        Toast.makeText(ScheduleDetailActivity.this,"任务已完成",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return ;
                    }
                });
                dialog.show();

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

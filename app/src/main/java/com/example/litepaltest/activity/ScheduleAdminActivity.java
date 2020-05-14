package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.entity.Schedule;
import com.example.litepaltest.entity.User;
import com.example.litepaltest.util.BaseActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.icu.text.SimpleDateFormat;


import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ScheduleAdminActivity extends BaseActivity {
    private static final String TAG = "ScheduleAdminActivity";
    private Schedule schedule = new Schedule();
    private List<String> userName = new ArrayList<>();
    private String name;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_admin);

        Toolbar toolbar = findViewById(R.id.admin_schedule_toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //将toolbar的后退键给显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataDao dataDao = new DataDao();
                userName = dataDao.selectUserName();
            }
        }).start();
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: +++++++++++++" + userName.size() );
        NiceSpinner spinner=findViewById(R.id.nice_spinner);
        spinner.attachDataSource(userName);
        spinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                name = userName.get(position);
            }
        });


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.admin_schedule_toolbar);
        collapsingToolbarLayout.setTitle("任务分配");



        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.admin_schedule_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EditText nameText = (EditText) findViewById(R.id.admin_schedule_user);
                EditText contentText = (EditText) findViewById(R.id.admin_schedule_content);

//                String name = nameText.getText().toString();
                String content = contentText.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                Date date = new Date();
                schedule.setName(name);
                schedule.setContent(content);
                schedule.setFinished(0);
                schedule.setDate(simpleDateFormat.format(date));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataDao dataDao = new DataDao();
                        dataDao.addSchedule(schedule);


                    }
                }).start();

                Toast.makeText(ScheduleAdminActivity.this,"任务分配成功"+simpleDateFormat.format(date),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ScheduleAdminActivity.this,AdminActivity.class);
                startActivity(intent);
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

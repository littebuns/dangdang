package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminApproveDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_detail);

        Toolbar toolbar = findViewById(R.id.approve_detail_toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //将toolbar的后退键给显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView textName = (TextView) findViewById(R.id.approve_detail_name);
        TextView textDate = (TextView) findViewById(R.id.approve_detail_date);
        TextView textContent = (TextView) findViewById(R.id.approve_detail_content);

        //从上一个活动取出任务的详情
        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");
        final String content = intent.getStringExtra("content");
        textName.setText(name);
        textDate.setText(date);
        textContent.setText(content);



        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.approve_detail_toolbar);
        collapsingToolbar.setTitle("审批详情");

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.approve_detail_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminApproveDetailActivity.this);
                dialog.setTitle("友情提醒");
                dialog.setMessage("请确认通过申请");
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Schedule schedule = LitePal.find(Schedule.class,i);
//                        schedule.setFinished(1);
//                        schedule.save();
//                        ContentValues values = new ContentValues();
//                        values.put("finished",1);
//                        LitePal.update(Schedule.class,values,id);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DataDao dataDao = new DataDao();
                                dataDao.updataApprove(content);
                            }
                        }).start();


                        Intent intent = new Intent(AdminApproveDetailActivity.this,AdminApproveActivity.class);
                        startActivity(intent);
                        Toast.makeText(AdminApproveDetailActivity.this,"申请已通过",Toast.LENGTH_SHORT).show();
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

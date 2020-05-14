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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.adapter.ApproveAdapter;
import com.example.litepaltest.adapter.PhoneAdapter;
import com.example.litepaltest.entity.Approve;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UserApproveActivity extends AppCompatActivity {
    private List<Approve> approveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_approve);

        Toolbar toolbar = findViewById(R.id.phone_toolbar1);
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
                approveList = dataDao.selectUserApprove(name);

            }
        }).start();


        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.phone_recyclerview);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
       ApproveAdapter approveAdapter = new ApproveAdapter(UserApproveActivity.this,approveList);
        recyclerView.setAdapter(approveAdapter);


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.phone_toolbar);
        collapsingToolbar.setTitle("发起申请");

        ImageView imageView = (ImageView) findViewById(R.id.phone_imageView);
        imageView.setImageResource(R.drawable.approvebackground);


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.user_manage_add_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserApproveActivity.this,"添加申请",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserApproveActivity.this,UserAddApproveActivity.class);
                startActivity(intent);
            }
        });




    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(UserApproveActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

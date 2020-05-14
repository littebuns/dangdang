package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.adapter.PhoneAdapter;
import com.example.litepaltest.entity.User;
import com.example.litepaltest.util.BaseActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.List;

public class TelephoneActivity extends BaseActivity {

    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone);

        Toolbar toolbar = findViewById(R.id.phone_toolbar1);
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
                userList = dataDao.selectAllUser();

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
        PhoneAdapter phoneAdapter = new PhoneAdapter(userList,TelephoneActivity.this);
        recyclerView.setAdapter(phoneAdapter);


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.phone_toolbar);
        collapsingToolbar.setTitle("通讯录");

        ImageView imageView = (ImageView) findViewById(R.id.phone_imageView);
        imageView.setImageResource(R.drawable.phonebackground);






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

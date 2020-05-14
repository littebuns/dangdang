package com.example.litepaltest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.adapter.SignAdapter;
import com.example.litepaltest.entity.Sign;
import com.example.litepaltest.util.BaseActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.litepal.LitePal;

import java.util.List;

public class SignDetailActivity extends BaseActivity {
    private List<Sign> signList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_detail);
        //获取所有的打卡记录

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataDao dataDao = new DataDao();
                signList = dataDao.selectAllSign();
            }
        }).start();

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sign_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SignAdapter signAdapter = new SignAdapter(signList);
        recyclerView.setAdapter(signAdapter);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.sign_toolbar);
        collapsingToolbar.setTitle("打卡统计");

        ImageView imageView = (ImageView) findViewById(R.id.sign_imageView);
        imageView.setImageResource(R.drawable.sign_pic);
    }
}

package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.litepaltest.R;
import com.example.litepaltest.entity.User;
import com.example.litepaltest.util.BaseActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class NewsActivity extends BaseActivity {
    private static final String TAG = "NewsActivity";

    private String newsTitle;
    private String newsContent;
    private String newsPictureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        User user = new User();
//        user.setName("admin");
//        user.setPassword("123");
//        user.setRole(1);
//        user.save();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        newsTitle = intent.getStringExtra("newsTitle");
        newsContent = intent.getStringExtra("newsContent");
        newsPictureUrl = intent.getStringExtra("newsPitctureUrl");
        Log.d(TAG, "onCreate: "+ newsPictureUrl+newsTitle);
        //将toolbar作为ActionBar显示
        Toolbar toolbar = findViewById(R.id.news_toolbar);
        setSupportActionBar(toolbar);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.news_toolbar);
//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //将toolbar的后退键给显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //设置news的标题
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(newsTitle);

        //图片加载
        ImageView newsImageView = (ImageView) findViewById(R.id.news_imageView);

        Glide.with(this).load(newsPictureUrl).into(newsImageView);

        //
        TextView newsTextView = (TextView)findViewById(R.id.news_content_detail);
        newsTextView.setText(newsContent);

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

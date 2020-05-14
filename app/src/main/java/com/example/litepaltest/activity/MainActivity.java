package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.adapter.NewsAdapter;
import com.example.litepaltest.entity.News;
import com.example.litepaltest.entity.Schedule;
import com.example.litepaltest.util.BaseActivity;
import com.example.litepaltest.util.activityUtil;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private List<News> newsList = new ArrayList<>();
    private NewsAdapter adapter;
    private SwipeRefreshLayout refresh;
    private RecyclerView recyclerView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.list1);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                DataDao dataDao = new DataDao();
                newsList= dataDao.selectNews();
            }
        }).start();

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        recyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(newsList,MainActivity.this);

        recyclerView.setAdapter(adapter);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataDao dataDao = new DataDao();
                        newsList= dataDao.selectNews();
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new NewsAdapter(newsList,MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                refresh.setRefreshing(false);
                                Toast.makeText(MainActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).start();

            }

        });

        //导航抽屉的子项点击事件
        NavigationView navigationView =(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_address:
                        Intent intent = new Intent(MainActivity.this,LocationActivity.class);
                        startActivity(intent);
                        break;


                        //通讯录
                    case R.id.nav_phone:
                        Intent intentPhone = new Intent(MainActivity.this,TelephoneActivity.class);
                        startActivity(intentPhone);
                        break;

                        //待办事项
                    case R.id.nav_schedule:
                        Intent intentSchedule = new Intent(MainActivity.this, ScheduleUserActivity.class);
                        startActivity(intentSchedule);
                        break;

                        //我的审批
                    case R.id.nav_approve:
                        Intent intentApprove = new Intent(MainActivity.this,UserApproveActivity.class);
                        startActivity(intentApprove);
                        break;


                    case R.id.nav_exit:
                        activityUtil.finishAll();
                        Toast.makeText(MainActivity.this,"您已成功退出",Toast.LENGTH_SHORT).show();
                        break;


                    default:
                        break;
                }
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.delete:
                Toast.makeText(this,"you chick Delete",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}

package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.adapter.NewsAdapter;
import com.example.litepaltest.entity.News;
import com.example.litepaltest.util.BaseActivity;
import com.example.litepaltest.util.activityUtil;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends BaseActivity {
    private  RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private List<News> newsList = new ArrayList<>();
    private NewsAdapter adapter;
    private SwipeRefreshLayout refresh;
    private String searchText;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar)findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //滑动菜单
        drawerLayout = (DrawerLayout) findViewById(R.id.admin_layout);

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.list1);
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

        //每个news
         recyclerView = (RecyclerView) findViewById(R.id.admin_news_recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(newsList,AdminActivity.this);
        recyclerView.setAdapter(adapter);


        refresh = findViewById(R.id.admin_refresh);
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
                                adapter = new NewsAdapter(newsList,AdminActivity.this);
                                recyclerView.setAdapter(adapter);
                                refresh.setRefreshing(false);
                                Toast.makeText(AdminActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).start();

            }

        });


        //导航抽屉的子项点击事件
        NavigationView navigationView =(NavigationView)findViewById(R.id.admin_view);
        View header = navigationView.getHeaderView(0);
        TextView username = (TextView)header.findViewById(R.id.name);
        username.setText("管理员");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_schedule:
                        Intent intent = new Intent(AdminActivity.this,AddScheduleActivity.class);
                        startActivity(intent);
                        break;

                        //打卡统计
                    case R.id.sign_admin:
                        Intent intentSign = new Intent(AdminActivity.this,SignDetailActivity.class);
                        startActivity(intentSign);
                        break;

                        //任务分配
                    case R.id.admin_schedule:
                        Intent intentScheduleAdmin = new Intent(AdminActivity.this,ScheduleAdminActivity.class);
                        startActivity(intentScheduleAdmin);
                        break;

                        //切换用户
                    case R.id.change_user:
                        activityUtil.returnLogin();
                        Intent intentLogin = new Intent(AdminActivity.this,LoginActivity.class);
                        startActivity(intentLogin);
                        break;

                        //人员管理
                    case R.id.user_manage:
                        Intent intentUserMange = new Intent(AdminActivity.this,UserManageActivity.class);
                        startActivity(intentUserMange);
                        break;

                        //审批管理
                    case R.id.admin_approve:
                        Intent intentAdminApprove = new Intent(AdminActivity.this,AdminApproveActivity.class);
                        startActivity(intentAdminApprove);
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
        MenuItem searchItem = menu.findItem(R.id.serach_menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("请输入搜索内容");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //处理点击搜索
            @Override
            public boolean onQueryTextSubmit(final String query) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataDao dataDao = new DataDao();
                        newsList = dataDao.selectNewsByTitle(query);
                    }
                }).start();


                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                adapter = new NewsAdapter(newsList,AdminActivity.this);
                recyclerView.setAdapter(adapter);

                Toast.makeText(AdminActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                return true;
            }
            //搜索框文字变化监听
            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                return false;
            }
        });
        return true;


    }



    //toolbar子项的点击事件
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

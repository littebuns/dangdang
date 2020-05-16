package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.adapter.PhoneAdapter;
import com.example.litepaltest.adapter.UserManageAdapter;
import com.example.litepaltest.entity.User;
import com.example.litepaltest.util.BaseActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.List;

public class UserManageActivity extends BaseActivity {


    private String searchText;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        Toolbar toolbar = findViewById(R.id.user_manage_toolbar1);
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.user_manage_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        UserManageAdapter userManageAdapter = new UserManageAdapter(UserManageActivity.this,userList);
        recyclerView.setAdapter(userManageAdapter);


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.user_manage_toolbar);
        collapsingToolbar.setTitle("人员管理");

//        ImageView imageView = (ImageView) findViewById(R.id.user_manage_imageView);
//        imageView.setImageResource(R.drawable.usermanage);


        FloatingActionButton floatingActionButton =  (FloatingActionButton)findViewById(R.id.user_manage_add_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserManageActivity.this,"添加",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserManageActivity.this,AddUserActivity.class);
                startActivity(intent);
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
                        userList = dataDao.selectUser(query);
                    }
                }).start();


                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.user_manage_recyclerview);
                UserManageAdapter userManageAdapter = new UserManageAdapter(UserManageActivity.this,userList);
                recyclerView.setAdapter(userManageAdapter);

                Toast.makeText(UserManageActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(UserManageActivity.this,AdminActivity.class);
                startActivity(intent);
                return true;

                //删除用户操作
            case R.id.delete:
                if(searchText!=null && !searchText.equals("admin")) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DataDao dataDao = new DataDao();
                            dataDao.deleteUser(searchText);
                        }
                    }).start();

                    Toast.makeText(UserManageActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UserManageActivity.this,"管理员无法删除",Toast.LENGTH_SHORT).show();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataDao dataDao = new DataDao();
                        userList = dataDao.selectAllUser();
                    }
                }).start();


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.user_manage_recyclerview);
                UserManageAdapter userManageAdapter = new UserManageAdapter(UserManageActivity.this,userList);
                recyclerView.setAdapter(userManageAdapter);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }




}

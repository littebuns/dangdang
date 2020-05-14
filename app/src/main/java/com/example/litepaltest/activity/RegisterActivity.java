package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.entity.User;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegisterActivity extends AppCompatActivity {
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Toolbar toolbar = findViewById(R.id.add_user_toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //将toolbar的后退键给显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final EditText editTextName = (EditText) findViewById(R.id.add_user_name) ;
        final EditText editTextPassword = (EditText) findViewById(R.id.add_user_password);
        final EditText editTextPhone = (EditText) findViewById(R.id.add_user_phone);




        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.add_user_toolbar);
        collapsingToolbar.setTitle("添加成员");

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.add_user_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("友情提醒");
                dialog.setMessage("请确认您的操作");
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user.setName(editTextName.getText().toString());
                        user.setPassword(editTextPassword.getText().toString());
                        user.setPhone(editTextPhone.getText().toString());
                        user.setRole(0);



                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DataDao dataDao = new DataDao();
                                dataDao.addUser(user);
                            }
                        }).start();



                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this,"成功注册",Toast.LENGTH_SHORT).show();
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

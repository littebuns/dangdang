package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.entity.Approve;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class UserAddApproveActivity extends AppCompatActivity {
    private static final String TAG = "UserAddApproveActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_approve);

        Toolbar toolbar = findViewById(R.id.add_approve_toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //将toolbar的后退键给显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences preferences = getSharedPreferences("name",MODE_PRIVATE);
        final String name = preferences.getString("name","");






        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.add_approve_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = (EditText) findViewById(R.id.add_approve);
                final String content = editText.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = new Date();
                final Approve approve = new Approve();
                approve.setName(name);
                approve.setContent(content);
                approve.setApprove(0);
                approve.setDate(simpleDateFormat.format(date));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataDao dataDao = new DataDao();
                        dataDao.addApprove(approve);
                    }
                }).start();
                Toast.makeText(UserAddApproveActivity.this,"申请已提交",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserAddApproveActivity.this,UserApproveActivity.class);
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

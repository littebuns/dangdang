package com.example.litepaltest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.litepaltest.R;
import com.example.litepaltest.entity.User;
import com.example.litepaltest.util.BaseActivity;
import com.example.litepaltest.util.JDBCUtils;

import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoginActivity extends BaseActivity {
    private static final int loginAction = 1;
    private static final String TAG = "LoginActivity";
    private Button login;
    private EditText accountEdit;
    private EditText passwordEdit;
    User user = new User();
    String account;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LitePal.getDatabase();//在oncreate中初始化一下LitePal
        accountEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);


        TextView registerview = (TextView)findViewById(R.id.register);
        registerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = accountEdit.getText().toString();
                password = passwordEdit.getText().toString();


                LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(LoginActivity.this)
                        .setMessage("加载中...")
                        .setCancelable(true)
                        .setCancelOutside(true);

                LoadingDailog dialog=loadBuilder.create();

                dialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        PreparedStatement pstmt  = null;
                        ResultSet rs = null;
                        try{
                            conn = JDBCUtils.getConnection();
                            String sql = "select * from user where name =  ?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1,account);
                            rs = pstmt.executeQuery();
                            while (rs.next()){
                                user.setName(rs.getString("name"));
                                user.setPassword(rs.getString("password"));
                                user.setRole(rs.getInt("role"));
                                Log.d(TAG, "run: "+ user.getName());
                            }

                        }catch (SQLException e){
                            e.printStackTrace();
                        }finally {
                            JDBCUtils.release(conn,pstmt,rs);
                        }
                    }
                }).start();

                //等待数据返回
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    if(user.getPassword().equals(password) && user.getRole() == 0){

                        SharedPreferences.Editor editor = getSharedPreferences("name",MODE_PRIVATE).edit();
                        editor.putString("name", user.getName());
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                    if(user.getPassword().equals(password) && user.getRole()==1){
                        Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                    if(!user.getPassword().equals(password)){
                        Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,"用户名错误",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



}

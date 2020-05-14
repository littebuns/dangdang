package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.entity.News;
import com.example.litepaltest.util.BaseActivity;
import com.example.litepaltest.util.getPhotoFromPhotoAlbum;

import com.example.litepaltest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class AddScheduleActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    News news = new News();
    private ImageView imageView;
    private EditText newsTitleEdit;
    private EditText newsContentEdit;
    private String photoPath;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LitePal.getDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
//        Button chooseFromAlbm = (Button) findViewById(R.id.get_photo);
        imageView = findViewById(R.id.picture);
//        Button saveNews = (Button) findViewById(R.id.save_news);
//        saveNews.setOnClickListener(this);
//        chooseFromAlbm.setOnClickListener(this);


        FloatingActionButton floatingActionButton =  (FloatingActionButton)findViewById(R.id.user_manage_add_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddScheduleActivity.this,"添加",Toast.LENGTH_SHORT).show();
                saveNews();
                Intent intent = new Intent(AddScheduleActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id){
//            case R.id.get_photo:
//                getPermission();
//                goPhotoAlbum();
//                break;
//            case R.id.save_news:
//                saveNews();
        }
    }

    public void saveNews(){
        newsTitleEdit = (EditText) findViewById(R.id.news_title);
        newsContentEdit = (EditText) findViewById(R.id.news_content);
        String title = newsTitleEdit.getText().toString();
        String content = newsContentEdit.getText().toString();
        news.setTitle(title);
        news.setContent(content);
        news.setPrictureUrl(photoPath);


        new Thread(new Runnable() {
            @Override
            public void run() {
                DataDao dataDao = new DataDao();
                dataDao.addNews(news);
            }
        }).start();

        Toast.makeText(AddScheduleActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddScheduleActivity.this,AdminActivity.class);
        startActivity(intent);

    }

    private void goPhotoAlbum(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }

    private void getPermission(){
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK){
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this,data.getData());
            Glide.with(AddScheduleActivity.this).load(photoPath).into(imageView);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //成功打开权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }
    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }
}

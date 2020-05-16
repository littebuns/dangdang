package com.example.litepaltest.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.litepaltest.Dao.DataDao;
import com.example.litepaltest.R;
import com.example.litepaltest.entity.Sign;
import com.example.litepaltest.util.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationActivity extends BaseActivity {
    private static final String TAG = "LocationActivity";

    public LocationClient locationClient;
    private TextView positionTextView;
    private MapView mapView;
    private boolean isFirstLocation = true;
    private BaiduMap baiduMap;
    private String signLocation;

    private Sign sign1 = new Sign();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());

        LitePal.getDatabase();
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new LocationListener());
        setContentView(R.layout.activity_location);
        //显示北京地图
        mapView = (MapView) findViewById(R.id.mapView);

        //获取到BaiduMap的实例
        baiduMap = mapView.getMap();

        baiduMap.setMyLocationEnabled(true);

        LocationClientOption option = new LocationClientOption();
        //设置为需要当前位置的详细地址
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);


        //用List存放多个权限请求，一次性申请
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(LocationActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(LocationActivity.this,Manifest.permission.READ_PHONE_STATE)!=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(LocationActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(LocationActivity.this,permissions,1);
        }else{
            requestLocation();
        }

        //签到的点击事件
        FloatingActionButton sign = (FloatingActionButton) findViewById(R.id.sign);
        sign.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("name",MODE_PRIVATE);
                String name = preferences.getString("name","");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                Date date = new Date();
                sign1.setLocation(signLocation);
                sign1.setName(name);
                sign1.setDate(simpleDateFormat.format(date));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataDao dataDao = new DataDao();
                        dataDao.addLocation(sign1);
                    }
                }).start();


                Log.d(TAG, "onClick: "+name + date + signLocation);
                Intent intent = new Intent(LocationActivity.this,MainActivity.class);
                Toast.makeText(LocationActivity.this,"打卡成功"+signLocation+name,Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


    }

    //
    private void navigateTo(BDLocation location){
        if(isFirstLocation){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            //设置缩放级别
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append(location.getProvince()).append(location.getCity()).append(location.getDistrict());
            signLocation = currentPosition+"";
            isFirstLocation = false;
        }


        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);



    }

    //开始定位
    private void requestLocation(){
//        initLocation();
        locationClient.start();
    }

//    //实现自动更新位置
//    private void initLocation(){
//        LocationClientOption option = new LocationClientOption();
//        //设置为需要当前位置的详细地址
//        option.setIsNeedAddress(true);
//        locationClient.setLocOption(option);
//    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有请求才能使用该功能",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"发生未知的错误",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public class LocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(final BDLocation location) {
            navigateTo(location);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append(location.getProvince()).append(location.getCity()).append(location.getDistrict()).append(location.getStreet());
                    signLocation = currentPosition+"";


                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止定位，不然会在后台一直更新，耗电严重
        locationClient.stop();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}

package com.example.tony.practice0527;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {


    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1;
    //設定超過一個距離(尺)他就會做更新location的動作
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000;
    //設定超過一個時間(毫秒)他就會做更新location的動作
    protected LocationManager locationManager;
    protected Button retrieveLocationButton;
    protected TextView locationPrint;
    ServerConnection sc;
    double long1,lati1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sc = new ServerConnection();

        retrieveLocationButton = (Button) findViewById(R.id.retrieveLocationButton);
        locationPrint = (TextView) findViewById(R.id.FindLocation);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(

                //Provider: the name of the GPS provider
                LocationManager.GPS_PROVIDER,
                //minTime: 最小時間間隔後更新位置，以毫秒為單位
                MINIMUM_TIME_BETWEEN_UPDATES,
                //minDistance: 最短距離間隔外更新位置，以公尺為單位
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                //Listener:每次地點更新時會呼叫LocationListener中onLocationChanged(Location) 		     方法
                new MyLocationListener()
        );

        retrieveLocationButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showCurrentLocation();
            }
        });

        Timer timer01 =new Timer();
        timer01.schedule(task, 0, 1000);
    }

    private TimerTask task = new TimerTask(){
        public void run() {
            Thread t1 = new Thread(r1);
            t1.start();

        }
    };

    private Runnable r1 = new Runnable() {
        public void run() {
            sc.update("imf91", "imf91", "id=10685", "B='" + long1 + "',C='" + lati1 + "'");
        }
    };

    protected void showCurrentLocation() {
        //取得最後得知道provider資訊
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //得知GPS位置時，在TextView上顯示經緯度
        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude());
            locationPrint.setText(message);
            long1=location.getLongitude();
            lati1=location.getLatitude();
        }
        else
            Toast.makeText(Main2Activity.this, "null", Toast.LENGTH_LONG).show();
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
// TODO Auto-generated method stub
            //將想要印出的資料用string.format的方法存入string
            //%1$s，%2$s中，1、2代表後方第幾個參數
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude());
            locationPrint.setText(message);
            long1=location.getLongitude();
            lati1=location.getLatitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
// TODO Auto-generated method stub
//當device的GPS沒有開啟的時候他會顯示
            Toast.makeText(Main2Activity.this,"Provider disabled by the user. GPS turned off",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
// TODO Auto-generated method stub
//當device將GPS打開的時候他會顯示
            Toast.makeText(Main2Activity.this,"Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
// TODO Auto-generated method stub
//當provider change的時候會顯示
            Toast.makeText(Main2Activity.this, "Provider status changed",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent.setClass(Main2Activity.this,MainActivity.class);
            startActivity(myIntent);
            this.finish();
        }
        return
                super.onKeyDown(keyCode, event);

    }



}

package com.example.tony.practice0527;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    JSONArray ja;
    ServerConnection sc;
    double longtitude,latitude;
    LatLng position;
    PolylineOptions polylineOpt = new PolylineOptions().width(5).color(Color.parseColor("#3399ff")).geodesic(true);
    Polyline line;
    public GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        sc = new ServerConnection();
        ja = new JSONArray();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Timer timer01 =new Timer();
        timer01.schedule(task, 0, 3000);

    }

    private Runnable r1 = new Runnable() {
        public void run() {
            ja = sc.query("imf91", "imf91", "B,C", "id=10685");
            Message message = new Message();
            message.what = 1;
            h1.sendMessage(message);
        }
    };

    Handler h1 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                try {
                    Double long1 = longtitude;
                    Double lati1 = latitude;
                    for (int i = 0; i < ja.length(); i++) {
                        longtitude = ja.getJSONObject(i).getDouble("B");
                        latitude = ja.getJSONObject(i).getDouble("C");
                    }
                    position = new LatLng(latitude, longtitude);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
                    if(long1!=0&&lati1!=0)
                        line = mMap.addPolyline(polylineOpt.add(new LatLng(lati1, long1), new LatLng(latitude, longtitude)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        position = new LatLng(latitude, longtitude);
        //mMap.addMarker(new MarkerOptions().position(position).title("Marker in current position"));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }

    private TimerTask task = new TimerTask(){
        public void run() {
            Thread t1 = new Thread(r1);
            t1.start();

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent.setClass(MapsActivity.this,MainActivity.class);
            startActivity(myIntent);
            this.finish();
        }
        return
                super.onKeyDown(keyCode, event);

    }

}
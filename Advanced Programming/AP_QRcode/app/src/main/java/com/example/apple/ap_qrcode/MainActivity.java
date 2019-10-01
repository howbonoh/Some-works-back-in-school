package com.example.apple.ap_qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private TextView pricetxt;
    private Button button1;
    ServerConnection sc;
    JSONArray ta;
    String s1="";
    Integer price=0;
    String sp;
    private TextView object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pricetxt = (TextView) findViewById(R.id.textView1);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(scan);
        sc = new ServerConnection();
        object = (TextView)findViewById(R.id.textView2);

    }
    private Button.OnClickListener scan = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "SCAN_MODE");
            startActivityForResult(intent, 1);
            Thread t1 = new Thread(r1);
            t1.start();
        }
    };
    private Runnable r1 = new Runnable() {
        @Override
        public void run() {
            ta = sc.query("imf88","imf88","A,B,C","id>0");
        }
    };
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                try {for (int i=0; i<ta.length();i++){
                    if (contents.equals(ta.getJSONObject(i).get("A"))){
                        s1 = s1+ta.getJSONObject(i).get("B")+"\n";
                        price+=ta.getJSONObject(i).getInt("C");
                        break;
                    }
                }
                } catch (JSONException e){}
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                sp = price.toString();
                pricetxt.setText(sp);
                object.setText(s1);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    };

}

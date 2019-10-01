package com.example.apple.ap_msger;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button b1;
    ServerConnection sc;
    EditText e1;
    EditText e2;
    TextView tv;
    String s1;
    String s2;
    String s3;
    JSONArray ta;
    int msgno;
    int msgno_org;


    private Runnable r1 = new Runnable()
    {
        public void run()
        {
            sc.insert("imf29", "imf29", "A,F" , "'"+s1+"','"+s2+"'");
        }
    };
    private Runnable r2 = new Runnable() {
        public void run() {
            ta = sc.query("imf29","imf29","A,F","id>0");
            Message message = new Message();
            message.what = 1;
            h1.sendMessage(message);

        }
    };
    private View.OnClickListener b1cl =new View.OnClickListener(){
        public void onClick(View v) {
            s1 = e1.getText().toString();
            s2 = e2.getText().toString();
            e2.setText("");
            if (!s1.equals("")&&!s2.equals("")){
                Thread t1 = new Thread(r1);
                t1.start();
            }
        }
    };
    private TimerTask task = new TimerTask(){
        public void run() {
            Thread t2 = new Thread(r2);
            t2.start();
        }
    };
    Handler h1 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                msgno=ta.length();
                if (msgno>msgno_org){
                    msgno_org = msgno;
                    s3 = "";
                    try{  for (int i = 0; i <= msgno; i++){
                        s3 = s3 +ta.getJSONObject(i).get("A")+":" + ta.getJSONObject(i).get("F") + "\n";
                    } }catch(JSONException e){}
                    tv.setText(s3);
                    }
            }
            super.handleMessage(msg);
        }
    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.button);
        b1.setOnClickListener(b1cl);
        sc = new ServerConnection();
        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);
        tv = (TextView)findViewById(R.id.textView);
        Timer timer01 =new Timer();
        timer01.schedule(task, 0,3000);
            msgno_org = 0;
    }
}

package com.example.tony.final0608;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    EditText et_id,et_passwd;
    Button bt_login;
    String s1,s2;
    JSONArray ja,jobject,jlist;
    ServerConnection sc;
    String userid;
    String group = "imf35";
    String code = "imf35";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_id = (EditText)findViewById(R.id.ed_id);
        et_passwd = (EditText)findViewById(R.id.ed_pw);
        bt_login = (Button)findViewById(R.id.login);
        sc = new ServerConnection();
        ja = new JSONArray();
        jobject = new JSONArray();
        jlist = new JSONArray();
        bt_login.setOnClickListener(login);
        et_id.setText("BJ");
        et_passwd.setText("imloser");
    }
    private View.OnClickListener login =new View.OnClickListener(){
        public void onClick(View v) {
            s1 = et_id.getText().toString();
            s2 = et_passwd.getText().toString();
            if(s1.equals("")||s2.equals(""))
                Toast.makeText(MainActivity.this, "請輸入正確的帳號及密碼", Toast.LENGTH_LONG).show();
            else
            {
                Thread t1 = new Thread(r1);
                t1.start();
            }
        }
    };
    private Runnable r1 = new Runnable()
    {
        public void run()
        {
            ja = sc.query(group,code,"id,C","A='1' AND B='"+s1+"'");
            Message message = new Message();
            message.what = 1;
            h1.sendMessage(message);
        }
    };
    private Runnable r2 = new Runnable()
    {
        public void run()
        {
            sc.insert(group,code,"A,B,C","'1','"+s1+"','"+s2+"'");
            ja = sc.query(group,code,"id","A='1' AND B='"+s1+"'");
            Message message = new Message();
            message.what = 2;
            h1.sendMessage(message);
        }
    };
    private Runnable r3 = new Runnable()
    {
        public void run()
        {
            jobject = sc.query(group,code,"id,B,C,D","A='2'");
            jlist = sc.query(group,code,"B,C,D","A='3' AND B='"+userid+"'");
            Message message = new Message();
            message.what = 4;
            h1.sendMessage(message);
        }
    };

    Handler h1 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (ja.length() == 0) {
                    Thread t2 = new Thread(r2);
                    t2.start();
                } else {
                    try {
                        if (ja.getJSONObject(0).getString("C").equals(s2)) {
                            Toast.makeText(MainActivity.this, "密碼正確，歡迎光臨", Toast.LENGTH_LONG).show();
                            userid = ja.getJSONObject(0).getString("id");


                            Thread t3 = new Thread(r3);
                            t3.start();

                        } else {
                            Toast.makeText(MainActivity.this, "密碼錯誤，請重新輸入", Toast.LENGTH_LONG).show();
                            et_passwd.setText("");
                        }
                    } catch (JSONException e) {
                    }
                }
            } else if (msg.what == 2) {
                try {
                    userid = ja.getJSONObject(0).getString("id");
                } catch (JSONException e) {}
                Thread t3 = new Thread(r3);
                t3.start();
            } else if (msg.what == 4){
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("acct",s1);
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, Mylist.class);
                startActivity(intent);
            }
        }
    };
}

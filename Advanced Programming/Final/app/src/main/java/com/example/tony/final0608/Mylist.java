package com.example.tony.final0608;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mylist extends AppCompatActivity {

    JSONArray jresult,jfollow,jc;
    ServerConnection sc;
    TextView title;
    ImageView imageView;
    Button add,bt_setting;
    SimpleAdapter adapter;
    private List<Map<String,Object>> list;
    ListView lv;
    String acct;
    String [] name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        sc = new ServerConnection();
        title = (TextView)findViewById(R.id.itemname);
        imageView = (ImageView) findViewById(R.id.image);
        add = (Button)findViewById(R.id.add);
        add.setOnClickListener(gosearchpage);
        bt_setting = (Button)findViewById(R.id.setting);
        bt_setting.setOnClickListener(setting);
        lv = (ListView)findViewById(R.id.listView);
        name = new String[10];

        Bundle bundle = getIntent().getExtras();
        acct = bundle.getString("acct");

        Thread t1 = new Thread(r1);
        t1.start();
    }

    private Runnable r1 = new Runnable()
    {
        public void run()
        {
            jfollow = sc.query("imf35","imf35","C","A='追蹤' AND B='"+acct+"'");
            Message message = new Message();
            message.what = 2;
            h1.sendMessage(message);
        }
    };

    private Runnable r2 = new Runnable()
    {
        public void run()
        {
            jresult = sc.query("imf35","imf35","B,C,F","A='商品' AND B='"+name+"'");
            Message message = new Message();
            message.what = 2;
            h1.sendMessage(message);
        }
    };

    private ListView.OnItemClickListener itemclick = new ListView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> adapterview, View view, int position, long arg) {
            Map<String,Object> item = list.get(position);
            String name= (item.get("name").toString());
            Intent intent = new Intent(Mylist.this, detail.class);
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("acct",acct);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private View.OnClickListener gosearchpage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("acct",acct);
            intent.setClass(Mylist.this, Search.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener setting = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(Mylist.this, setting.class);
            startActivity(intent);
        }
    };

    Handler h1 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                }
            else if(msg.what==2){

                list = new ArrayList<Map<String, Object>>();
                adapter = new SimpleAdapter(Mylist.this,list,R.layout.searchlist,new String[]{"name","price","note"},new int[]{R.id.itemname, R.id.itemprice, R.id.itemnote});
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(itemclick);

                try {
                    for(int i=0;i<jfollow.length();i++){
                        JSONObject object = jfollow.getJSONObject(i);
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("name", object.getString("C"));
                        item.put("note", "下殺八折");
                        list.add(item);
                        adapter.notifyDataSetChanged();
                    }

                }
                catch (JSONException e) {
                }
            }
            }
    };
}

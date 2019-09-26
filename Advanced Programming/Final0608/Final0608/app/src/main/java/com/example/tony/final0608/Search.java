package com.example.tony.final0608;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search extends AppCompatActivity {
    EditText type;
    Button search;
    String searching;
    ServerConnection sc;
    JSONArray jresult;
    String group="imf35",code="imf35";
    SimpleAdapter adapter;
    private List<Map<String,Object>> list;
    ListView lv;
    ImageView imageView;
    String acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        type = (EditText)findViewById(R.id.editText);
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(gosearch);
        sc = new ServerConnection();
        jresult = new JSONArray();
        imageView = (ImageView) findViewById(R.id.image);
        lv = (ListView)findViewById(R.id.listView);
        list = new ArrayList<Map<String, Object>>();
        adapter = new SimpleAdapter(Search.this,list,R.layout.s_resultlist,new String[]{"name","price","note"},new int[]{R.id.itemname, R.id.itemprice, R.id.itemnote});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(itemclick);

        Bundle bundle = getIntent().getExtras();
        acct = "BJ";
//        Map<String, Object> item = new HashMap<String, Object>();
//        item.put("name","Nikon D5 公司貨");
//        item.put("price", "$219900");
//        item.put("note", "下殺8折");
//        list.add(item);
//        adapter.notifyDataSetChanged();
    }
    private ListView.OnItemClickListener itemclick = new ListView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> adapterview, View view, int position, long arg) {
            Map<String,Object> item = list.get(position);
            String name = item.get("name").toString();
            Intent intent = new Intent();
            intent.setClass(Search.this, detail.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("acct",acct);
            bundle.putInt("checker",1);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    };
    private View.OnClickListener gosearch =new View.OnClickListener(){
        public void onClick(View v) {
            searching=type.getText().toString();
            Thread t1 = new Thread(r1);
            t1.start();
        }
    };
    private Runnable r1 = new Runnable()
    {
        public void run()
        {
            jresult = sc.query(group,code,"id,B,C,D","A='商品' AND B LIKE '%"+searching+"%'");
            Message message = new Message();
            message.what = 1;
            h1.sendMessage(message);
        }
    };
    Handler h1 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (jresult.length() == 0) {
                    Toast.makeText(Search.this, "查無資料嗚嗚嗚", Toast.LENGTH_LONG).show();
                } else {
                    try{for (int i=0; i<jresult.length(); i++){
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("name",jresult.getJSONObject(i).getString("B"));
                        item.put("price",jresult.getJSONObject(i).getString("C"));
                        item.put("note",jresult.getJSONObject(i).getString("D"));
                        list.add(item);
                        adapter.notifyDataSetChanged();
                    }
                    }catch (JSONException e){}
                }
            }
        }
    };
}

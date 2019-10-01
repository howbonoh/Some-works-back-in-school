package com.example.tony.final0608;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class detail extends AppCompatActivity {

    JSONArray jitem,jcomment,jreview;
    ServerConnection sc;
    TextView tv1,tv2,tv3;
    EditText et2;
    ImageView imageView;
    Button bt_cancel,bt_location,bt_comment,bt_review,bt_send;
    SimpleAdapter adapter;
    private List<Map<String,Object>> list;
    ListView itemlv,commentlv,reviewlv;
    String name,acct,message;
    int checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemlv = (ListView)findViewById(R.id.listView2);
        commentlv = (ListView)findViewById(R.id.listView2);
        reviewlv = (ListView)findViewById(R.id.listView2);
        sc = new ServerConnection();
        imageView = (ImageView) findViewById(R.id.image);
        et2 = (EditText)findViewById(R.id.editText2);
        bt_cancel = (Button)findViewById(R.id.cancel);
        bt_location = (Button)findViewById(R.id.location);
        bt_comment = (Button)findViewById(R.id.comment);
        bt_review = (Button)findViewById(R.id.review);
        bt_send = (Button)findViewById(R.id.send);
        bt_cancel.setOnClickListener(cancel);
        bt_location.setOnClickListener(location);
        bt_comment.setOnClickListener(comment);
        bt_review.setOnClickListener(review);
        bt_send.setOnClickListener(send);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        tv1.setText("Nikon");
        tv2.setText("4K UHD高畫質影片");
        tv3.setText("TFT觸控感應螢幕");


        bt_send.setVisibility(View.INVISIBLE);
        et2.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        acct = bundle.getString("acct");
        checker = bundle.getInt("checker");

        if(checker==1){
            bt_cancel.setText("追蹤商品");
        }
        else{
            bt_cancel.setText("取消追蹤");
        }


        Thread t1 = new Thread(r1);
        t1.start();

    }

    private ListView.OnItemClickListener itemclick = new ListView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> adapterview, View view, int position, long arg) {
            Map<String,Object> item = list.get(position);
            String url= (item.get("url").toString());
            Uri uri= Uri.parse(url);
            Intent i=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(i);
        }
    };

    private View.OnClickListener cancel =new View.OnClickListener(){
        public void onClick(View v) {
            bt_send.setVisibility(View.INVISIBLE);
            et2.setVisibility(View.INVISIBLE);
            Thread t5 = new Thread(r5);
            t5.start();
        }
    };

    private View.OnClickListener location =new View.OnClickListener(){
        public void onClick(View v) {
            bt_send.setVisibility(View.INVISIBLE);
            et2.setVisibility(View.INVISIBLE);
            Intent intent = new Intent();
            intent.setClass(detail.this, MapsActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener comment =new View.OnClickListener(){
        public void onClick(View v) {
            bt_send.setVisibility(View.VISIBLE);
            et2.setVisibility(View.VISIBLE);

            Thread t2 = new Thread(r2);
            t2.start();
        }
    };

    private View.OnClickListener review =new View.OnClickListener(){
        public void onClick(View v) {
            bt_send.setVisibility(View.INVISIBLE);
            et2.setVisibility(View.INVISIBLE);

            Thread t3 = new Thread(r3);
            t3.start();
        }
    };

    private View.OnClickListener send =new View.OnClickListener(){
        public void onClick(View v) {
            message = et2.getText().toString();
            et2.setText("");
            Thread t4 = new Thread(r4);
            t4.start();
        }
    };

    private Runnable r1 = new Runnable()
    {
        public void run()
        {       //讀取資料庫中該使用者之id及密碼
            jitem = sc.query("imf35","imf35","id,C,D,F","A='商品' AND B='"+name+"'");
            Message message = new Message();
            message.what = 1;
            h1.sendMessage(message);        //A是1代表使用者資訊，此時B為帳號、C為密碼
        }
    };

    private Runnable r2 = new Runnable()
    {
        public void run()
        {       //讀取資料庫中該使用者之id及密碼
            jcomment = sc.query("imf35","imf35","B,C,F","A='評論' AND C='"+name+"'");
            Message message = new Message();
            message.what = 2;
            h1.sendMessage(message);        //A是1代表使用者資訊，此時B為帳號、C為密碼
        }
    };

    private Runnable r3 = new Runnable()
    {
        public void run()
        {
            jreview = sc.query("imf35","imf35","B,C,F","A='商品心得' AND B='"+name+"'");
            Message message = new Message();
            message.what = 3;
            h1.sendMessage(message);
        }
    };

    private Runnable r4 = new Runnable()
    {
        public void run() {
            sc.insert("imf35", "imf35", "A,B,C,F", "'評論','" + acct + "','" + name + "','" + message + "'");
            Message message = new Message();
            message.what = 2;
            h1.sendMessage(message);
        }
    };

    private Runnable r5 = new Runnable()
    {
        public void run() {
            if(checker==1){
                sc.insert("imf35", "imf35", "A,B,C","'追蹤','BJ','"+name+"'");
                checker=0;
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("acct",acct);
                intent.putExtras(bundle);
                intent.setClass(detail.this, Mylist.class);
                startActivity(intent);
            }
            else {
                sc.delete("imf35", "imf35", "A='追蹤' AND C='" + name + "'");
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("acct",acct);
                intent.putExtras(bundle);
                intent.setClass(detail.this, Mylist.class);
                startActivity(intent);
            }
        }
    };

    Handler h1 = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==1) {

                list = new ArrayList<Map<String, Object>>();
                adapter = new SimpleAdapter(detail.this,list,R.layout.searchlist,new String[]{"name","price","note"},new int[]{R.id.itemname, R.id.itemprice, R.id.itemnote});
                itemlv.setAdapter(adapter);
                itemlv.setOnItemClickListener(itemclick);

                try {
                            for(int i=0;i<jitem.length();i++){
                                JSONObject object = jitem.getJSONObject(i);
                                Map<String, Object> item = new HashMap<String, Object>();
                                item.put("name",object.getString("D"));
                                item.put("price","$"+object.getString("C"));
                                item.put("url", object.getString("F"));
                                list.add(item);
                                adapter.notifyDataSetChanged();
                            }

                        }
                     catch (JSONException e) {
                    }
                }
            else if(msg.what==2){
                try {

                    list = new ArrayList<Map<String, Object>>();
                    adapter = new SimpleAdapter(detail.this,list,R.layout.commentlist,new String[]{"comment"},new int[]{R.id.comment});
                    commentlv.setAdapter(adapter);

                    for(int i=0;i<jcomment.length();i++){
                        JSONObject object = jcomment.getJSONObject(i);
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("comment",object.getString("B")+':'+object.getString("F"));
                        list.add(item);
                        adapter.notifyDataSetChanged();
                    }

                }
                catch (JSONException e) {
                }
            }
            else if(msg.what==3){
                try {

                    list = new ArrayList<Map<String, Object>>();
                    adapter = new SimpleAdapter(detail.this,list,R.layout.reviewlist,new String[]{"name"},new int[]{R.id.itemname});
                    reviewlv.setAdapter(adapter);
                    reviewlv.setOnItemClickListener(itemclick);

                    for(int i=0;i<jreview.length();i++){
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("name",jreview.getJSONObject(i).getString("C"));
                        item.put("url", jreview.getJSONObject(i).getString("F"));
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

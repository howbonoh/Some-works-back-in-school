package tw.edu.nctu.imf.panda.shopping_example;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    ServerConnection sc;
    Button bt_login,bt_scan,bt_download,bt_update,bt_clear;
    EditText et_id,et_passwd;
    String s1,s2,s3;
    JSONArray ja,jb;
    String userid;
    private List<Map<String,Object>> list;
    ListView lv;
    SimpleAdapter adapter;
    String group = "imf29";
    String code = "imf29";
    TextView tp;
    Integer sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_login = (Button)findViewById(R.id.login);
        et_id = (EditText)findViewById(R.id.id);
        et_passwd = (EditText)findViewById(R.id.passwd);
        sc = new ServerConnection();
        ja = new JSONArray();
        jb = new JSONArray();
        bt_login.setOnClickListener(login);
        et_id.setText("April");
        et_passwd.setText("grandma");
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

    private View.OnClickListener scan =new View.OnClickListener(){
        public void onClick(View v) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "SCAN_MODE");
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener download =new View.OnClickListener(){
        public void onClick(View v) {
            Thread t3 = new Thread(r3);
            t3.start();
            Message message = new Message();
            message.what = 4;
            h1.sendMessage(message);
        }
    };

    private View.OnClickListener update =new View.OnClickListener(){
        public void onClick(View v) {
            Thread t4 = new Thread(r4);
            t4.start();
            Message message = new Message();
            message.what = 4;
            h1.sendMessage(message);
        }
    };

    private View.OnClickListener clear =new View.OnClickListener(){
        public void onClick(View v) {
            list.clear();
            adapter.notifyDataSetChanged();
            sum = 0;
            tp.setText(sum.toString());
        }
    };

    private ListView.OnItemClickListener itemclick = new ListView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> adapterview, View view, int position, long arg)
        {
            Map<String,Object> item = list.get(position);
            int count = Integer.parseInt(item.get("count").toString());
            int price = Integer.parseInt(item.get("price").toString());
            int total = Integer.parseInt(item.get("total").toString());
            count--;
            if(count==0)
                list.remove(position);
            else
            {
                item.put("total",price*count);
                item.put("count",count);
                item.put("note","單價:"+price+"      個數:"+count);
                list.set(position,item);
            }
            adapter.notifyDataSetChanged();
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
            jb = sc.query(group,code,"id,B,C,D","A='2'");
            ja = sc.query(group,code,"B,C,D","A='3' AND B='"+userid+"'");
            Message message = new Message();
            message.what = 3;
            h1.sendMessage(message);
        }
    };
    private Runnable r4 = new Runnable() {
        @Override
        public void run() {
            sc.delete(group,code,"B='"+userid+"'");
            for (int i=0; i<list.size(); i++) {
                Map<String,Object> item = list.get(i);
                sc.insert(group, code, "A,B,C,D", "'3','" + userid + "','"+item.get("Oid")+"','"+item.get("count")+"'");
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String ca[] = contents.split(";");
                for (int i=0; i<ca.length; i+=2) {
                    try {
                        for (int j = 0; j < jb.length(); j++)
                        {
                                if (ca[i].equals(jb.getJSONObject(j).get("B"))) {
                                    String Oid = jb.getJSONObject(j).getString("id");
                                    int checker=0;
                                    int reminder=0;
                                    for (int w=0; w<ja.length();w++) {
                                        if (Oid.equals(ja.getJSONObject(w).get("C"))){
                                            checker = 1;
                                            reminder = w;
                                        }
                                    }
                                    if (checker==1) {
                                        Map<String, Object> item = new HashMap<String, Object>();
                                        item.put("Oid", jb.getJSONObject(j).get("id"));
                                        item.put("name", jb.getJSONObject(j).getString("C"));
                                        int price = Integer.parseInt(jb.getJSONObject(j).getString("D"));
                                        item.put("price", price);
                                        int count = Integer.parseInt(ca[i + 1]);
                                        count += ja.getJSONObject(reminder).getInt("D");
                                        item.put("count", count);
                                        item.put("total", "" + price * count);
                                        item.put("note", "單價:" + price + "      個數:" + count);
                                        for (int h=0; h<list.size(); h++) {
                                            Map<String,Object> item2 = list.get(h);
                                            if (item.get("Oid").equals(item2.get("Oid")))
                                                list.set(h, item);
                                        }
                                            break;
                                    }
                                    else {
                                        Map<String, Object> item = new HashMap<String, Object>();
                                        item.put("Oid", jb.getJSONObject(j).get("id"));
                                        item.put("name", jb.getJSONObject(j).getString("C"));
                                        int price = Integer.parseInt(jb.getJSONObject(j).getString("D"));
                                        item.put("price", price);
                                        int count = Integer.parseInt(ca[i+1]);
                                        item.put("count", count);
                                        item.put("total", "" + price * count);
                                        item.put("note", "單價:" + price + "      個數:" + count);
                                        list.add(item);
                                        break;
                                    }
                                }

                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {}
                }
                Message message = new Message();
                message.what = 4;
                h1.sendMessage(message);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    };
    Handler h1 = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==1) {
                if(ja.length()==0)
                {
                    Thread t2 = new Thread(r2);
                    t2.start();
                }
                else {
                    try {
                        if (ja.getJSONObject(0).getString("C").equals(s2)) {
                            Toast.makeText(MainActivity.this, "密碼正確，歡迎光臨", Toast.LENGTH_LONG).show();
                            userid = ja.getJSONObject(0).getString("id");

                            setContentView(R.layout.layout);
                            lv = (ListView)findViewById(R.id.listView);
                            bt_scan = (Button)findViewById(R.id.buttonscan);
                            bt_scan.setOnClickListener(scan);
                            bt_download = (Button)findViewById(R.id.buttondownload);
                            bt_download.setOnClickListener(download);
                            bt_update = (Button)findViewById(R.id.buttonupdate);
                            bt_update.setOnClickListener(update);
                            bt_clear = (Button)findViewById(R.id.buttonclear);
                            bt_clear.setOnClickListener(clear);
                            tp = (TextView)findViewById(R.id.totalprice);
                            list = new ArrayList<Map<String, Object>>();
                            adapter = new SimpleAdapter(MainActivity.this,list,R.layout.list_item,new String[]{"name","total","note"},new int[]{R.id.itemname, R.id.itemtotal, R.id.itemnote});
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(itemclick);
                            Thread t3 = new Thread(r3);
                            t3.start();

                        } else {
                            Toast.makeText(MainActivity.this, "密碼錯誤，請重新輸入", Toast.LENGTH_LONG).show();
                            et_passwd.setText("");
                        }
                    } catch (JSONException e) {
                    }
                }
            }
            else if(msg.what==2)
            {
                try {
                    userid = ja.getJSONObject(0).getString("id");
                } catch (JSONException e) {
                }
                setContentView(R.layout.layout);
                lv = (ListView)findViewById(R.id.listView);
                bt_scan = (Button)findViewById(R.id.buttonscan);
                bt_scan.setOnClickListener(scan);
                bt_download = (Button)findViewById(R.id.buttondownload);
                bt_download.setOnClickListener(download);
                bt_update = (Button)findViewById(R.id.buttonupdate);
                bt_update.setOnClickListener(update);
                bt_clear = (Button)findViewById(R.id.buttonclear);
                bt_clear.setOnClickListener(clear);
                tp = (TextView)findViewById(R.id.totalprice);
                list = new ArrayList<Map<String, Object>>();
                adapter = new SimpleAdapter(MainActivity.this,list,R.layout.list_item,new String[]{"name","total","note"},new int[]{R.id.itemname, R.id.itemtotal, R.id.itemnote});
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(itemclick);
                Thread t3 = new Thread(r3);
                t3.start();
            }
            else if(msg.what==3)
            {
                try {
                    list.clear();
                    for(int i=0;i<ja.length();i++)
                    {
                        JSONObject buy = ja.getJSONObject(i);
                        for(int j=0;j<jb.length();j++)
                        {
                            JSONObject object = jb.getJSONObject(j);
                            if(buy.getString("C").equals(object.getString("id")))
                            {
                                Map<String, Object> item = new HashMap<String, Object>();
                                item.put("Oid",object.get("id"));
                                item.put("name", object.getString("C"));
                                int price = Integer.parseInt(object.getString("D"));
                                item.put("price", price);
                                int count = Integer.parseInt(buy.getString("D"));
                                item.put("count",count);
                                item.put("total", ""+price*count);
                                item.put("note","單價:"+price+"      個數:"+count);
                                list.add(item);
                                break;
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                }
                Message message = new Message();
                message.what = 4;
                h1.sendMessage(message);
            }
            else if(msg.what==4)
            {
                sum = 0;
                for (int i=0; i<list.size(); i++)
                {
                    Map<String,Object> item = list.get(i);
                    int pp = Integer.parseInt(item.get("total").toString());
                    sum += pp;
                }
                tp.setText(sum.toString());
            }
            super.handleMessage(msg);
        }
    };
}

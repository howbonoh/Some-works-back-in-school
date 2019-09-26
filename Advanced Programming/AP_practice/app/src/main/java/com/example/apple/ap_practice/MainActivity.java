package com.example.apple.ap_practice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3;
    RadioButton rb1, rb2, rb3;
    RadioGroup rg;
    TextView tv;
    int[] select = {-1, -1, -1, -1};  //紀錄各題選的答案，-1代表未選,0~2代表三選項
    int page = 0;        //紀錄目前進行哪一題(0,1,2,3)
    int b3mode = 1; //紀錄目前第三個按紐在哪個狀態 (0->作答, 1->輸出結果)
    String[] question = {"以下三位女藝人，你最欣賞哪一位的外型？", "假設你結婚後第一次回娘家，你覺得你老丈人或妳爸會開什麼酒來跟男方喝？", "以下三種球類運動中，你最常參加的是：", "你最好的朋友血型為："};
    String[][] choice = {{"林志玲", "蔡依林", "楊丞琳"}, {"高梁酒", "葡萄酒", "啤酒"}, {"籃球", "羽球", "桌球"}, {"O型", "B型或AB型", "A型"}};
    String[] answer = {"張飛", "呂布", "關羽", "諸葛亮", "曹操"};
    int counter=1;
    int c2=0;
    int[] r_c = {0,0,0,0,0};

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Button.OnClickListener buttonclick = new Button.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.button1) {
                if (page == 0)
                    page = 4;
                page--;
                tv.setText(question[page]);
                rb1.setText(choice[page][0]);
                rb2.setText(choice[page][1]);
                rb3.setText(choice[page][2]);
                if(select[page]==0)
                    rb1.setChecked(true);
                else if(select[page]==1)
                    rb2.setChecked(true);
                else if(select[page]==2)
                    rb3.setChecked(true);
                else
                    rg.clearCheck();
            } else if (v.getId() == R.id.button2) {
                if(page==3)
                    page = -1;
                page++;
                tv.setText(question[page]);
                rb1.setText(choice[page][0]);
                rb2.setText(choice[page][1]);
                rb3.setText(choice[page][2]);
                if(select[page]==0)
                    rb1.setChecked(true);
                else if(select[page]==1)
                    rb2.setChecked(true);
                else if(select[page]==2)
                    rb3.setChecked(true);
                else
                    rg.clearCheck();
            } else if (v.getId() == R.id.button3) {
                if(c2%2==0){
                    for(int i=0; i<4; i++){
                        counter+=select[i];
                    }
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    rb1.setVisibility(View.INVISIBLE);
                    rb2.setVisibility(View.INVISIBLE);
                    rb3.setVisibility(View.INVISIBLE);
                    tv.setText("三國武將中，你最像"+answer[counter/2]);
                    b3.setText("Retry");
                    c2++;
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Statistic.class);
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("result",r_c);
                    bundle.putInt("answer",counter/2);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);
                }
            }
        }
    };
    private RadioButton.OnClickListener radiobuttonclick = new RadioButton.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.radioButton1) {
                select[page]=0;
                if(select[0]==-1||select[1]==-1||select[2]==-1||select[3]==-1)
                    b3mode=0;
                else
                    b3mode=1;
                if(b3mode==0)
                    b3.setEnabled(false);
                else if(b3mode!=0)
                    b3.setEnabled(true);
            } else if (v.getId() == R.id.radioButton2) {
                select[page]=1;
                if(select[0]==-1||select[1]==-1||select[2]==-1||select[3]==-1)
                    b3mode=0;
                else
                    b3mode=1;
                if(b3mode==0)
                    b3.setEnabled(false);
                else if(b3mode!=0)
                    b3.setEnabled(true);
            } else if (v.getId() == R.id.radioButton3) {
                select[page]=2;
                if(select[0]==-1||select[1]==-1||select[2]==-1||select[3]==-1)
                    b3mode=0;
                else
                    b3mode=1;
                if(b3mode==0)
                    b3.setEnabled(false);
                else if(b3mode!=0)
                    b3.setEnabled(true);
            }
        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==1&&resultCode==RESULT_OK)
        {
            b3.setText("計算結果");
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            counter=1;
            page=0;
            for(int i=0; i<4; i++)
                select[i]=-1;
            c2++;
            rg.clearCheck();
            b3.setEnabled(false);
            tv.setText(question[page]);
            rb1.setText(choice[page][0]);
            rb2.setText(choice[page][1]);
            rb3.setText(choice[page][2]);
            r_c = data.getExtras().getIntArray("newR");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        rb1 = (RadioButton) findViewById(R.id.radioButton1);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        b1.setOnClickListener(buttonclick);
        b2.setOnClickListener(buttonclick);
        b3.setOnClickListener(buttonclick);
        rb1.setOnClickListener(radiobuttonclick);
        rb2.setOnClickListener(radiobuttonclick);
        rb3.setOnClickListener(radiobuttonclick);
        b3.setEnabled(false);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.apple.ap_practice/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.apple.ap_practice/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}

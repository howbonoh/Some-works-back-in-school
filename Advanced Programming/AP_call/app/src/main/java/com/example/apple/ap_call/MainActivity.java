package com.example.apple.ap_call;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ServerConnection sc;
    JSONArray ja;
    TextView t1, t2;
    Button b1, b2;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//使用RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        t1 = (TextView)findViewById(R.id.textView);
        t2 = (TextView)findViewById(R.id.textView2);
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b2.setVisibility(View.INVISIBLE);
        sc = new ServerConnection();
        b1.setOnClickListener(speak);
        b2.setOnClickListener(call);
        Thread t1 = new Thread(r1);
        t1.start();

    }

    private Runnable r1 = new Runnable()
    {
        public void run()
        {
            ja = sc.query("imf70","imf70","A,B,C","id>0");
        }
    };

    private View.OnClickListener speak = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//設定辨識語言(這邊設定的是繁體中文)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW");
//設定語音辨識視窗的內容
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener call = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
//用來儲存最後的辨識結果
        String firstMatch;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //取出多個辨識結果並儲存在String的ArrayList中
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            firstMatch = (String) result.get(0);
            t1.setText(result.get(0));
            String resultname=result.get(0);
            try
            {
                for (int i=0; i<ja.length(); i++)
                {
                    t2.setText(ja.getJSONObject(4).getString("B"));
                    if(ja.getJSONObject(i).getString("B").equals(resultname))
                    {
                        b2.setVisibility(View.VISIBLE);
                        t2.setText(ja.getJSONObject(i).getString("C"));
                        tts = new TextToSpeech(MainActivity.this, ttsInitListener);
                    }
                }

            }catch (JSONException e){}
        } else {
            firstMatch = "無法辨識";
        }
//開啟對話方塊
//set.title:設定標題
//setMessage:設定顯示訊息 這裡會顯示辨識的結果
        new AlertDialog.Builder(MainActivity.this).setTitle("語音辨識結果")
                .setIcon(android.R.drawable.ic_menu_search)
                .setMessage(firstMatch.toString())
                .setPositiveButton("OK", null).show();
    }
    private TextToSpeech.OnInitListener ttsInitListener = new TextToSpeech.OnInitListener() {
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);
// 如果該語言資料不見了或沒有支援則無法使用
                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "This Language is not supported");
                } else {
// 語調(1為正常語調；0.5比正常語調低一倍；2比正常語調高一倍)
                    tts.setPitch((float) 0.5);
// 速度(1為正常速度；0.5比正常速度慢一倍；2比正常速度快一倍)
                    tts.setSpeechRate((float) 0.5);
// 設定要說的內容文字
                    tts.speak(t2.toString(), TextToSpeech.QUEUE_FLUSH, null);

                }
            } else {
                Toast.makeText(MainActivity.this, "Initialization Failed!",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
// Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

    }
}

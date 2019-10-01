package com.example.apple.ap_practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Statistic extends AppCompatActivity {
    private Intent intent;
    private Bundle bundle;
    TextView r1,r2,r3,r4,r5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        r1 = (TextView) findViewById(R.id.r1_c);
        r2 = (TextView) findViewById(R.id.r2_c);
        r3 = (TextView) findViewById(R.id.r3_c);
        r4 = (TextView) findViewById(R.id.r4_c);
        r5 = (TextView) findViewById(R.id.r5_c);
        intent = this.getIntent();
        bundle = intent.getExtras();
        final int[] result=bundle.getIntArray("result");
        final int answer=bundle.getInt("answer");
        r1.setText(String.format("%d",result[0]));
        r2.setText(String.format("%d",result[1]));
        r3.setText(String.format("%d",result[2]));
        r4.setText(String.format("%d",result[3]));
        r5.setText(String.format("%d",result[4]));
        Button bt = (Button)findViewById(R.id.bt_st);
        bt.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                result[answer]++;
                bundle.putIntArray("newR",result);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                Statistic.this.finish();
            }
        });
        Button bt2 = (Button)findViewById(R.id.bt_discard);
        bt2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                bundle.putIntArray("newR",result);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                Statistic.this.finish();
            }
        });

    }
}

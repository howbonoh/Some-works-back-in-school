package com.example.tony.final0608;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class setting extends AppCompatActivity {

    CheckBox a,b,c,d,e,f;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        a = (CheckBox)findViewById(R.id.checkBox);
        b = (CheckBox)findViewById(R.id.checkBox2);
        c = (CheckBox)findViewById(R.id.checkBox3);
        d = (CheckBox)findViewById(R.id.checkBox4);
        e = (CheckBox)findViewById(R.id.checkBox5);
        f = (CheckBox)findViewById(R.id.checkBox6);
        confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(listen);

    }

    private View.OnClickListener listen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(setting.this, Mylist.class);
            Bundle bundle = new Bundle();
            bundle.putString("acct","BJ");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

}

package com.mhutshow.daktarlagbe.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.mhutshow.daktarlagbe.R;

public class FicheInfo extends AppCompatActivity {
    TextView t1 ,t2, t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_info);
        t1=findViewById(R.id.textView2);
        t2=findViewById(R.id.textView4);
        t3=findViewById(R.id.textView5);

        t1.setText("Date de creation"+getIntent().getStringExtra("dateCreated"));
        t2.setText(getIntent().getStringExtra("doctor"));
        t3.setText(getIntent().getStringExtra("description"));
    }
}

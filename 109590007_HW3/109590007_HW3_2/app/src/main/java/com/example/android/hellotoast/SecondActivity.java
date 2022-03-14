package com.example.android.hellotoast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent =getIntent();
        String message = intent.getStringExtra(MainActivity.ex_EXtra);
        TextView Shownumber = (TextView) findViewById(R.id.numbercount2);
        Shownumber.setText(message);
    }
}
package com.example.android.recyclerview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class receipe3 extends AppCompatActivity {

    private TextView Test3;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe3);
        Test3 = (TextView) findViewById(R.id.recipe22);
        Test3.setText("much noodle\n" +
                "much water\n" +
                "love\n" +
                "family\n" +
                "master\n" +
                "¼ cup light brown sugar\n" +
                "1 tablespoon prepared yellow mustard\n" +
                "2 tablespoons butter, or as needed");
    }
}
package com.example.android.recyclerview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class receipe1 extends AppCompatActivity {
    private TextView Test1;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe1);
        Test1 = (TextView) findViewById(R.id.recipe22);
        Test1.setText("much water\n" +
                "meet\n" +
                "love\n" +
                "family\n" +
                "½ cup chicken broth\n" +
                "¼ cup light brown sugar\n" +
                "1 tablespoon prepared yellow mustard\n" +
                "2 tablespoons butter, or as needed");
    }



}
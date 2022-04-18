package com.example.android.recyclerview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class receipe2 extends AppCompatActivity {

    private TextView recipe2;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe2);
        recipe2 = (TextView) findViewById(R.id.recipe22);
        recipe2.setText("1 teaspoon vegetable oil\n" +
                "1 cup barbeque sauce\n" +
                "½ cup apple cider vinegar\n" +
                "pork\n"+
                "1 tablespoon prepared yellow mustard\n" +
                "1 tablespoon Worcestershire sauce\n" +
                "1 tablespoon chili powder\n" +
                "1 extra large onion, chopped\n" +
                "2 large cloves garlic, crushed\n" +
                "2 tablespoons butter, or as needed");
    }
}
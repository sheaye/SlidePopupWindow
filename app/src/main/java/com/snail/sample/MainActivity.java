package com.snail.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new SharePopupWindow(this).show(getWindow().getDecorView().findViewById(android.R.id.content));
    }
}

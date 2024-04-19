package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class Store extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ImageButton p = findViewById(R.id.prec);
        p.setOnClickListener(v -> {
            Intent i = new Intent(Store.this, MyProfile.class);
            startActivity(i);
        });
    }
}
package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.azorom.chatgame.Pages.HomePage.HomePage;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Button b = findViewById(R.id.EditProfile);
        b.setOnClickListener(v -> {
            Intent intent2 = new Intent(MyProfile.this, EditProfile.class);
            startActivity(intent2);
        });
        ImageButton s = findViewById(R.id.store);
        s.setOnClickListener(v -> {
            Intent intent3 = new Intent(MyProfile.this, Store.class);
            startActivity(intent3);
        });
        ImageButton h = findViewById(R.id.Home);
        s.setOnClickListener(v -> {
            Intent intent4 = new Intent(MyProfile.this, HomePage.class);
            startActivity(intent4);
        });
    }
}
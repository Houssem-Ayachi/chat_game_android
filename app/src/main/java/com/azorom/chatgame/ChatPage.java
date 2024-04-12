package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ChatPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        String chatId = getChatId();
    }

    private String getChatId(){
        Intent i = this.getIntent();
        String chatId = i.getStringExtra("chatId");
        if(chatId == null){
            Log.d("DEBUG", "chatId: is null bby");
            Intent home = new Intent(this.getApplicationContext(), HomePage.class);
            startActivity(home);
            this.finish();
        }
        return chatId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "chatPage died");
    }
}
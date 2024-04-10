package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azorom.chatgame.Storage.CharacterSets;

import java.util.Map;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        fillFriendsScroll();
        fillConversationScroll();
        Button searchButton = findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(v -> {
            Intent i = new Intent(this, SearchPage.class);
            startActivity(i);
        });
    }

    //TODO: fetch friends data from actual db
    private void fillFriendsScroll(){
        LinearLayout frindsScroll = findViewById(R.id.onlineFriendsScroll);
        String[] names = new String[] {"zrom", "misou", "dbs", "lezzi", "balbes", "mimi", "ahmed", "skol", "3li", "nigger", "pplek"};
        for(String name: names){
            View box = View.inflate(this, R.layout.online_friend_description, null);
            TextView nameLabel = box.findViewById(R.id.nameLabel);
            nameLabel.setText(name);
            LinearLayout container = box.findViewById(R.id.friendDescriptionContainer);
            container.setOnClickListener(v -> {
                Log.d("CARD", name);
            });
            frindsScroll.addView(box);
        }
    }

    //TODO: fill conversation data from actual db
    private void fillConversationScroll(){
        LinearLayout conversationScroll = findViewById(R.id.convosScroll);
        for(int i=0;i<20;i++){
            View row = View.inflate(this, R.layout.conversation_row, null);
            conversationScroll.addView(row);
        }
    }
}
package com.azorom.chatgame.Pages.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azorom.chatgame.R;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.ActiveChatRow;
import com.azorom.chatgame.Requests.User.FilteredUser;
import com.azorom.chatgame.Requests.User.UserRequest;
import com.azorom.chatgame.SearchPage;
import com.azorom.chatgame.Storage.CharacterSets;
import com.azorom.chatgame.WS.IncomingObjects.BasicError;
import com.azorom.chatgame.WS.WSClient;
import com.azorom.chatgame.WS.WSSingleton;

public class HomePage extends AppCompatActivity {

    UserRequest userRequestHandler;
    WSClient wsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        this.userRequestHandler = new UserRequest(this.getApplicationContext());
        wsc = WSSingleton.getClient();

        wsc.getOnlineFriends(resp -> {
            RequestResponse<FilteredUser[], BasicError> friends = (RequestResponse<FilteredUser[], BasicError>)resp;
            Log.d("DEBUG", "" + friends.response.length);
        });

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

    private void fillConversationScroll(){
        RequestResponse<ActiveChatRow[], HttpRequestError> resp = this.userRequestHandler.getActiveChats();
        if(resp.error != null){
            Log.d("DEBUG", resp.error.message);
        }else if(resp.response != null){
            for(ActiveChatRow activeChat: resp.response){
                addActiveChatToScroll(activeChat);
            }
        }
    }

    private void addActiveChatToScroll(ActiveChatRow activeChat){
        LinearLayout conversationScroll = findViewById(R.id.convosScroll);
        View row = View.inflate(this, R.layout.conversation_row, null);
        ImageView hat = row.findViewById(R.id.convRowHat);
        hat.setImageResource(CharacterSets.hats.get(activeChat.user.character.hat));
        ImageView head = row.findViewById(R.id.convRowHead);
        head.setImageResource(CharacterSets.heads.get(activeChat.user.character.head));
        TextView userNameLabel = row.findViewById(R.id.userNameLabel);
        userNameLabel.setText(activeChat.user.userName);
        conversationScroll.addView(row);
    }
}
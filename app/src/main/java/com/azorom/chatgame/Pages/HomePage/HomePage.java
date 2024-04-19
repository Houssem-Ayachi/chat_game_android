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

import com.azorom.chatgame.Pages.ChatPage.ChatPage;
import com.azorom.chatgame.R;
import com.azorom.chatgame.Requests.Chat.ChatMessage;
import com.azorom.chatgame.Requests.Chat.ChatRequests;
import com.azorom.chatgame.Requests.Chat.OnlineChat;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.Chat.ChatRow;
import com.azorom.chatgame.Requests.User.FilteredUser;
import com.azorom.chatgame.Requests.User.UserRequests;
import com.azorom.chatgame.SearchPage;
import com.azorom.chatgame.Storage.DrawableSets;
import com.azorom.chatgame.WS.IncomingObjects.BasicError;
import com.azorom.chatgame.WS.IncomingObjects.BasicResponse;
import com.azorom.chatgame.WS.WSClient;
import com.azorom.chatgame.WS.WSEventsListener;
import com.azorom.chatgame.WS.WSSingleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomePage extends AppCompatActivity {

    UserRequests userRequestsHandler;
    ChatRequests chatRequestsHandler;
    WSClient wsc;
    WSEventsListener WSE = new WSEventsListener() {
        @Override
        public void onUserConnected(RequestResponse<FilteredUser, BasicError> response) {
            HomePage.this.runOnUiThread(() ->
                    addFriendToActiveFriendsScroll(response.response)
            );
        }

        @Override
        public void onUserDisconnected(RequestResponse<FilteredUser, BasicError> response) {
            getOnlineChats();
        }

        @Override
        public void onChatMessage(RequestResponse<ChatMessage, BasicError> response) {

        }

        @Override
        public void onRegistered(RequestResponse<BasicResponse, BasicError> response) {

        }

        @Override
        public void onSocketConnected() {

        }

        @Override
        public void onSocketDisconnected() {

        }

        @Override
        public void onSocketConnectionError() {

        }
    };
    ExecutorService executorService;

    public HomePage(){
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        this.userRequestsHandler = new UserRequests(this.getApplicationContext());
        this.chatRequestsHandler = new ChatRequests(this.getApplicationContext());

        wsc = WSSingleton.getClient();

        init();

        wsc.setListener(WSE);
    }

    private void init(){
        getOnlineChats();
        fillConversationScroll();
        Button searchButton = findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(v -> {
            Intent i = new Intent(this, SearchPage.class);
            startActivity(i);
        });
    }

    private void getOnlineChats(){
        wsc.getOnlineFriends(resp -> {
            RequestResponse<OnlineChat[], BasicError> friends = (RequestResponse<OnlineChat[], BasicError>)resp;
            HomePage.this.runOnUiThread(() -> {
                fillOnlineFriendsScroll(friends.response);
            });
        });
    }

    private void fillOnlineFriendsScroll(OnlineChat[] friends){
        LinearLayout friendsScroll = findViewById(R.id.onlineFriendsScroll);
        friendsScroll.removeAllViews();
        for(OnlineChat friend: friends){
            View box = createOnlineFriendView(friend);
            friendsScroll.addView(box);
        }
    }

    private View createOnlineFriendView(OnlineChat chat){
        View box = View.inflate(this, R.layout.online_chat_description, null);
        TextView nameLabel = box.findViewById(R.id.onlineFriendName);
        nameLabel.setText(chat.friend.userName);
        ImageView hat = box.findViewById(R.id.onlineFriendHat);
        hat.setImageResource(DrawableSets.hats.get(chat.friend.character.hat));
        ImageView head = box.findViewById(R.id.onlineFriendHead);
        head.setImageResource(DrawableSets.heads.get(chat.friend.character.head));
        box.setOnClickListener(v -> sendToChatPage(chat.chatId));
        return box;
    }

    private void addFriendToActiveFriendsScroll(FilteredUser friend){
        RequestResponse<OnlineChat, HttpRequestError> onlineChat = chatRequestsHandler.getFriendChat(friend._id);
        if(onlineChat.error != null){
            //TODO: handle this error (pls future me)
            return;
        }
        LinearLayout friendsScroll = findViewById(R.id.onlineFriendsScroll);
        friendsScroll.addView(createOnlineFriendView(onlineChat.response));
    }

    private void fillConversationScroll(){
        RequestResponse<ChatRow[], HttpRequestError> resp = this.chatRequestsHandler.getActiveChats();
        if(resp.error != null){
            Log.d("DEBUG", resp.error.message);
        }else if(resp.response != null){
            for(ChatRow activeChat: resp.response){
                addActiveChatToScroll(activeChat);
            }
        }
    }

    private void addActiveChatToScroll(ChatRow activeChat){
        LinearLayout conversationScroll = findViewById(R.id.convosScroll);
        conversationScroll.removeAllViews();
        View row = View.inflate(this, R.layout.conversation_row, null);
        ImageView hat = row.findViewById(R.id.convRowHat);
        hat.setImageResource(DrawableSets.hats.get(activeChat.user.character.hat));
        ImageView head = row.findViewById(R.id.convRowHead);
        head.setImageResource(DrawableSets.heads.get(activeChat.user.character.head));
        TextView userNameLabel = row.findViewById(R.id.userNameLabel);
        userNameLabel.setText(activeChat.user.userName);
        TextView lastMessageLabel = row.findViewById(R.id.lastMessage);
        lastMessageLabel.setText(activeChat.lastMessage.content);
        conversationScroll.addView(row);
        row.setOnClickListener(v -> sendToChatPage(activeChat.chatId));
    }

    private void sendToChatPage(String chatId){
        Intent i = new Intent(this, ChatPage.class);
        i.putExtra("chatId", chatId);
        startActivity(i);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
        this.wsc.setListener(WSE);
    }
}
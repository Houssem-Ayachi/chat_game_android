package com.azorom.chatgame.Pages.HomePage;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azorom.chatgame.Login;
import com.azorom.chatgame.MyProfile;
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
import com.azorom.chatgame.Pages.SearchPage.SearchPage;
import com.azorom.chatgame.Storage.Storage;
import com.azorom.chatgame.WS.IncomingObjects.BasicError;
import com.azorom.chatgame.WS.IncomingObjects.BasicResponse;
import com.azorom.chatgame.WS.WSClient;
import com.azorom.chatgame.WS.WSEventsListener;
import com.azorom.chatgame.WS.WSSingleton;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomePage extends AppCompatActivity {

    Storage storage;
    UserRequests userRequestsHandler;
    ChatRequests chatRequestsHandler;
    WSClient wsc;
    WSEventsListener WSE = new WSEventsListener() {
        @Override
        public void onUserConnected(RequestResponse<FilteredUser, BasicError> response) {
            HomePage.this.runOnUiThread(() ->
                    getOnlineChats()
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

        @Override
        public void onLevelUp() {

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

        storage = new Storage();

        this.userRequestsHandler = new UserRequests(this.getApplicationContext());
        this.chatRequestsHandler = new ChatRequests(this.getApplicationContext());

        wsc = WSSingleton.getClient();

        init();

        wsc.setListener(WSE);
    }

    private void init(){
        setSideBar();
        setOnlineFriendScroll();
        getOnlineChats();
        fillConversationScroll();
    }

    private void setSideBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setSideBarItemsActions();
    }

    private void setSideBarItemsActions(){
        NavigationView navView = findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(item -> {
            String itemTitle = item.getTitle().toString();
            switch (itemTitle){
                case "Search":
                    startActivity(new Intent(this, SearchPage.class));
                    break;
                case "Profile":
                    startActivity(new Intent(this, MyProfile.class));
                    break;
                case "Logout":
                    logout();
                    break;
            }
            return false;
        });
    }

    private void logout(){
        storage.removeKey();
        WSSingleton.disconnect();
        startActivity(new Intent(this, Login.class));
        this.finish();
    }

    private void getOnlineChats(){
        wsc.getOnlineFriends(resp -> {
            RequestResponse<OnlineChat[], BasicError> friends = (RequestResponse<OnlineChat[], BasicError>)resp;
            HomePage.this.runOnUiThread(() -> {
                RecyclerView friendsScroll = findViewById(R.id.onlineFriendsScroll);
                friendsScroll.setAdapter(new OnlineChatsAdapter(
                        friends.response,
                        chat -> sendToChatPage(chat.chatId)
                ));
            });
        });
    }

    private void setOnlineFriendScroll(){
        RecyclerView onlineFriendsContainer = findViewById(R.id.onlineFriendsScroll);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        onlineFriendsContainer.setLayoutManager(layoutManager);
    }

    private void fillConversationScroll(){
        ListView conversationScroll = findViewById(R.id.convosScroll);
        RequestResponse<ChatRow[], HttpRequestError> resp = this.chatRequestsHandler.getActiveChats();
        if(resp.error != null){
            Log.d("DEBUG", resp.error.message);
        }else if(resp.response != null){
            conversationScroll.setOnItemClickListener((adapterView, view, i, l) -> {
                ChatRow selectedChat = (ChatRow)adapterView.getItemAtPosition(i);
                sendToChatPage(selectedChat.chatId);
            });
            conversationScroll.setAdapter(new ActiveChatsAdapter(this, resp.response));
        }
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
package com.azorom.chatgame.Pages.ChatPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azorom.chatgame.Pages.ChatPage.Fragments.SendMessageFragment;
import com.azorom.chatgame.Pages.ChatPage.Fragments.StickersPickerFragment;
import com.azorom.chatgame.Pages.HomePage.HomePage;
import com.azorom.chatgame.R;
import com.azorom.chatgame.Requests.Chat.ChatMessage;
import com.azorom.chatgame.Requests.Chat.ChatRequests;
import com.azorom.chatgame.Requests.Chat.CreateMsgObj;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.FilteredUser;
import com.azorom.chatgame.Storage.DrawableSets;
import com.azorom.chatgame.WS.IncomingObjects.BasicError;
import com.azorom.chatgame.WS.IncomingObjects.BasicResponse;
import com.azorom.chatgame.WS.WSClient;
import com.azorom.chatgame.WS.WSEventsListener;
import com.azorom.chatgame.WS.WSSingleton;

import java.util.Objects;

//TODO: add sticker logic (can't send stickers for now)

public class ChatPage extends AppCompatActivity {

    String chatId;
    ChatRequests chatRequestsHandler;
    WSClient wsc;
    ChatMessage messages[];

    //NOTE: using this object to construct the message to send
    //it is being used this way so it can contain both a message and a sticker
    //message property is updated when the editText is submitted
    //sticker property is updated when the user chooses a sticker
    //beacause I couldn't find a way to update them both in the same function
    MessageViewModel messageViewModel;

    WSEventsListener wse = new WSEventsListener() {
        @Override
        public void onUserConnected(RequestResponse<FilteredUser, BasicError> response) {

        }

        @Override
        public void onUserDisconnected(RequestResponse<FilteredUser, BasicError> response) {

        }

        @Override
        public void onChatMessage(RequestResponse<ChatMessage, BasicError> response) {
            ChatPage.this.runOnUiThread(() -> addNewChatMessage(response.response));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        chatRequestsHandler = new ChatRequests(this.getApplicationContext());

        wsc = WSSingleton.getClient();
        wsc.setListener(wse);

        chatId = getChatId();

        messageViewModel.setChatId(chatId);
        messageViewModel.setSendMessageListener(this::sendMessage);
        messageViewModel.setSwitchToStickerPickerListener(this::setStickerPickerFragment);
        messageViewModel.setSwitchToMessageListener(this::setSendMessageFragment);

        setSendMessageFragment();
        getChatMessages();
    }

    private void setSendMessageFragment(){
        //default fragment for the fragment holder is the sendMessage Fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentHolder, SendMessageFragment.class, null)
                .commit();
    }

    private void setStickerPickerFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentHolder, StickersPickerFragment.class, null)
                .commit();
    }

    private void sendMessage(CreateMsgObj createdMsg){
        if(Objects.equals(createdMsg.message, "") && Objects.equals(createdMsg.sticker, "")){
            Log.d("DEBUG", "both fields empty");
            //TODO: idk send a notifcation or some thing to aler user;
            return;
        }
        wsc.sendMessage(createdMsg);
    }

    private void getChatMessages(){
        RequestResponse<ChatMessage[], HttpRequestError> resp = chatRequestsHandler.getChatMessages(chatId);
        if(resp.error != null){
            //TODO: handle error here pls
            return;
        }
        if(resp.response != null){
            messages = resp.response;
            fillChatWithMessages(resp.response);
        }
    }

    private void scrollToLastMessage(){
        NestedScrollView scroll = findViewById(R.id.messagesScroll);
        scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN));
    }

    private void addNewChatMessage(ChatMessage message){
        LinearLayout msgsContainer = findViewById(R.id.messagesContainer);
        View messageView;
        if(Objects.equals(message.sticker, "")){
            messageView = createChatMessage(message);
        }else{
            messageView = createChatMessageWithSticker(message);
        }
        msgsContainer.addView(messageView);
        scrollToLastMessage();
    }

    private void fillChatWithMessages(ChatMessage[] messages){
        LinearLayout msgsContainer = findViewById(R.id.messagesContainer);
        for(ChatMessage message: messages){
            View messageView;
            if(message.sticker == ""){
                messageView = createChatMessage(message);
            }else{
                messageView = createChatMessageWithSticker(message);
            }
            msgsContainer.addView(messageView);
        }
        scrollToLastMessage();
    }


    private View createChatMessage(ChatMessage message){
        View box = View.inflate(this, R.layout.chat_message, null);
        ImageView hat = box.findViewById(R.id.chatMsgStickerHat);
        hat.setImageResource(DrawableSets.hats.get(message.user.character.hat));
        ImageView head = box.findViewById(R.id.chatMsgStickerHead);
        head.setImageResource(DrawableSets.heads.get(message.user.character.head));
        TextView content = box.findViewById(R.id.chatMsgStickerContent);
        content.setText(message.content);
        return box;
    }

    private View createChatMessageWithSticker(ChatMessage message){
        View box = View.inflate(this, R.layout.chat_message_with_sticker, null);
        ImageView hat = box.findViewById(R.id.chatMsgStickerHat);
        hat.setImageResource(DrawableSets.hats.get(message.user.character.hat));
        ImageView head = box.findViewById(R.id.chatMsgStickerHead);
        head.setImageResource(DrawableSets.heads.get(message.user.character.head));
        TextView content = box.findViewById(R.id.chatMsgStickerContent);
        content.setText(message.content);
        ImageView sticker = box.findViewById(R.id.chatMessageSticker);
        sticker.setImageResource(DrawableSets.stickers.get(message.sticker));
        return box;
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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        wsc.setListener(wse);
    }
}
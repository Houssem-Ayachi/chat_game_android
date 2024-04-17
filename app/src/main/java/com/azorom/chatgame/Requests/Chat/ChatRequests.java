package com.azorom.chatgame.Requests.Chat;

import android.content.Context;

import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestsConstants;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ChatRequests {
    private final ExecutorService _executor;
    Context context;

    public ChatRequests(Context context){
        _executor = Executors.newSingleThreadExecutor();
        this.context = context;
    }

    private Future<Object> sendActiveChatsRequest(){
        return _executor.submit(() -> RequestsConstants.getRequest(
                RequestsConstants.serverHost + "/api/chat/active",
                ChatRow[].class
        ));
    }

    private Future<Object> sendGetFriendChat(String friendId){
        return _executor.submit(() -> RequestsConstants.getRequest(
                RequestsConstants.serverHost + "/api/chat/friend/"+friendId,
                OnlineChat.class
        ));
    }

    private Future<Object> sendGetChatMessages(String chatId){
        return _executor.submit(() -> RequestsConstants.getRequest(
           RequestsConstants.serverHost + "/api/chat/messages/"+chatId,
                ChatMessage[].class
        ));
    }

    public RequestResponse<ChatMessage[], HttpRequestError> getChatMessages(String chatId){
        RequestResponse<ChatMessage[], HttpRequestError> resp;
        try {
            resp = (RequestResponse<ChatMessage[], HttpRequestError>)this.sendGetChatMessages(chatId).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<OnlineChat, HttpRequestError> getFriendChat(String friendId){
        RequestResponse<OnlineChat, HttpRequestError> resp;
        try {
            resp = (RequestResponse<OnlineChat, HttpRequestError>)this.sendGetFriendChat(friendId).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<ChatRow[], HttpRequestError> getActiveChats(){
        RequestResponse<ChatRow[], HttpRequestError> resp;
        try {
            resp = (RequestResponse<ChatRow[], HttpRequestError>)this.sendActiveChatsRequest().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }
}
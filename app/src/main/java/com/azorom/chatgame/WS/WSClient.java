package com.azorom.chatgame.WS;

import android.util.Log;

import com.azorom.chatgame.JSON.CustomJsonParser;
import com.azorom.chatgame.Requests.Chat.ChatMessage;
import com.azorom.chatgame.Requests.Chat.CreateMsgObj;
import com.azorom.chatgame.Requests.Chat.OnlineChat;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestsConstants;
import com.azorom.chatgame.Requests.User.FilteredUser;
import com.azorom.chatgame.Storage.Storage;
import com.azorom.chatgame.WS.IncomingObjects.BasicError;
import com.azorom.chatgame.WS.IncomingObjects.BasicResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.List;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;

public class WSClient {

    String url = RequestsConstants.serverHost;
    Socket socket;
    String authToken;
    Storage storage;
    WSEventsListener listener;

    public WSClient(){
        storage = new Storage();
        authToken = storage.getKey();
        URI uri = URI.create(url);
        IO.Options options = IO.Options.builder()
                .setTransports(new String[] {"websocket"})
                .setExtraHeaders(Map.of("Authorization", List.of("Bearer " + authToken)))
                .build();
        socket = IO.socket(uri, options);
        socket.connect();
        initSocketListeners();
    }

    public void setListener(WSEventsListener listener) {
        this.listener = listener;
    }

    public void getOnlineFriends(WSResponseACK ack){
        socket.emit(EventsToEmit.GET_ONLINE_CHATS, null, arg -> {
            RequestResponse<OnlineChat[], BasicError> resp = (RequestResponse<OnlineChat[], BasicError>)CustomJsonParser.parseResponse(arg[0].toString(), OnlineChat[].class, BasicError.class);
            ack.resolve(resp);
        });
    }

    public void sendMessage(CreateMsgObj msgObj){
        JSONObject obj;
        try {
            obj = new JSONObject(CustomJsonParser.convertToJson(msgObj));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        socket.emit(EventsToEmit.SEND_MESSAGE, obj);
    }

    private void register(){
        socket.emit(EventsToEmit.REGISTER, null, arg -> {
            RequestResponse<BasicResponse, BasicError> resp = (RequestResponse<BasicResponse, BasicError>)CustomJsonParser.parseResponse(arg[0].toString(), BasicResponse.class, BasicError.class);
            if(listener != null){
                listener.onRegistered(resp);
            }
        });
    }

    private void initSocketListeners(){
        socket.on(Socket.EVENT_CONNECT, args -> {
            Log.d("DEBUG", "SOCKET CONNECTED");
            register();
            if(listener != null){
                listener.onSocketConnected();
            }
        });

        socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
           Log.d("DEBUG", "SOCKET ERROR: " + args[0].toString());
            if(listener != null){
                listener.onSocketConnectionError();
            }
        });

        socket.on(Socket.EVENT_DISCONNECT, args -> {
            Log.d("DEBUG", "DISCONNECTED");
            if(listener != null){
                listener.onSocketDisconnected();
            }
        });

        socket.on(EventsToListenTo.USER_CONNECTED, args -> {
            RequestResponse<FilteredUser, BasicError> resp = (RequestResponse<FilteredUser, BasicError>) CustomJsonParser.parseResponse(args[0].toString(), FilteredUser.class, BasicError.class);
            if(listener != null){
                listener.onUserConnected(resp);
            }else{
                Log.d("DEBUG", "listener is null");
            }
        });

        socket.on(EventsToListenTo.USER_DISCONNECTED, args -> {
            RequestResponse<FilteredUser, BasicError> resp = (RequestResponse<FilteredUser, BasicError>) CustomJsonParser.parseResponse(args[0].toString(), FilteredUser.class, BasicError.class);
            Log.d("DEBUG", "user disconnected: " + resp.response._id);
            if(listener != null){
                listener.onUserDisconnected(resp);
            }
        });

        socket.on(EventsToListenTo.CHAT_MESSAGE, args -> {
            RequestResponse<ChatMessage, BasicError> res = (RequestResponse<ChatMessage, BasicError>) CustomJsonParser.parseResponse(args[0].toString(), ChatMessage.class, BasicError.class);
            if(listener != null){
                listener.onChatMessage(res);
            }
        });
    }
}
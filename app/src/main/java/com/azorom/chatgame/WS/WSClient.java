package com.azorom.chatgame.WS;

import android.util.Log;

import com.azorom.chatgame.JSON.CustomJsonParser;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestsConstants;
import com.azorom.chatgame.Requests.User.FilteredUser;
import com.azorom.chatgame.Storage.Storage;
import com.azorom.chatgame.WS.IncomingObjects.BasicError;
import com.azorom.chatgame.WS.IncomingObjects.BasicResponse;
import com.azorom.chatgame.WS.IncomingObjects.UserConnectDisconnectOBJ;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
        socket.emit(EventsToEmit.GET_ONLINE_FRIENDS, null, arg -> {
            RequestResponse<FilteredUser[], BasicError> resp = (RequestResponse<FilteredUser[], BasicError>)CustomJsonParser.parseResponse(arg[0].toString(), FilteredUser[].class, BasicError.class);
            ack.resolve(resp);
        });
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
            RequestResponse<UserConnectDisconnectOBJ, BasicError> resp = (RequestResponse<UserConnectDisconnectOBJ, BasicError>) CustomJsonParser.parseResponse(args[0].toString(), UserConnectDisconnectOBJ.class, BasicError.class);
            if(listener != null){
                listener.onUserConnected(resp);
            }
        });

        socket.on(EventsToListenTo.USER_DISCONNECTED, args -> {
            RequestResponse<UserConnectDisconnectOBJ, BasicError> resp = (RequestResponse<UserConnectDisconnectOBJ, BasicError>) CustomJsonParser.parseResponse(args[0].toString(), UserConnectDisconnectOBJ.class, BasicError.class);
            if(listener != null){
                listener.onUserConnected(resp);
            }
        });
    }
}
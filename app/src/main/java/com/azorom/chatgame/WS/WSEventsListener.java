package com.azorom.chatgame.WS;

import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.WS.IncomingObjects.BasicError;
import com.azorom.chatgame.WS.IncomingObjects.BasicResponse;
import com.azorom.chatgame.WS.IncomingObjects.ChatMessage;
import com.azorom.chatgame.WS.IncomingObjects.UserConnectDisconnectOBJ;

public interface WSEventsListener {
    void onUserConnected(RequestResponse<UserConnectDisconnectOBJ, BasicError> response);
    void onUserDisconnected(RequestResponse<UserConnectDisconnectOBJ, BasicError> response);
    void onChatMessage(RequestResponse<ChatMessage, BasicError> response);
    void onRegistered(RequestResponse<BasicResponse, BasicError> response);
    void onSocketConnected();
    void onSocketDisconnected();
    void onSocketConnectionError();
}

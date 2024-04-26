package com.azorom.chatgame.WS;

import com.azorom.chatgame.Requests.Chat.ChatMessage;
import com.azorom.chatgame.Requests.Chat.PlainMessageObj;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.FilteredUser;
import com.azorom.chatgame.WS.IncomingObjects.BasicError;
import com.azorom.chatgame.WS.IncomingObjects.BasicResponse;

public interface WSEventsListener {
    void onUserConnected(RequestResponse<FilteredUser, BasicError> response);
    void onUserDisconnected(RequestResponse<FilteredUser, BasicError> response);
    void onChatMessage(RequestResponse<ChatMessage, BasicError> response);
    void onRegistered(RequestResponse<BasicResponse, BasicError> response);
    void onSocketConnected();
    void onSocketDisconnected();
    void onSocketConnectionError();
    void onLevelUp();
}

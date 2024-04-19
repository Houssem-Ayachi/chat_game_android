package com.azorom.chatgame.Pages.ChatPage.Interfaces;

import com.azorom.chatgame.Requests.Chat.CreateMsgObj;

public interface SendMessageListener {
    void sendMessage(CreateMsgObj message);
}

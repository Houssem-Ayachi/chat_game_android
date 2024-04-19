package com.azorom.chatgame.Requests.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatCreatedResponse {
    @JsonProperty("chatId")
    public String chatId;
}

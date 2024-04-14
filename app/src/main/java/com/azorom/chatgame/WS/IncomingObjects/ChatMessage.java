package com.azorom.chatgame.WS.IncomingObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessage {
    @JsonProperty("senderId")
    public String senderId;
    @JsonProperty("chatId")
    public String chatId;
    @JsonProperty("content")
    public String content;
    @JsonProperty("sticker")
    public String sticker;
}

package com.azorom.chatgame.Requests.Chat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageObj {

    @JsonProperty("senderId")
    public String senderId;
    @JsonProperty("chatId")
    public String chatId;
    @JsonProperty("content")
    public String content;
    @JsonProperty("sticker")
    public String sticker;

}

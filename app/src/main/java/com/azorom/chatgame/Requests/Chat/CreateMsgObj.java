package com.azorom.chatgame.Requests.Chat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateMsgObj {
    @JsonProperty("chatId")
    public String chatId;
    @JsonProperty("message")
    public String message;
    @JsonProperty("sticker")
    public String sticker;

    public CreateMsgObj(String chatId, String message, String sticker){
        this.chatId = chatId;
        this.message = message;
        this.sticker = sticker;
    }

}

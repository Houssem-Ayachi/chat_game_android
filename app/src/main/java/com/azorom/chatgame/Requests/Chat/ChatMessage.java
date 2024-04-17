package com.azorom.chatgame.Requests.Chat;

import com.azorom.chatgame.Requests.User.FilteredUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessage {
    @JsonProperty("user")
    public FilteredUser user;
    @JsonProperty("chatId")
    public String chatId;
    @JsonProperty("content")
    public String content;
    @JsonProperty("sticker")
    public String sticker;
}

package com.azorom.chatgame.Requests.Chat;

import com.azorom.chatgame.Requests.User.FilteredUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatRow {
    @JsonProperty("chatId")
    public String chatId;

    @JsonProperty("lastMessage")
    public PlainMessageObj lastMessage;

    @JsonProperty("user")
    public FilteredUser user;
}

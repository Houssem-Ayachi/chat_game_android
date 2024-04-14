package com.azorom.chatgame.Requests.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActiveChatRow {
    @JsonProperty("chatId")
    public String chatId;

    @JsonProperty("user")
    public FilteredUser user;
}

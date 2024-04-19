package com.azorom.chatgame.Requests.Chat;

import com.azorom.chatgame.Requests.User.FilteredUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OnlineChat {
    @JsonProperty("_id")
    public String chatId;

    @JsonProperty("friend")
    public FilteredUser friend;
}

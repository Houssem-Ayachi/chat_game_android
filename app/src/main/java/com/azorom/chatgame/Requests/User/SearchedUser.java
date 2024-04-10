package com.azorom.chatgame.Requests.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchedUser {
    @JsonProperty("_id")
    public String _id;
    @JsonProperty("userName")
    public String userName;
    @JsonProperty("character")
    public Character character;
}

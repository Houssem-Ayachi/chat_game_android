package com.azorom.chatgame.Requests.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Character {
    @JsonProperty("hat")
    public String hat;
    @JsonProperty("head")
    public String head;
    @JsonProperty("_id")
    public String _id;
}
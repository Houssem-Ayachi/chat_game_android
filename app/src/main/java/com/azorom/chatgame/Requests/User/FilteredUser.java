package com.azorom.chatgame.Requests.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilteredUser {
    @JsonProperty("_id")
    public String _id;
    @JsonProperty("userName")
    public String userName;
    @JsonProperty("character")
    public Character character;

    @JsonProperty("currentXp")
    public int currentXp;

    @JsonProperty("points")
    public int points;

    @JsonProperty("level")
    public String level;

}

package com.azorom.chatgame.Requests.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileResp {

    @JsonProperty("_id")
    public String _id;

    @JsonProperty("userName")
    public String userName;

    @JsonProperty("character")
    public Character character;

    @JsonProperty("bio")
    public String bio;

    @JsonProperty("email")
    public String email;

    @JsonProperty("funFacts")
    public String[] funFacts;

    @JsonProperty("level")
    public Level level;

    @JsonProperty("points")
    public int points;

}

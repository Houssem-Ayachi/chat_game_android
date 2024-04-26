package com.azorom.chatgame.Requests.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Level {

    @JsonProperty("_id")
    public String _id;

    @JsonProperty("rank")
    public int rank;

    @JsonProperty("id")
    public String id;

    @JsonProperty("createdAt")
    public String createdAt;

    @JsonProperty("updatedAt")
    public String updatedAt;

    @JsonProperty("__v")
    public int __v;

    @JsonProperty("xpCeiling")
    public int xpCeiling;

}

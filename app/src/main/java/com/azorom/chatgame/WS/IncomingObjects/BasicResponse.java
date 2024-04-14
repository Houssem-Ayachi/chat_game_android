package com.azorom.chatgame.WS.IncomingObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicResponse {
    @JsonProperty("message")
    public String message;
}

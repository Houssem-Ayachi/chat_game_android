package com.azorom.chatgame.WS.IncomingObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicError {
    @JsonProperty("message")
    public String message;

    @JsonProperty("error")
    public String error;
}

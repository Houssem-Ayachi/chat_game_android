package com.azorom.chatgame.WS.IncomingObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserConnectDisconnectOBJ {
    @JsonProperty("frindId")
    public String friendId;
}

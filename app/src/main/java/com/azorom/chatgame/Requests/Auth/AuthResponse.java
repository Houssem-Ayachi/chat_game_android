package com.azorom.chatgame.Requests.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {
    public String access_key;
    public AuthResponse(@JsonProperty("access_key") String accessKey){
        this.access_key = accessKey;
    }
}

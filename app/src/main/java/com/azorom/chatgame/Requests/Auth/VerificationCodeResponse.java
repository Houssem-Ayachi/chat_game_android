package com.azorom.chatgame.Requests.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerificationCodeResponse {
    public String response;
    public VerificationCodeResponse(@JsonProperty("response") String response){
        this.response = response;
    }
}

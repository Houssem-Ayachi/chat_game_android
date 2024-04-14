package com.azorom.chatgame.Requests.Constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpRequestError {
    public String message;
    public String error;
    public int statusCode;
    public HttpRequestError(
            @JsonProperty("message") String message,
            @JsonProperty("error") String error,
            @JsonProperty("statusCode") int statusCode
    ){
        this.message = message;
        this.error = error;
        this.statusCode = statusCode;
    }
}
